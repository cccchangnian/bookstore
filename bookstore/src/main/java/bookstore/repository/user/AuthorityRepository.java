package bookstore.repository.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import bookstore.domain.user.Authority;

@Repository
public interface AuthorityRepository extends CrudRepository<Authority, Long> {

}
