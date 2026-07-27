/*
 * Copyright (c) 2025 Maritime Connectivity Platform Consortium
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.maritimeconnectivity.serviceregistry.config;

import jakarta.servlet.MultipartConfigElement;
import net.maritimeconnectivity.serviceregistry.components.GeoJsonStringToGeometryConverter;
import net.maritimeconnectivity.serviceregistry.components.StringToG1128SchemaConverter;
import net.maritimeconnectivity.serviceregistry.components.StringToServiceStatusConverter;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletPath;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * The WebConfig Class
 *
 * This is the main configuration class for the Web MVC operations.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * The String to G1128 Schema Converter.
     */
    @Autowired
    StringToG1128SchemaConverter stringToG1128SchemaConverter;

    /**
     * The String to Service Status Converter.
     */
    @Autowired
    StringToServiceStatusConverter stringToServiceStatusConverter;

    /**
     * The GeoJSON string to Geometry Converter.
     */
    @Autowired
    GeoJsonStringToGeometryConverter geoJsonStringToGeometryConverter;

    /**
     * Add the static resources and webjars to the web resources.
     *
     * @param registry the resource handler registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/webjars/**",
                        "/static/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/",
                        "classpath:/static/")
                .resourceChain(false);
        registry.setOrder(1);
    }

    /**
     * Make the index.html our main page so that it's being picked up by
     * Thymeleaf.
     *
     * @param registry The View Controller Registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/index");
    }

    /**
     * Add the converters between strings and the G1128 Service Instance status
     * and the G1128 Schemas enumerations.
     *
     * @param registry the Formatter Registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(stringToG1128SchemaConverter);
        registry.addConverter(stringToServiceStatusConverter);
        registry.addConverter(geoJsonStringToGeometryConverter);
    }

    /**
     * SECOM v1 (RESTEasy/JAX-RS, from the secom-springboot3 library) is
     * registered on the servlet path "/api/secom/*", which as a prefix
     * mapping would otherwise shadow the SECOM v2 Spring MVC endpoint at
     * "/api/secom/v2/searchService" (the servlet container always prefers a
     * matching servlet over a prefix-mapped one).
     * <p>
     * Only one Spring MVC endpoint actually lives under "/api/secom/v2/":
     * {@code SecomV2SearchServiceController#searchService}. Adding its exact
     * path here (not a "/api/secom/v2/*" prefix) makes the DispatcherServlet
     * win for that one URL without shadowing anything else RESTEasy serves,
     * and - critically - an exact-match servlet mapping does not trigger
     * Spring's per-request "strip the servlet mapping prefix" behavior
     * (see {@code org.springframework.web.util.ServletRequestPathUtils}),
     * unlike a "/*" prefix mapping would. That means neither the controller's
     * own absolute {@code @RequestMapping} nor the SECOM library's exception
     * mapper (which inspects {@code request.getServletPath()}) need to
     * change: this dispatch behaves exactly as if it arrived via the
     * default "/" mapping.
     * <p>
     * If more Spring MVC SECOM v2 endpoints are added later, their exact
     * paths need to be added to this list too.
     * <p>
     * Boot's own {@code DispatcherServletRegistrationBean} refuses extra URL
     * mappings (it must stay the single source of truth for
     * {@link DispatcherServletPath}), so a plain {@link ServletRegistrationBean}
     * is used here instead, and {@link DispatcherServletPath} is supplied
     * separately below to satisfy other autoconfiguration (e.g. error pages).
     *
     * @param dispatcherServlet the Spring MVC dispatcher servlet
     * @param webMvcProperties  the Spring MVC properties (servlet path/load-on-startup)
     * @param multipartConfig   the optional multipart config element
     * @return the customized DispatcherServlet registration
     */
    @Bean
    public ServletRegistrationBean<DispatcherServlet> dispatcherServletRegistration(
            DispatcherServlet dispatcherServlet,
            WebMvcProperties webMvcProperties,
            ObjectProvider<MultipartConfigElement> multipartConfig) {
        String primaryPath = webMvcProperties.getServlet().getPath();
        ServletRegistrationBean<DispatcherServlet> registration = new ServletRegistrationBean<>(
                dispatcherServlet, primaryPath, "/api/secom/v2/searchService");
        registration.setName(DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_BEAN_NAME);
        registration.setLoadOnStartup(webMvcProperties.getServlet().getLoadOnStartup());
        multipartConfig.ifAvailable(registration::setMultipartConfig);
        return registration;
    }

    /**
     * Reports the primary DispatcherServlet path for autoconfiguration that
     * depends on {@link DispatcherServletPath} (e.g. error page mapping),
     * since {@link #dispatcherServletRegistration} above no longer produces
     * a bean of that type.
     *
     * @param webMvcProperties the Spring MVC properties (servlet path)
     * @return the primary dispatcher servlet path
     */
    @Bean
    public DispatcherServletPath dispatcherServletPath(WebMvcProperties webMvcProperties) {
        return webMvcProperties.getServlet()::getPath;
    }

}
