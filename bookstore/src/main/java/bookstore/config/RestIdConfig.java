package bookstore.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import bookstore.domain.rent.RentedBookCategory;
import bookstore.domain.rent.RentedBookInventory;
import bookstore.domain.rent.RentedBookOrder;
//import bookstore.domain.reserve.BookReserve;
import bookstore.domain.sale.BookCategory;
import bookstore.domain.sale.BookInventory;
import bookstore.domain.sale.BookOrder;
import bookstore.domain.user.Authority;
import bookstore.domain.user.User;

@Configuration
public class RestIdConfig implements RepositoryRestConfigurer {

	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
		config
			.exposeIdsFor(
					User.class, Authority.class,
					BookCategory.class, RentedBookCategory.class,
					BookInventory.class, RentedBookInventory.class,
					BookOrder.class, RentedBookOrder.class
					//BookReserve.class
					);
	}
}
