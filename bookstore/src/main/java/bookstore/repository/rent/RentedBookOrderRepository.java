package bookstore.repository.rent;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import bookstore.domain.rent.RentedBookOrder;

@Repository
public interface RentedBookOrderRepository extends PagingAndSortingRepository<RentedBookOrder, String> {

	Page<RentedBookOrder> findByOwnerId(@Param("ownerId") Long id, Pageable p);
}
