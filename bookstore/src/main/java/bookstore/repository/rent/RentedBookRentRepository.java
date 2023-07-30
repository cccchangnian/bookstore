package bookstore.repository.rent;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import bookstore.domain.rent.RentedBookRent;

@Repository
public interface RentedBookRentRepository extends PagingAndSortingRepository<RentedBookRent, Long> {

}
