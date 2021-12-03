package com.configs;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
            .dataSource(dataSource)
            .passwordEncoder(encoder());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .and()
                    .anonymous()
                .and()
                    .csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/login/**")           .anonymous()
                    .antMatchers("/login/**")           .permitAll()
                    .antMatchers("/admin/**")           .hasRole("ADMIN")
///////////////////////////////////////////   ACCOUNT   ////////////////////////////////////////////////////////////////
                    .antMatchers("/account/create/**")  .hasAnyRole("ROLE_ADMIN")
                    .antMatchers("/account/get/**")     .hasAnyRole("ROLE_ADMIN")
                    .antMatchers("/account/delete/**")  .hasAnyRole("ROLE_ADMIN")
                    .antMatchers("/account/update/**")  .hasAnyRole("ROLE_ADMIN")
///////////////////////////////////////////   EDU   ////////////////////////////////////////////////////////////////////
                    .antMatchers("/edu/**")             .authenticated()
///////////////////////////////////////////   DEBUG   //////////////////////////////////////////////////////////////////
                    .antMatchers("/user/**")            .authenticated()
                    .anyRequest()                                   .permitAll()
                .and()
                    .formLogin()
                    .defaultSuccessUrl("/user", true)
                .and()
                    .logout()
                    .logoutSuccessUrl("/");
    }


    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
