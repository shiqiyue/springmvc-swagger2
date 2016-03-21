package cn.wuwenyao.blog.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;

import cn.wuwenyao.blog.site.App;

/****
 * spring 根上下文配置
 * @author 文尧
 *
 */
@Configuration
@ComponentScan(basePackageClasses = App.class, excludeFilters = {
		@ComponentScan.Filter(classes = { Controller.class, ControllerAdvice.class }) })

public class RootContextConfig {

}
