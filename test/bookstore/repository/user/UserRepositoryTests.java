package bookstore.repository.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import bookstore.domain.user.User;

@SpringBootTest
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;
    
    @Test
    void testInsert() {
        String username = "jinhaoran";
        String password = "123456";
        String email = "942345433@qq.com";
        String phone = "19851920392";
        String location = "常州大学";

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhone(phone);
        user.setLocation(location);

        userRepository.save(user);
    }
}
