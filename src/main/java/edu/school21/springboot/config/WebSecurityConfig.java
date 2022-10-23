package edu.school21.springboot.config;

import edu.school21.springboot.model.type.UserRole;
import edu.school21.springboot.repository.UserRepository;
import edu.school21.springboot.security.CustomAuthenticationFailureHandler;
import edu.school21.springboot.security.CustomAuthenticationSuccessHandler;
import edu.school21.springboot.security.CustomUserDetailsService;
import edu.school21.springboot.support.TransactionalHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Value("${edu.school21.spring-boot.remember-me.key}")
	private String secretKey;
	@Value("${edu.school21.spring-boot.remember-me.validity-time}")
	private Integer validityTime;

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TransactionalHelper transactionalHelper;

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder(12);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.csrf()
					.ignoringAntMatchers("/signUp", "/signIn", "/ws/**")
					.and()
				.authorizeRequests()
					.antMatchers("/admin/**").hasAuthority(UserRole.ROLE_ADMIN.name())
					.antMatchers("/favicon.ico", "/signUp", "/signIn", "/logout", "/confirm/**").permitAll()
					.anyRequest().authenticated()
					.and()
				.formLogin()
					.loginPage("/signIn") // GET
					.loginProcessingUrl("/signIn") // POST
					.failureHandler(new CustomAuthenticationFailureHandler())
					.successHandler(new CustomAuthenticationSuccessHandler(userRepository, transactionalHelper))
					.usernameParameter("email")
					.passwordParameter("password") // Дефолтное название параметра в html форме, но указан явно
					.and()
				.logout()
					.logoutUrl("/logout")
					.logoutSuccessUrl("/signIn")
					.clearAuthentication(true)
					.deleteCookies("remember-me", "JSESSIONID")
					.and()
				.rememberMe()
					.key(secretKey)
					.tokenValiditySeconds(validityTime)
					.rememberMeParameter("remember-me"); // Дефолтное название параметра в html форме, но указан явно
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(new CustomUserDetailsService(userRepository, transactionalHelper))
				.passwordEncoder(bCryptPasswordEncoder());
	}
}
