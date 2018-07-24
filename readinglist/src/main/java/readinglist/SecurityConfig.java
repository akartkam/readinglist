package readinglist;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	  @Autowired
	  private ReaderRepository readerRepository;
	  
	  @Override
	  protected void configure(HttpSecurity http) throws Exception {
	    http
	      .authorizeRequests()
	        .antMatchers("/").access("hasRole('READER')")
	        //.antMatchers("/").permitAll()
	        .antMatchers("/**").permitAll()
	      .and()
	      //.csrf().disable()
	      .formLogin()
	        .loginPage("/login")
	        .failureUrl("/login?error=true");
	  }
	  
	  @Override
	  protected void configure(
	              AuthenticationManagerBuilder auth) throws Exception {
	    auth
	      .userDetailsService(userDetailsService());
	  }
	  
	  @Bean
	  public UserDetailsService userDetailsService() {
	    return new UserDetailsService() {
	      @Override
	      public UserDetails loadUserByUsername(String username)
	          throws UsernameNotFoundException {
    	
	        UserDetails userDetails = (UserDetails) readerRepository.findAll().get(0);
	        String qqq = userDetails.getAuthorities().toString();
	        if (userDetails != null) {
	          return userDetails;
	        }
	        throw new UsernameNotFoundException("User '" + username + "' not found.");
	      }
	    };
	  }
  
  
}