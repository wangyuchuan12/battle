package com.wyc.common.config;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;


@EnableWebMvc
@Configuration
@ComponentScan(basePackages="com",
				excludeFilters={
					@Filter(type=FilterType.ASSIGNABLE_TYPE,value=AppConfig.class),
					@Filter(type=FilterType.ASSIGNABLE_TYPE,value=DatabaseConfig.class),
					@Filter(type=FilterType.ASSIGNABLE_TYPE,value=WebConfig.class)
})
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class GameWebConfig extends WebMvcConfigurerAdapter{
	
	
	@Qualifier(value="gameViewResolver")
	@Primary
    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/games/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

	
	@Qualifier(value="gameTilesConfigurer")
	@Primary
    @Bean
    public TilesConfigurer tilesConfigurer() {
        final TilesConfigurer configurer = new TilesConfigurer();
        configurer.setDefinitions(new String[] {"/WEB-INF/tiles.xml"});
        configurer.setCheckRefresh(true);
        return configurer;
    }

	
	@Qualifier(value="gameDispatcherServlet")
	@Primary
    @Bean
    public DispatcherServlet dispatcherServlet() {
        return new DispatcherServlet();
    }
    
	
	@Qualifier(value="gameMultipartResolver")
	@Primary
    @Bean
    public MultipartResolver multipartResolver(){
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setDefaultEncoding("utf-8");
        commonsMultipartResolver.setMaxUploadSize(500000);
        return commonsMultipartResolver;
    }
    
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/js/**").addResourceLocations("/js/");
        registry.addResourceHandler("/css/**").addResourceLocations("/css/");
    }
}
