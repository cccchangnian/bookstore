package bookstore.rent;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
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
public class RentedBookOrderTests {
    
    @RegisterExtension
	public RestDocumentationExtension restDocumentation = new RestDocumentationExtension("target/generated-snippets/rent/book-order");
	
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext context;

	// 创建的订单id
	private String id = "RBO176456388094464000";

	// token
    private String token = "b2a7180a-8bda-4b69-8812-3e0c1a8d7dc9";
	
	@BeforeEach
	public void setUp(RestDocumentationContextProvider restDocumentation) {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
					.apply(documentationConfiguration(restDocumentation)
							.snippets().withEncoding("UTF-8"))
					.build();
	}

    // 测试添加一个订单 有数据
	@Order(1)
	@Test
	void postBookOrder() throws Exception {
		Map<String, String> map = new HashMap<>();
		map.put("id", id);
        map.put("prePrice", "50.9");
		map.put("totalPrice", "0.0");
        map.put("price", "20.1");
		map.put("owner", "http://127.0.0.1:9001/api/users/1");
        map.put("location", "江苏省南京市浦口区盘龙山庄33栋105室");
		map.put("phone", "19851920392");

		String content = JSONObject.toJSONString(map);

		this.mockMvc
			.perform(post("/api/rentedBookOrders").header("Bearer", token)
				.contentType(MediaType.APPLICATION_JSON)
				.content(content))
			.andExpect(status().isCreated())
			.andDo(document("post-rent-book-order"));
	}

	// 测试获取所有图书订单 不加参数
	@Order(2)
	@Test
	void getBookOrders() throws Exception {
		this.mockMvc
			.perform(get("/api/rentedBookOrders").header("Bearer", token))
			.andExpect(status().isOk())
			.andDo(document("get-rent-book-orders"));
	}

	@Order(3)
	@Test
	// 测试获取所有图书订单 加参数
	void getBookOrdersInlineOwner() throws Exception {
		this.mockMvc
			.perform(get("/api/rentedBookOrders?projection=inlineOwner").accept(MediaType.APPLICATION_JSON).header("Bearer", token))
			.andExpect(status().isOk())
			.andDo(document("get-rent-book-orders-inline-owner", requestParameters(
				parameterWithName("projection").description("将订单持有者信息显示在订单中")
			)));
			
	}
	
	// 测试获取一个订单 不加参数
	@Order(4)
	@Test
	void getBookOrder() throws Exception {
		this.mockMvc
			.perform(get("/api/rentedBookOrders/{id}", "RBO0001").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("get-rent-book-order"));
	}

	// 测试获取一个订单 加参数
	@Order(5)
	@Test
	void getBookOrderInlineOwner() throws Exception {
		this.mockMvc
			.perform(get("/api/rentedBookOrders/{id}?projection=inlineOwner").accept(MediaType.APPLICATION_JSON).header("Bearer", token))
			.andExpect(status().isOk())
			.andDo(document("get-rent-book-order-inline-owner", requestParameters(
				parameterWithName("projection").description("将订单持有者信息显示在订单中")
			)));
	}

	// 测试更新一个订单
	@Order(6)
	@Test
	void putBookOrder() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("prePrice", "50.9");
		map.put("totalPrice", "400.1");
        map.put("price", "20.1");
		map.put("owner", "http://127.0.0.1:9001/api/users/1");
        map.put("location", "江苏省南京市浦口区盘龙山庄33栋105室");
		map.put("phone", "19851920392");
		String content = JSONObject.toJSONString(map);
		
		this.mockMvc
			.perform(put("/api/rentedBookOrders/{id}", id).header("Bearer", token)
				.contentType(MediaType.APPLICATION_JSON)
				.content(content))
			.andExpect(status().isNoContent())
			.andDo(document("put-rent-book-order"));
	}

	// 测试部分更新一个订单
	@Order(7)
	@Test
	void patchBookOrder() throws Exception {
		Map<String, String> map = new HashMap<>();
		map.put("totalPrice", "420.5");
		String content = JSONObject.toJSONString(map);

		this.mockMvc
			.perform(patch("/api/rentedBookOrders/{id}", id).header("Bearer", token)
				.contentType(MediaType.APPLICATION_JSON)
				.content(content))
			.andExpect(status().isOk())
			.andDo(document("patch-rent-book-order"));
	}

	// 测试删除一个订单
	@Order(8)
	@Test
	void deleteBookOrder() throws Exception {
		this.mockMvc
			.perform(delete("/api/bookOrders/{id}", id).header("Bearer", token))
			.andExpect(status().isNoContent())
			.andDo(document("delete-rent-book-order"));
	}
}
