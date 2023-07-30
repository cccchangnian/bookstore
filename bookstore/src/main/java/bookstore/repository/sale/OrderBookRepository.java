package bookstore.repository.sale;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import bookstore.domain.sale.OrderBook;

@Repository
public interface OrderBookRepository extends PagingAndSortingRepository<OrderBook, Long> {

}
