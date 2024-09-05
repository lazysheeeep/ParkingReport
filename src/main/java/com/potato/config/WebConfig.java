package com.potato.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.potato.interceptor.PermissionInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.cbor.MappingJackson2CborHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Configuration
@Slf4j
public class WebConfig extends WebMvcConfigurationSupport {

  @Autowired private PermissionInterceptor permissionInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(permissionInterceptor)
            .addPathPatterns("/**")
            .excludePathPatterns( //放行的页面
                    "/users/login",
                    "/users/register",
                    "/users/send",
                    "/users/changePassword",
                    "/uploads/**"
            );
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    // 将/uploads/** 映射到 static/uploads/
    registry.addResourceHandler("/uploads/**")
            .addResourceLocations("file:uploads/");
  }

  @Override
  protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    log.info("扩展自己的消息转换器");

    // 创建消息转换器对象
    MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();

    // 设置对象转换器，底层使用JacksonObjectMapper将Java对象转为Json
    messageConverter.setObjectMapper(new ObjectMapper());

    // 将上面的消息转换器对象追加到MVC集合中
    converters.add(0,messageConverter); // 增加索引，把我们的转换器放到最前面
  }

  // 跨域配置
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    // 设置允许跨域的路径
    registry.addMapping("/**") //设置允许跨域的路径
            .allowedOriginPatterns("*")  // 设置允许跨域请求的域名
            .allowCredentials(true) // 是否允许证书
            .allowedMethods("*") // 设置允许的方法
            .allowedHeaders("*") // 设置允许的header属性
            .maxAge(3600); // 允许跨域时间
  }
}
