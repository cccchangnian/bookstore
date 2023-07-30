package bookstore.repository.sale;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import bookstore.domain.sale.BookSale;

@Repository
public interface BookSaleRepository extends PagingAndSortingRepository<BookSale, Long> {

}
