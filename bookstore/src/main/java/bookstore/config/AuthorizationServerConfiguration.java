package bookstore.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import bookstore.security.CustomUserDetailsService;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		// use jdbc service
		clients.withClientDetails(new JdbcClientDetailsService(dataSource));
	}
	
	@Bean
	@Primary
	public AuthorizationServerTokenServices authorizationTokenService() {
		// token service
		DefaultTokenServices tokenServices = new DefaultTokenServices();
		// jdbc client service
		ClientDetailsService clientService = new JdbcClientDetailsService(dataSource);
		tokenServices.setClientDetailsService(clientService);
		tokenServices.setSupportRefreshToken(true);
		// jdbc token store
		TokenStore tokenStore = new JdbcTokenStore(dataSource);
		tokenServices.setTokenStore(tokenStore);
		tokenServices.setSupportRefreshToken(true);
		tokenServices.setAccessTokenValiditySeconds(7200);
		tokenServices.setRefreshTokenValiditySeconds(86400);
		return tokenServices;
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		// User Details
		endpoints.userDetailsService(userDetailsService);
		// token management service
		endpoints.tokenServices(authorizationTokenService());
		// password mode need
		endpoints.authenticationManager(authenticationManager);
		// code mode need
		endpoints.authorizationCodeServices(new JdbcAuthorizationCodeServices(dataSource));
		// allow post
		endpoints.allowedTokenEndpointRequestMethods(HttpMethod.POST);
		// approval
		ApprovalStore approvalStore = new JdbcApprovalStore(dataSource);
		endpoints.approvalStore(approvalStore);
	}
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) {
		security
			.tokenKeyAccess("permitAll()")
			.checkTokenAccess("permitAll()")
			.allowFormAuthenticationForClients();
	}
}
