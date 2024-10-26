package com.tcc5.car_price_compare.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return  httpSecurity
                .cors(c -> c.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/vehicle").permitAll()
                        .requestMatchers(HttpMethod.POST, "/vehicle").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/vehicle/{id}/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/vehicle/brand").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/vehicle/model").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/vehicle/brand").permitAll()
                        .requestMatchers(HttpMethod.GET, "/brand/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/model/**").permitAll()
                        .requestMatchers(HttpMethod.GET , "/vehicle/model").permitAll()
                        .requestMatchers(HttpMethod.POST, "/user/notifications").authenticated()
                        .requestMatchers(HttpMethod.GET, "user/favorites/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/user/favorites").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/user/favorites").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/user/").authenticated()
                        .requestMatchers(HttpMethod.GET, "/statistic/brand").permitAll()
                        .requestMatchers(HttpMethod.GET, "/statistic/model").permitAll()
                        .requestMatchers(HttpMethod.GET, "/statistic/vehicle").permitAll()
                        .requestMatchers(HttpMethod.GET, "/statistic/year").permitAll()
                        .requestMatchers(HttpMethod.POST, "/rating").authenticated()
                        .requestMatchers(HttpMethod.GET, "/rating").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/rating/{id}").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/rating/{id}").authenticated()
                        .requestMatchers(HttpMethod.GET, "/price/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/price/store").permitAll()
                        .requestMatchers("/ws-notification/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers("swagger-resources/**").permitAll()
                        .requestMatchers("/swagger-resources").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
