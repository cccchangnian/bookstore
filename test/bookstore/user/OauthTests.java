package bookstore.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import bookstore.domain.user.User;
import bookstore.repository.user.UserRepository;
@SpringBootTest
public class OauthTests {
    
    @RegisterExtension
	public RestDocumentationExtension restDocumentation = new RestDocumentationExtension("target/generated-snippets/oauth");
	
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext context;

    @Autowired
    private UserRepository userRepository;
	
	@BeforeEach
	public void setUp(RestDocumentationContextProvider restDocumentation) {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
					.apply(documentationConfiguration(restDocumentation)
							.snippets().withEncoding("UTF-8"))
					.build();
	}

    // 进行测试之前先创建一个用户
    @Test
	void contextLoads() {
        // User user = new User();
        // user.setUsername("oauth_test");
        // user.setPassword("123456");
		// userRepository.save(user);
	}

    // 测试获取token
    @Test
    void getToken() throws Exception {
        // // 再发送请求
        // Map<String, String> map = new HashMap<>();
        // map.put("client_id", "bookstore");
        // map.put("client_secret", "123456");
        // map.put("grant_type", "password");
        // map.put("username", "oauth_test");
        // map.put("password", "123456");
        // String content = JSONObject.toJSONString(map);

        // this.mockMvc
        //     .perform(post("/oauth/token")
        //         .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        //         .content(content))
        //     .andExpect(status().isOk())
        //     .andDo(document("get-token"));
    }
}
