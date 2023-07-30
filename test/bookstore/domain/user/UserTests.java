package bookstore.domain.user;

import org.junit.jupiter.api.Test;

import javax.validation.Valid;

import org.junit.jupiter.api.Assertions;

import bookstore.domain.user.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserTests {

    // 一个User领域类的信息
    @Test
    void testUser() {
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

        Assertions.assertEquals(user.getUsername(), username);
        Assertions.assertEquals(user.getPassword(), password);
        Assertions.assertEquals(user.getEmail(), email);
        Assertions.assertEquals(user.getPhone(), phone);
        Assertions.assertEquals(user.getLocation(), location);
    }
}
