package bookstore.repository.sale;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import bookstore.domain.sale.BookOrder;

@Repository
public interface BookOrderRepository extends PagingAndSortingRepository<BookOrder, String> {

	Page<BookOrder> findByBuyerId(@Param("id") Long id, Pageable p);
}
