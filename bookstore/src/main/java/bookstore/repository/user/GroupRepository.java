package bookstore.repository.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import bookstore.domain.user.Group;

@Repository
public interface GroupRepository extends CrudRepository<Group, Long> {

}
