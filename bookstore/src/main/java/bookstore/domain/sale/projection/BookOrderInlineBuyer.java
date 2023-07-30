package bookstore.domain.sale.projection;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.rest.core.config.Projection;

import bookstore.domain.sale.BookOrder;
import bookstore.domain.user.User;

@Projection(name = "inlineBuyer", types = { BookOrder.class })
public interface BookOrderInlineBuyer {

	String getId();
	
	BigDecimal getTotalPrice();

	String getPhone();
	
	String getLocation();
	
	Date getCreatedTime();
	
	User getBuyer();
}
