package it.h2i.idservice.accountablemodel.security;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;



@Configuration
@EnableWebSecurity
public class AppConfig extends WebSecurityConfigurerAdapter {


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.
		authorizeRequests()
	 //.antMatchers("/allUser").hasRole("ADMIN")
		.antMatchers("/").permitAll()
		.antMatchers("/login").permitAll()
		.antMatchers("/loginEffettuata").authenticated().and()
		.formLogin().loginPage("/login").failureHandler(CustomAuthenticationFailureHandler()).defaultSuccessUrl("/loginEffettuata", true)
		.usernameParameter("username")
		.passwordParameter("password")
		.and().csrf().disable();


	}


	@Override
	public void configure(AuthenticationManagerBuilder builder)
			throws Exception {
		builder.userDetailsService(UserDetailsService());
	}

	@Bean
	public MyUserDetailsService UserDetailsService() {
		return new MyUserDetailsService();
	}; 

	@Bean
	public CustomAuthenticationFailureHandler CustomAuthenticationFailureHandler() {
		return new CustomAuthenticationFailureHandler();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder pe =new BCryptPasswordEncoder();
		return pe;
	}
}
