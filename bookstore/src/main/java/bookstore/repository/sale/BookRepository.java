package bookstore.repository.sale;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import bookstore.domain.sale.Book;

@Repository
public interface BookRepository extends PagingAndSortingRepository<Book, String> {

}
