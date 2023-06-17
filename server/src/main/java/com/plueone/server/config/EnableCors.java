// package com.plueone.server.config;

// import org.springframework.web.servlet.config.annotation.CorsRegistry;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// public class EnableCors implements WebMvcConfigurer {

//     private String path;
//     private String origins;

//     public EnableCors(String p, String o) {
//         path = p;
//         origins = o;
//     }

//     @Override
//     public void addCorsMappings(CorsRegistry registry) {
//         registry.addMapping(path)
//                 .allowedMethods("GET", "POST", "PUT", "DELETE")
//                 .allowedOriginPatterns("*")
//                 .allowedOriginPatterns("*")
//                 .allowCredentials(true)
//                 .allowedOrigins(origins);

//     }

// }