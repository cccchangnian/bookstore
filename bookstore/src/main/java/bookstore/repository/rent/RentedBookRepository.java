package bookstore.repository.rent;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import bookstore.domain.rent.RentedBook;

@Repository
public interface RentedBookRepository extends PagingAndSortingRepository<RentedBook, String> {

}
