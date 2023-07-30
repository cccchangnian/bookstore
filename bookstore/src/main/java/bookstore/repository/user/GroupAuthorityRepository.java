package bookstore.repository.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import bookstore.domain.user.GroupAuthority;

@Repository
public interface GroupAuthorityRepository extends CrudRepository<GroupAuthority, Long> {

}
