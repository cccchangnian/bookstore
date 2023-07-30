package bookstore.domain.rent.projection;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.rest.core.config.Projection;

import bookstore.domain.rent.RentedBookOrder;
import bookstore.domain.user.User;

@Projection(name = "inlineOwner", types = { RentedBookOrder.class })
public interface RentedBookOrderInlineOwner {

	String getId();
	
	BigDecimal getTotalPrice();

	BigDecimal getPrice();

	String getLocation();

	String getPhone();
	
	Date getCreatedTime();
	
	User getOwner();
}
