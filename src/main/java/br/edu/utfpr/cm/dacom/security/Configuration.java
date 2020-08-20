package br.edu.utfpr.cm.dacom.security;



import java.io.Serializable;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@EnableWebSecurity
public class Configuration extends WebSecurityConfigurerAdapter implements Serializable {

	@Override
    public void configure(HttpSecurity http) throws Exception {
		/*
        http.authorizeRequests()
        		.antMatchers("/javax.faces.resource/**").permitAll()
		        .antMatchers("/index.xhtml").hasAnyRole("ADMIN", "USUARIO").anyRequest().authenticated()
        		.and()
        		.exceptionHandling().accessDeniedPage("/login");
        http.formLogin().permitAll();
        http.logout().logoutSuccessUrl("/login");
        http.csrf().disable();

       */
		http
		.authorizeRequests()
			.antMatchers("/javax.faces.resource/**").permitAll()
			.antMatchers("/index.xhtml", "/").hasAnyRole("ADMIN", "USUARIO")
			.anyRequest().authenticated()
			.and()
		.formLogin().permitAll()
			.and()
		.logout()
			.permitAll()
			.logoutUrl("/j_spring_security_logout")
			.logoutSuccessUrl("/logout.xhtml")
			.invalidateHttpSession(true)
			.deleteCookies("JSESSIONID")
			.and()
		.exceptionHandling().accessDeniedPage("/403.xhtml")
			.and()
		.csrf().disable();
		
		http.headers().frameOptions().sameOrigin();
		
    }
	
	@Autowired
	 public void configureGlobal(AuthenticationManagerBuilder auth)throws Exception {
	    auth.inMemoryAuthentication()
	    			.withUser("usuario").password("{noop}123456").roles("USUARIO")
	    			.and()
	    			.withUser("admin").password("{noop}admin").roles("ADMIN", "USUARIO");
	}	
}
