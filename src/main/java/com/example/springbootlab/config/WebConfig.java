package com.example.springbootlab.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.Duration;

@EnableWebMvc // Swagger를 사용하기 위해 추가
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Value("${upload.image.location")
    private String location;

    // /image/ 접두 경로 접근 시 파일 시스템의 location 경로에 접근
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/image/**")
                .addResourceLocations("file:" + location)
                .setCacheControl(CacheControl.maxAge(Duration.ofHours(1L)).cachePublic()); // 이미지 이름은 수정되지 않기 때문에 캐시 설정
        // 자원에 접근할 때마다 새롭게 자원을 내려받지 않고, 캐시된 자원을 이용 (1시간 마다 캐시 만료)
    }
}
