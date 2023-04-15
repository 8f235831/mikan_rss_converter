package pers.u8f23.mikan_rss_converter.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 8f23
 * @create 2023/4/15-12:30
 */
@Configuration
public class WebConfig implements WebMvcConfigurer
{
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry)
	{
		registry.addResourceHandler("/static/**") //过滤策略
			.addResourceLocations("classpath:/static/");  // 静态资源路径
	}
}
