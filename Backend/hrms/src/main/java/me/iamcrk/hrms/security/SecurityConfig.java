package me.iamcrk.hrms.security;

import me.iamcrk.hrms.jwt.JwtAuthenticationFilter;
import me.iamcrk.hrms.jwt.JwtService;
import me.iamcrk.hrms.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
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

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           JwtAuthenticationFilter jwtAuthenticationFilter,
                                           UserService userService) throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(req ->
                        req.requestMatchers("/api/clock", "/api/user/", "/images/**",
                                        "/api/employee/", "/api/auth/active/**", "/api/auth/all",
                                        "/api/auth/login",
                                        "/api/department/**", "/api/designation/**",
                                        "/api/holiday",
                                        "/api/advanceSalary/**"
                                )
                                .permitAll()
//                                .requestMatchers()
//                                .hasRole("EMPLOYEE")
                                .requestMatchers(
                                       "/api/leave/byDept",
                                        "/api/admin/register"
                                )
                                .hasRole("DEPARTMENT_HEAD")
                                .requestMatchers(
                                        "/api/user/all",
                                        "/api/employee/all",
                                        "/api/employee/profile",
                                        "/api/leave/byEmp",
                                        "/api/attendance/**",
                                        "/api/leave/**",
                                        "/api/accountant/**",
                                        "/api/deptHead/**",
                                        "/api/salary/**")
                                .hasAnyRole( "EMPLOYEE","DEPARTMENT_HEAD", "ACCOUNTANT", "ADMIN")
                                .requestMatchers(
                                        "/api/employee/{id}/salary")
                                .hasRole("ACCOUNTANT")
                                .requestMatchers("/api/admin/end/{id}", "/api/deptHead/end/{id}")
                                .hasRole("ADMIN")
                                .requestMatchers(
                                        "/api/auth/logout")
                                .authenticated()
                                .anyRequest().authenticated()
                )
                .userDetailsService(userService)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtService jwtService, UserService userService) {
        return new JwtAuthenticationFilter(jwtService, userService);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200", "http://127.0.0.1:4200"));
        configuration.setAllowedMethods(List.of("GET", "POST", "DELETE", "PUT", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Cache_Control", "Content-type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
