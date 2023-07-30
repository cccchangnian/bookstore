package bookstore.rent;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RentedBookShoppingCartTests {
    
    @RegisterExtension
	public RestDocumentationExtension restDocumentation = new RestDocumentationExtension("target/generated-snippets/rent/book-shopping-cart");
	
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext context;

	// token
	private String token = "b2a7180a-8bda-4b69-8812-3e0c1a8d7dc9";
	
	@BeforeEach
	public void setUp(RestDocumentationContextProvider restDocumentation) {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
					.apply(documentationConfiguration(restDocumentation)
							.snippets().withEncoding("UTF-8"))
					.build();
	}

    // 将商品加入到临时购物车
	@Order(1)
	@Test
    void postTempShoppingCart() throws Exception {
        Map<String, Object> map = new HashMap<>();
		map.put("inventoryId", 1);
		map.put("amount", 2);
		String content = JSONObject.toJSONString(map);

		this.mockMvc
			.perform(post("/api/users/{id}/rent/tempShoppingCart", 1).header("Bearer", token)
			.contentType(MediaType.APPLICATION_JSON)
			.content(content))
			.andExpect(status().isCreated())
			.andDo(document("post-rent-temp-shopping-cart"));
    }

	// 从临时购物车中取出商品
	@Order(2)
	@Test
	void getTempShoppingCart() throws Exception {
		this.mockMvc
			.perform(get("/api/users/{id}/rent/tempShoppingCart", 1).header("Bearer", token))
			.andExpect(status().isOk())
			.andDo(document("get-rent-temp-shopping-cart"));
	}
}
