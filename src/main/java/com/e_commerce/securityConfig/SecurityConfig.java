package com.e_commerce.securityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	public static PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public UserDetailsService getUserDetailsService()
	{
		return new CustomeUserDetailsService();
	}
	@Autowired
	private AuthenticationSuccessHandler authenticationSuccessHandler;
//	@Autowired
//	@Lazy
//	private AuthFailureHandlerImpl authFailureHandlerImpl;
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider()
	{
		DaoAuthenticationProvider daoAuthenticationProvider= new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(getUserDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
	}
	
//	@Bean
//	public AuthenticationSuccessHandler successHandler() {
//	    SimpleUrlAuthenticationSuccessHandler handler = new SimpleUrlAuthenticationSuccessHandler();
//	    handler.setUseReferer(true);
//	    return handler;
//	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception
	{
//		httpSecurity.csrf(csrf->csrf.disable()).cors(cors->cors.disable())
//		.authorizeHttpRequests(req->req.requestMatchers("/user/**").hasRole("USER")
//		.requestMatchers("/admin/**").hasRole("ADMIN")
//		.requestMatchers("/**").permitAll())
//		.formLogin(form->form.loginPage("/login")
//				.loginProcessingUrl("/login")
////				.defaultSuccessUrl("/")
//				.failureHandler(authFailureHandlerImpl)
//				.successHandler(authenticationSuccessHandler))
//		.logout(logout->logout.permitAll());
		
		httpSecurity.csrf(c -> c.disable())
				.authorizeHttpRequests(auth -> auth.requestMatchers("/admin/**").hasRole("ADMIN").requestMatchers("/user/**").hasRole("USER")
				.requestMatchers("/registration", "/", "/home/**").permitAll()
				.requestMatchers("/resources/**" , "/img/**", "/static/**", "/css/**", "/assets/**", "/js/**", "/images/**", "/icon/**", "/fonts/**", "/plugins/**", "/productimages/**", "/Reviewimages/**", "/categoryimage/**").permitAll()
				.anyRequest().authenticated())
				
		.formLogin(form -> form.loginPage("/login")   // By this page we can refer to the user when some page have login required or that page also which secured or not url mapping !
						.loginProcessingUrl("/login").permitAll()						
						//.defaultSuccessUrl("/home", true).permitAll())
//						.failureHandler(authFailureHandlerImpl)
						.successHandler(authenticationSuccessHandler)) 
		// This is for stay contacted at the same page after successfully login!
		
		.logout(form -> form.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
				.clearAuthentication(true)
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			//	.logoutSuccessUrl("/login?logout").permitAll());         // By "Default" logout page destination !
				.logoutSuccessUrl("/").permitAll())					     // By "Custom" logout page destination !
			// 	 sample exception handling customization
				.exceptionHandling((exce) -> exce .accessDeniedPage("/errors/access-denied"));
				 return httpSecurity.build();					    
	}
}
