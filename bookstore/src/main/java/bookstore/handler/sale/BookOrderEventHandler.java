package bookstore.handler.sale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

import bookstore.domain.sale.BookOrder;
import bookstore.repository.sale.OrderBookRepository;

import javax.annotation.Resource;

@RepositoryEventHandler(BookOrder.class)
public class BookOrderEventHandler {

	@Autowired
	//@Resource
	private OrderBookRepository orderBookRepository;
	
	// 订单创建后 清空购物车
	@HandleAfterCreate
	public void handleOrderBookAfterCreate(BookOrder bookOrder) {
		
	}
}
