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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.alibaba.fastjson.JSONObject;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
public class UserTests {
    
    @RegisterExtension
	public RestDocumentationExtension restDocumentation = new RestDocumentationExtension("target/generated-snippets/user");
	
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext context;

    // @Autowired
    // private RestTemplate restTemplate;

    // token
    private String token = "b2a7180a-8bda-4b69-8812-3e0c1a8d7dc9";

	@BeforeEach
	public void setUp(RestDocumentationContextProvider restDocumentation) {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
					.apply(documentationConfiguration(restDocumentation)
							.snippets().withEncoding("UTF-8"))
					.build();
	}

    // 测试之前，获取一位用户的token
    @Test
	void contextLoads() {
        // MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        // postParameters.add("client_id", "bookstore");
        // postParameters.add("client_secret", "123456");
        // postParameters.add("grant_type", "password");
        // postParameters.add("username", "oauth_test");
        // postParameters.add("password", "123456");

        // HttpHeaders headers = new HttpHeaders();
        // headers.add("Content-Type", "application/x-www-form-urlencoded");

        // HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(postParameters, headers);
	}

    // 创建一个用户
    @Test
    void postUser() throws Exception {
        // 使用uuid来避免用户名重复的问题
        Map<String, Object> map = new HashMap<>();
        map.put("username", UUID.randomUUID().toString());
        map.put("password", "123456");
        String content = JSONObject.toJSONString(map);

        this.mockMvc
            .perform(post("/api/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(content))
            .andExpect(status().isCreated())
            .andDo(document("post-user"));
    }

    // 获取所有用户
    @Test
    void getUsers() throws Exception {
        this.mockMvc
            .perform(get("/api/users").header("Bearer", token))
            .andExpect(status().isOk())
            .andDo(document("get-users"));
    }

    // 获取单个用户
    @Test
    void getUser() throws Exception {
        this.mockMvc
            .perform(get("/api/users/{id}", 1).header("Bearer", token))
            .andExpect(status().isOk())
            .andDo(document("get-user"));
    }

    // 修改某个用户
    @Test
    void putUser() throws Exception {
        Map<String, String> map = new HashMap<>();
        // put请求需要携带所有必要的参数
        // 将用户名设置为
        map.put("username", UUID.randomUUID().toString());
        map.put("password", "123456");
        String content = JSONObject.toJSONString(map);

        this.mockMvc
            .perform(put("/api/users/{id}", 1).header("Bearer", token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(content))
            .andExpect(status().isNoContent())
            .andDo(document("put-user"));
    }

    // 部分修改某个用户
    @Test
    void patchUser() throws Exception {
        Map<String, String> map = new HashMap<>();
        // 将用户名修改
        map.put("username", UUID.randomUUID().toString());
        String content = JSONObject.toJSONString(map);

        this.mockMvc
            .perform(patch("/api/users/{id}", 1).header("Bearer", token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(content))
            .andExpect(status().isNoContent())
            .andDo(document("patch-user"));
    }

    // 删除某个用户，这里不删除，因为后续还会用到
    // @Test
    void deleteUser() throws Exception {
        this.mockMvc
            .perform(delete("/api/users/{id}", 1).header("Bearer", token))
            .andExpect(status().isNoContent())
            .andDo(document("delete-user"));
    }
}
