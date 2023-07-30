package bookstore.repository.rent;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import bookstore.domain.rent.RentedBookCategory;

@Repository
public interface RentedBookCategoryRepository extends CrudRepository<RentedBookCategory, Long> {

}
