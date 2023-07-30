package bookstore.repository.sale;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import bookstore.domain.sale.BookCategory;

@Repository
public interface BookCategoryRepository extends CrudRepository<BookCategory, Long> {

}
