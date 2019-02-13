package com.jrmcdonald.podcasts.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.EncodedResourceResolver;

/**
 * WebMvc configuration class.
 * 
 * @author Jamie McDonald
 */
@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {
    
    @Value("${podcast.file.source}")
    private String fileSource;

    /**
     * Create a static file resource handler to expose audio files.
     * 
     * @param registry the {@link ResourceHandlerRegistry} to update
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/files/**/*.mp3").addResourceLocations("file:" + fileSource)
                .setCachePeriod(3600).resourceChain(true)
                .addResolver(new EncodedResourceResolver());
    }
}
