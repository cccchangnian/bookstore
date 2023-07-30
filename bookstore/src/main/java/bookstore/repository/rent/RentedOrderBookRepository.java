package bookstore.repository.rent;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import bookstore.domain.rent.RentedOrderBook;

@Repository
public interface RentedOrderBookRepository extends PagingAndSortingRepository<RentedOrderBook, Long> {

}
