package cn.wuwenyao;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import cn.wuwenyao.config.RootContextConfiguration;
import cn.wuwenyao.config.WebConfiguration;

/***
 * web应用程序初始化，相当于web.xml
 * 
 * @author 文尧
 *
 */
public class App implements WebApplicationInitializer {
	private static final Logger log = LoggerFactory.getLogger(App.class);

	public void onStartup(ServletContext container) throws ServletException {
		log.debug("excute framework bootstrap");
		ServletRegistration defaultServlet = container
				.getServletRegistration("default");
		defaultServlet.addMapping("/static/*", "/favicon.ico");
		// 添加属性初始化监听器

		// 添加spring根上下文
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(RootContextConfiguration.class);
		container.addListener(new ContextLoaderListener(rootContext));

		// 添加springmvc上下文
		AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
		webContext.register(WebConfiguration.class);
		ServletRegistration.Dynamic dispatcher = container.addServlet(
				"springDispatcher", new DispatcherServlet(webContext));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");

		// 编码过滤器
		FilterRegistration.Dynamic encodingFilter = container.addFilter(
				"encodingFilter", new CharacterEncodingFilter("UTF-8", true));
		encodingFilter.addMappingForUrlPatterns(null, false, "/*");

	}
}
