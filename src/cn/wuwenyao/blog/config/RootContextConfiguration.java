package cn.wuwenyao.blog.config;

import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.Executor;

import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.Ordered;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/***
 * {@link Configuration},标志这是一个spring的配置
 * {@link EnableScheduling},启用spring定时任务功能，实现{@link SchedulingConfigurer}可以具体配置
 * {@link EnableAsync},启用spring的异步任务功能，实现{@link AsyncConfigurer}可以具体配置
 * {@link EnableTransactionManagement}
 * {@link EnableJpaRepositories}，启用spring data jpa仓库
 * {@link ComponentScan}，扫描包，获取Bean
 * {@link ImportResource}，导入xml配置的bean
 * @author 文尧
 *
 */
@Configuration
@EnableScheduling
@EnableAsync(mode = AdviceMode.PROXY, proxyTargetClass = false, order = Ordered.HIGHEST_PRECEDENCE)
@EnableTransactionManagement(mode = AdviceMode.PROXY, proxyTargetClass = false, order = Ordered.LOWEST_PRECEDENCE)
@EnableJpaRepositories(basePackages = "cn.wuwenyao.blog.site.dao", entityManagerFactoryRef = "entityManagerFactoryBean", transactionManagerRef = "jpaTransactionManager")
@ComponentScan(basePackages = "cn.wuwenyao.blog.site", excludeFilters = @ComponentScan.Filter({ Controller.class,
		ControllerAdvice.class }))
@ImportResource(locations="classPath:datasource.xml")
public class RootContextConfiguration implements AsyncConfigurer, SchedulingConfigurer,ApplicationContextAware {
	private static final Logger log = LoggerFactory.getLogger(RootContextConfiguration.class);
	private static final Logger schedulingLogger = LoggerFactory.getLogger("scheduling");
	private static final Logger asyncLogger = LoggerFactory.getLogger("async");
	private ApplicationContext applicationContext;
	private static final String webBasePackage = "cn.wuwenyao.blog.site";
	private static final String entityPackage = webBasePackage + ".entity";
	

	/**
	 * 实体验证
	 * @return
	 */
	@Bean
	public LocalValidatorFactoryBean localValidatorFactoryBean() {
		LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
		return validator;
	}

	

	/***
	 * 
	 * @return
	 */
	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.findAndRegisterModules();
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		mapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);
		return mapper;
	}

	/***
	 * xml实体支持
	 * @return
	 */
	@Bean
	public Jaxb2Marshaller jaxb2Marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setPackagesToScan(new String[] { webBasePackage });
		return marshaller;
	}

	

	/***
	 * spring jpa 实体管理工厂
	 * @return
	 */
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
		Map<String, Object> properties = new Hashtable<>();
		properties.put("javax.persistence.schema-generation.database.action", "none");

		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setDatabasePlatform("org.hibernate.dialect.MySQL5InnoDBDialect");

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(adapter);
		factory.setDataSource(applicationContext.getBean(org.apache.tomcat.jdbc.pool.DataSource.class));
		factory.setPackagesToScan(entityPackage);
		factory.setSharedCacheMode(SharedCacheMode.ENABLE_SELECTIVE);
		factory.setValidationMode(ValidationMode.NONE);
		factory.setJpaPropertyMap(properties);
		return factory;
	}

	/***
	 * spring jpa 事务管理
	 * @return
	 */
	@Bean
	public PlatformTransactionManager jpaTransactionManager() {
		return new JpaTransactionManager(this.entityManagerFactoryBean().getObject());
	}

	/***
	 * 任务的线程池
	 * @return
	 */
	@Bean
	public ThreadPoolTaskScheduler taskScheduler() {
		log.info("Setting up thread pool task scheduler with 20 threads.");
		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		scheduler.setPoolSize(20);
		scheduler.setThreadNamePrefix("task-");
		scheduler.setAwaitTerminationSeconds(60);
		scheduler.setWaitForTasksToCompleteOnShutdown(true);
		scheduler.setErrorHandler(t -> schedulingLogger.error("Unknown error occurred while executing task.", t));
		scheduler.setRejectedExecutionHandler(
				(r, e) -> schedulingLogger.error("Execution of task {} was rejected for unknown reasons.", r));
		return scheduler;
	}

	/***
	 * 异步任务的执行器
	 */
	@Override
	public Executor getAsyncExecutor() {
		Executor executor = this.taskScheduler();
		log.info("Configuring asynchronous method executor {}.", executor);
		return executor;
	}

	/***
	 * 定时任务的执行器
	 */
	@Override
	public void configureTasks(ScheduledTaskRegistrar registrar) {
		TaskScheduler scheduler = this.taskScheduler();
		log.info("Configuring scheduled method executor {}.", scheduler);
		registrar.setTaskScheduler(scheduler);
	}

	/***
	 * 异步执行错误处理
	 */
	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		// TODO Auto-generated method stub
		return new AsyncUncaughtExceptionHandler() {
			
			@Override
			public void handleUncaughtException(Throwable ex, Method method, Object... params) {
				asyncLogger.error(ex.getMessage());
				
			}
		};
	}

	/***
	 * 注入applicationContext
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
		
	}
}
