package bookstore.repository.sale;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import bookstore.domain.sale.BookInventory;

@Repository
public interface BookInventoryRepository extends PagingAndSortingRepository<BookInventory, Long> {

	Page<BookInventory> findByCategoryId(@Param("categoryId") Long categoryId, Pageable p);
}
