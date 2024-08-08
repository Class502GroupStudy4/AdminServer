<<<<<<< HEAD:src/main/java/org/g9project4/global/configs/MvcConfig.java
package org.g9project4.global.configs;

=======
package org.g9project4.admin.global.configs;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
>>>>>>> 0694ea017b72880143ea0b0b2a75758e79b2496d:src/main/java/org/g9project4/admin/global/configs/MvcConfig.java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableJpaAuditing
<<<<<<< HEAD:src/main/java/org/g9project4/global/configs/MvcConfig.java
public class MvcConfig implements WebMvcConfigurer {

=======
@EnableDiscoveryClient
public class MvcConfig implements WebMvcConfigurer {
    /*
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("front/index");
    }
    */
>>>>>>> 0694ea017b72880143ea0b0b2a75758e79b2496d:src/main/java/org/g9project4/admin/global/configs/MvcConfig.java

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }

    /*
     * <input type="hidden" name="_method" value="PATCH">->patch 방식으로 요청
     * ?_method=DELETE
     * */
    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {//_method 방식으로 보냈을 때 해당 방식으로 처리
        return new HiddenHttpMethodFilter();
    }
}
