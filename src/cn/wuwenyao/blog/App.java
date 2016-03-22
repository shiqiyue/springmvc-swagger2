package cn.wuwenyao.blog;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import cn.wuwenyao.blog.config.RootContextConfiguration;
import cn.wuwenyao.blog.config.WebConfiguration;

/***
 * web应用程序初始化，相当于web.xml
 * @author 文尧
 *
 */
public class App implements WebApplicationInitializer {
	@Override
	public void onStartup(ServletContext container) throws ServletException {
		//静态内容配置到default的servlet处理
		container.getServletRegistration("default").addMapping("/static/*");
		
		//应用程序的根上下文配置
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(RootContextConfiguration.class);
		container.addListener(new ContextLoaderListener(rootContext));
		
		//web的根上下文配置
		AnnotationConfigWebApplicationContext mvcContext = new AnnotationConfigWebApplicationContext();
		mvcContext.register(WebConfiguration.class);
		ServletRegistration.Dynamic dispatcher = container.addServlet("springDispatcher",
				new DispatcherServlet(mvcContext));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");
		
		//编码格式化过滤器
		FilterRegistration.Dynamic encodingFilter = container.addFilter("encodingFilter", 
				new CharacterEncodingFilter("UTF-8", true));
		encodingFilter.addMappingForUrlPatterns(null, false, "/");
		

	}
}
