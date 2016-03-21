package cn.wuwenyao.blog.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import cn.wuwenyao.blog.site.App;

/***
 * spring web端配置
 * @author 文尧
 *
 */
@Configuration
@ComponentScan(basePackageClasses = App.class, useDefaultFilters = false, includeFilters = {
		@ComponentScan.Filter(classes = {Controller.class,ControllerAdvice.class}) })
@EnableWebMvc
public class WebConfig {

}
