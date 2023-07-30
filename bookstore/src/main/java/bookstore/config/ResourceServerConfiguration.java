package bookstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	// the name of resource id
	public static final String RESOURCE_ID = "bookstore";
	
	@Bean
	public ResourceServerTokenServices resourceTokenService() {
		RemoteTokenServices service = new RemoteTokenServices();
		service.setCheckTokenEndpointUrl("http://127.0.0.1:9001/oauth/check_token");
		service.setClientId("bookstore");
		service.setClientSecret("123456");
		return service;
	}
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.resourceId(RESOURCE_ID)
			.tokenServices(resourceTokenService())
			.stateless(true);
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
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
			.antMatchers(HttpMethod.GET, "/api/rentedBookRents/**").authenticated()
			.antMatchers("/api/rentedBookRents/**").denyAll()
			.antMatchers("/api/rentedBookOrders/**").authenticated()
			.antMatchers(HttpMethod.DELETE, "/api/rentedBookOrders/**").denyAll()
			.antMatchers("/api/bookReserves/**").authenticated()
			.antMatchers("/api/booReserveRecords/**").hasRole("ADMIN")
			.and()
			.formLogin().disable()
			.httpBasic()
			.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
}