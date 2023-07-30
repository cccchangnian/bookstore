package bookstore.domain.reserve.projection;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.rest.core.config.Projection;

import bookstore.domain.reserve.BookReserve;
import bookstore.domain.user.User;

@Projection(name = "inlineUser", types = { BookReserve.class })
public interface BookReserveInlineUser {
    
    String getId();

    boolean getIsCompleted();

    BigDecimal getPrePrice();

    BigDecimal getTotalPrice();

    String getLocation();

    int getAmount();

    String getPhone();

    Date getCreatedTime();

    User getUser();
}
