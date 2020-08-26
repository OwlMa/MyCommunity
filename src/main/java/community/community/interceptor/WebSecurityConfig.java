package community.community.interceptor;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/publish", "/publish/**", "/delete/articles/**").authenticated()
                .anyRequest().permitAll();
        http.formLogin().loginProcessingUrl("/");
        http.csrf().disable();
        http.headers().frameOptions().sameOrigin();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).withUser("matianyang").password("$2b$10$ewzNwazJKFEbMMQekMy0KOlSlkm0i6W0fhhdSInkADVgLF0n3JqLe").roles("admin");
    }
}
