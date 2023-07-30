package bookstore.handler.user;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.crypto.password.PasswordEncoder;

import bookstore.domain.user.User;
import bookstore.repository.user.UserRepository;

//这个类是一个Repository事件处理器，并指定了要处理的实体类为User。也就是说，这个处理器将监听与User实体相关的持久化事件。
//@RepositoryEventHandler注解来标记为一个Spring Data JPA事件处理器，它可以监听和处理特定实体的持久化事件
//项目运行时，Spring框架会自动扫描并注册UserEventHandler作为一个事件监听器。当持久化操作（例如保存或更新User实体）发生时，Spring Data JPA会触发相应的持久化事件，并自动调用UserEventHandler中对应的方法
//当Handler处理器的方法被调用时，通常会在方法执行之前开启一个事务（如果尚未存在事务），然后在方法执行结束后根据方法执行结果决定是提交事务还是回滚事务。
@RepositoryEventHandler(User.class)
public class UserEventHandler {

	@Autowired
	private PasswordEncoder passwordEncoder; //Spring Security提供的密码加密类
	
	@Autowired
	private UserRepository userRepo; //用于提供对user表的增删改查
	
	@PersistenceContext
	private EntityManager entityManager; //EntityManager是JPA中的核心接口，EntityManager负责实体对象的创建、更新、删除、查询等操作
	
	//方法会自动调用
	@HandleBeforeSave
	public void handleUserBeforeSave(User user) {
		// 清除掉缓存中的用户 不然后续密码比对会出错
		entityManager.detach(user);
		// 获取原来的用户
		Optional<User> optional = userRepo.findByUsername(user.getUsername());
		if (optional.isPresent()) {
			User origin = optional.get();
			// 如果密码不一致 注意这里提交上来的是明文 原来的是密文的
			if (!origin.getPassword().equals(user.getPassword())) {
				user.setPassword(passwordEncoder.encode(user.getPassword()));
			}
		}
	}
	
	//方法会自动调用
	@HandleBeforeCreate
	public void handleUserBeforeCreate(User user) {
		// 在用户创建并保存到数据库之前会先对明文密码进行加密
		user.setPassword(passwordEncoder.encode(user.getPassword()));
	}
}
