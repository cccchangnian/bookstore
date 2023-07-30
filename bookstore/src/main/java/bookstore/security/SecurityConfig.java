package bookstore.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Autowired
	private DataSource dataSource;	

	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.userDetailsService(userDetailsService)
			.passwordEncoder(encoder());
		auth
			.jdbcAuthentication()
			.dataSource(dataSource)
			.usersByUsernameQuery(
					"select username, password, enabled from users where username = ?"
					)
			.authoritiesByUsernameQuery(
					"select username, authority from authorities where user_id in (select user_id from users where username = ?)"
					)
			.groupAuthoritiesByUsername(
					"select g.id, g.group_name, ga.authority from user_groups g, group_members gm, group_authorities ga " +
					"where gm.user_id in (select user_id from users where username = ?) and g.id = ga.group_id and g.id = gm.group_id"
					);
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.cors()
			.and()
			.authorizeRequests()
			.antMatchers(HttpMethod.POST, "/api/login").permitAll()
			.antMatchers(HttpMethod.POST, "/api/users").permitAll()
			.antMatchers(HttpMethod.GET, "/api/users/{username}").permitAll()
			.antMatchers("/api/users/{username}").authenticated()
			.antMatchers(HttpMethod.GET, "/api/users").hasRole("ADMIN")	
			.antMatchers("/api/authorities/**").hasRole("ADMIN")
			.antMatchers("/api/groups/**").hasRole("ADMIN")
			.antMatchers("/api/groupAuthorities/**").hasRole("ADMIN")
			.antMatchers(HttpMethod.GET, "/api/bookInventories/**").permitAll()
			.antMatchers("/api/booInventories/**").hasRole("ADMIN")
			.antMatchers(HttpMethod.GET, "/api/bookCategories/**").permitAll()
			.antMatchers("/api/bookCategories/**").hasRole("ADMIN")
			.antMatchers(HttpMethod.GET, "/api/books/**").permitAll()
			.antMatchers("/api/books/**").hasRole("ADMIN")
			.antMatchers(HttpMethod.DELETE, "/api/bookOrders/**").denyAll()
			.antMatchers(HttpMethod.PATCH, "/api/bookOrders/**").denyAll()
			.antMatchers(HttpMethod.PUT, "/api/bookOrders/**").denyAll()
			.antMatchers("/api/bookOrders/**").authenticated()
			.antMatchers("/api/bookSales/**").hasRole("ADMIN")
			.antMatchers(HttpMethod.GET, "/api/rentedBookInventories/**").permitAll()
			.antMatchers("/api/rentedBookInventories/**").hasRole("ADMIN")
			.antMatchers(HttpMethod.GET, "/api/rentedBookCategories/**").permitAll()
			.antMatchers("/api/rentedBookCategories/**").hasRole("ADMIN")
			.antMatchers(HttpMethod.GET, "/api/rentedBooks/**").permitAll()
			.antMatchers("/api/rentedBooks/**").hasRole("ADMIN")
			.antMatchers("/api/rentedBookOrders/**").authenticated()
			.antMatchers(HttpMethod.DELETE, "/api/rentedBookOrders/**").denyAll()
			.antMatchers(HttpMethod.GET, "/api/rentedBookOrders/**").authenticated()
			.antMatchers("/api/rentedBookOrders/**").denyAll()
			.antMatchers("/api/bookReserves/**").authenticated()
			.antMatchers("/api/booReserveRecords/**").hasRole("ADMIN")
			.and()
			.formLogin().disable()
			.httpBasic()
			.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
			
	}
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
