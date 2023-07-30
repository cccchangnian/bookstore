package bookstore.sale;

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
public class BookInventoryTests {
    
    @RegisterExtension
	public RestDocumentationExtension restDocumentation = new RestDocumentationExtension("target/generated-snippets/sale/book-inventory");
	
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

    // 测试创建一个图书库存，在测试此方法之前需要数据库中存在一个分类
    @Test
    void postBookInventory() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("bookName", "五年高考三年模拟");
        map.put("price", "20.3");
        map.put("rating", 9.5);
        map.put("description", "五年高考三年模拟，你值得拥有");
        map.put("author", "金浩然");
        map.put("category", "http://127.0.0.1:9001/api/bookCategories/1");
        String content = JSONObject.toJSONString(map);

        this.mockMvc
            .perform(post("/api/bookInventories").header("Bearer", token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(content))
            .andExpect(status().isCreated())
            .andDo(document("post-book-inventory"));
    }

    // 测试获取多个图书库存
    @Test
    void getBookInventories() throws Exception {
        this.mockMvc
            .perform(get("/api/bookInventories"))
            .andExpect(status().isOk())
            .andDo(document("get-book-inventories"));
    }

    @Test
    // 测试带参数的获取多个图书库存
    void getBookInventoriesInlineCategory() throws Exception {
        this.mockMvc
            .perform(get("/api/bookInventories?projection=inlineCategory"))
            .andExpect(status().isOk())
            .andDo(document("get-book-inventories-inline-category", requestParameters(
                parameterWithName("projection").description("将分类信息直接显示在库存中")
            )));
    }

    // 测试获取单个图书库存
    @Test
    void getBookInventory() throws Exception {
        // 图书库存不易被删除，所以直接用1来表示
        this.mockMvc
            .perform(get("/api/bookInventories/{id}", 1))
            .andExpect(status().isOk())
            .andDo(document("get-book-inventory"));
    }

    // 测试带参数获取单个图书库存
    @Test
    void getBookInventoryInlineCategory() throws Exception {
        this.mockMvc
            .perform(get("/api/bookInventories/{id}?projection=inlineCategory", 1))
            .andExpect(status().isOk())
            .andDo(document("get-book-inventory-inline-category", requestParameters(
                parameterWithName("projection").description("将分类信息显示在库存中")
            )));
    }

    // 测试修改一个图书库存
    @Test
    void putBookInventory() throws Exception {
        // put必须携带所有必要的信息
        Map<String, Object> map = new HashMap<>();
        map.put("bookName", "五年高考三年模拟");
        map.put("price", "23.5");
        map.put("rating", 7.5);
        map.put("description", "五年高考三年模拟，你值得拥有");
        map.put("author", "金浩然");
        map.put("category", "http://127.0.0.1:9001/api/bookCategories/1");
        String content = JSONObject.toJSONString(map);

        this.mockMvc
            .perform(put("/api/bookInventories/{id}", 1).header("Bearer", token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(content))
            .andExpect(status().isNoContent())
            .andDo(document("put-book-inventory"));
    }

    @Test
    // 测试部分修改一个图书库存
    void patchBookInventory() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("bookName", "高等数学");
        String content = JSONObject.toJSONString(map);

        this.mockMvc
            .perform(patch("/api/bookInventories/{id}", 1).header("Bearer", token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(content))
            .andExpect(status().isNoContent())
            .andDo(document("patch-book-inventory"));
    }

    // @Test
    // 测试删除一个图书库存，这里不删除，因为后续还要用到
    void deleteBookInventory() throws Exception {
        this.mockMvc.perform(delete("/api/bookInventories/{id}", 1))
            .andExpect(status().isNoContent())
            .andDo(document("delete-book-inventory"));
    }
}
