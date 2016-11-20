package cn.wuwenyao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	// Don't forget the @Bean annotation
	public Docket customImplementation() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(new ApiInfo("文尧的api", "欢迎使用", "0.0.1", "dasdad", "not contact to us", "dsada", "dasda"));

	}
}