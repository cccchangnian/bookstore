package bookstore.repository.rent;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import bookstore.domain.rent.RentedBookInventory;

@Repository
public interface RentedBookInventoryRepository extends PagingAndSortingRepository<RentedBookInventory, Long> {

	Page<RentedBookInventory> findByCategoryId(@Param("categoryId") Long categoryId, Pageable p);
}
