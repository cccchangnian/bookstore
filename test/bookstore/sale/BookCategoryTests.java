package bookstore.sale;

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
public class BookCategoryTests {
    
    @RegisterExtension
	public RestDocumentationExtension restDocumentation = new RestDocumentationExtension("target/generated-snippets/sale/book-category");
	
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

    // 测试创建一个图书分类
    @Test
    void postBookCategory() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("name", "测试分类");
        String content = JSONObject.toJSONString(map);

        this.mockMvc
            .perform(post("/api/bookCategories").header("Bearer", token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(content))
            .andExpect(status().isCreated())
            .andDo(document("post-book-category"));
    }

    // 测试获取多个图书分类
    @Test
    void getBookCategories() throws Exception {
        this.mockMvc
            .perform(get("/api/bookCategories"))
            .andExpect(status().isOk())
            .andDo(document("get-book-categories"));
    }

    // 测试获取单个图书分类
    @Test
    void getBookCategory() throws Exception {
        // 获取分类的前提是分类存在，因为图书分类并不容易被删除，所以这里直接用了1来表示id
        this.mockMvc
            .perform(get("/api/bookCategories/{id}", 1))
            .andExpect(status().isOk())
            .andDo(document("get-book-category"));
    }

    // 测试修改一个图书分类
    @Test
    void putBookCategory() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("name", "测试分类2");
        String content = JSONObject.toJSONString(map);

        this.mockMvc
            .perform(put("/api/bookCategories/{id}", 1).header("Bearer", token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(content))
            .andExpect(status().isNoContent())
            .andDo(document("put-book-category"));
    }

    @Test
    // 测试部分修改一个图书分类
    void patchBookCategory() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("name", "测试分类3");
        String content = JSONObject.toJSONString(map);

        this.mockMvc
            .perform(patch("/api/bookCategories/{id}", 1).header("Bearer", token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(content))
            .andExpect(status().isNoContent())
            .andDo(document("patch-book-category"));
    }

    // @Test
    // 测试删除一个图书分类，这里就不删除了，因为后续会用到分类
    void deleteBookCategory() throws Exception {
        this.mockMvc
            .perform(delete("/api/bookCategories/{id}", 1).header("Bearer", token))
            .andExpect(status().isNoContent())
            .andDo(document("delete-book-category"));
    }
}
