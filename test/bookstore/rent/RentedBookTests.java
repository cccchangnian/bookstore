package bookstore.rent;

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

import com.alibaba.fastjson.JSONObject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
public class RentedBookTests {
    
    @RegisterExtension
	public RestDocumentationExtension restDocumentation = new RestDocumentationExtension("target/generated-snippets/rent/book");
	
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext context;

	// token
    private String token = "b2a7180a-8bda-4b69-8812-3e0c1a8d7dc9";

	// 图书id，在post的是否赋值
	private String id = "RB176456388094464000";
	
	@BeforeEach
	public void setUp(RestDocumentationContextProvider restDocumentation) {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
					.apply(documentationConfiguration(restDocumentation)
							.snippets().withEncoding("UTF-8"))
					.build();
	}
	
	// 测试添加一本图书 有数据
	@Order(1)
	@Test
	void postBook() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("id", id);
		map.put("name", "book1");
		map.put("inventory", "http://127.0.0.1:9001/api/rentedBookInventories/1");

		String content = JSONObject.toJSONString(map);

		this.mockMvc
			.perform(post("/api/rentedBooks").header("Bearer", token)
				.contentType(MediaType.APPLICATION_JSON)
				.content(content))
			.andExpect(status().isCreated())
			.andDo(document("post-rent-book"));
	}

	// 测试获取所有图书 不加参数
	@Order(2)
	@Test
	void getBooks() throws Exception {
		this.mockMvc
			.perform(get("/api/rentedBooks"))
			.andExpect(status().isOk())
			.andDo(document("get-rent-books"));
	}
	
	// 测试获取一本图书 不加参数
	@Order(3)
	@Test
	void getBook() throws Exception {
		this.mockMvc
			.perform(get("/api/rentedBooks/{id}", id))
			.andExpect(status().isOk())
			.andDo(document("get-rent-book"));
	}

	// 测试更新一本图书
	@Order(4)
	@Test
	void putBook() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("name", "book2");
		map.put("inventory", "http://127.0.0.1:9001/api/rentedBookInventories/1");
		String content = JSONObject.toJSONString(map);
		
		this.mockMvc
			.perform(put("/api/rentedBooks/{id}", id).header("Bearer", token)
				.contentType(MediaType.APPLICATION_JSON)
				.content(content))
			.andExpect(status().isNoContent())
			.andDo(document("put-rent-book"));
	}

	// 测试部分更新一本图书
	@Order(5)
	@Test
	void patchBook() throws Exception {
		Map<String, String> map = new HashMap<>();
		map.put("name", "book3");
		String content = JSONObject.toJSONString(map);

		this.mockMvc
			.perform(patch("/api/rentedBooks/{id}", token).header("Bearer", token)
				.contentType(MediaType.APPLICATION_JSON)
				.content(content))
			.andExpect(status().isNoContent())
			.andDo(document("patch-rent-book"));
	}

	// 测试删除一本图书
	@Order(6)
	@Test
	void deleteBook() throws Exception {
		this.mockMvc
			.perform(delete("/api/rentedBooks/{id}", id).header("Bearer", token))
			.andExpect(status().isNoContent())
			.andDo(document("delete-rent-book"));
	}
}
