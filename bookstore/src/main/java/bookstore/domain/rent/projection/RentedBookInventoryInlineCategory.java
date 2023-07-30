package bookstore.domain.rent.projection;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.rest.core.config.Projection;

import bookstore.domain.rent.RentedBookCategory;
import bookstore.domain.rent.RentedBookInventory;


@Projection(name = "inlineCategory", types = { RentedBookInventory.class })
public interface RentedBookInventoryInlineCategory {

	long getId();
	
	String getBookName();
	
	BigDecimal getPrice();
	
	String getIcon();
	
	double getRating();
	
	String getDescription();
	
	String getAuthor();
	
	long getAmount();
	
	RentedBookCategory getCategory();
	
	Date getCreatedTime();
}
