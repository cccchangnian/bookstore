package bookstore.repository.user;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import bookstore.domain.user.User;

//Spring Data中，Repository接口不需要手动实现，Spring Data会根据接口的定义自动生成Repository的实现类。
//继承自CrudRepository 会自动生成findById deleteById save等方法
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	Optional<User> findByUsername(String username);
}
