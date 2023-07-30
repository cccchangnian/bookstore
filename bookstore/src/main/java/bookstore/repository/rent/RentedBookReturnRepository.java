package bookstore.repository.rent;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import bookstore.domain.rent.RentedBookReturn;

@Repository
public interface RentedBookReturnRepository extends PagingAndSortingRepository<RentedBookReturn, Long> {

}
