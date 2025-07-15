package org.chinoel.goormspring.config;

import lombok.RequiredArgsConstructor;
import org.chinoel.goormspring.interceptor.PrivatePostInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final PrivatePostInterceptor privatePostInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(privatePostInterceptor)
                .addPathPatterns("/post/*")
                .order(1);
    }
}