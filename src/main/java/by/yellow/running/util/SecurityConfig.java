package by.yellow.running.util;

import by.yellow.running.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public CustomUserDetailsService customUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/registration").permitAll()
                .antMatchers(HttpMethod.GET, "/users/{\\d+}/*/*/*").hasAnyRole("user", "admin")
                .antMatchers(HttpMethod.POST, "/users/{\\d+}/*/*/*").hasAnyRole("admin", "user")
                .antMatchers(HttpMethod.PUT, "/users/{\\d+}/*/*/*").hasAnyRole("admin", "user")
                .antMatchers("/users/").hasRole("admin")
                .antMatchers(HttpMethod.DELETE, "/users/**").hasRole("admin")
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .formLogin().disable();
    }
}
