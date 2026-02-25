package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      http
        .authorizeHttpRequests(auth -> auth
          .requestMatchers("/public", "/public/**", "/css/**", "/js/**", "/images/**", "/error", "/login").permitAll()
          .anyRequest().authenticated()
        )
        .formLogin(form -> form
          .loginPage("/login")
          .permitAll()
          .defaultSuccessUrl("/", true)
        )
        .logout(logout -> logout
          .logoutSuccessUrl("/public")
          .permitAll()
        );

      return http.build();
    }

  @Bean
  UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
    UserDetails user = User.builder()
      .username("user")
      .password(passwordEncoder.encode("password"))
      .roles("USER")
      .build();

    return new InMemoryUserDetailsManager(user);
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
