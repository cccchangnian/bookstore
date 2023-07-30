package bookstore.reserve;

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
public class BookReserveTests {
    
    @RegisterExtension
	public RestDocumentationExtension restDocumentation = new RestDocumentationExtension("target/generated-snippets/reserve");
	
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext context;
	
	@BeforeEach
	public void setUp(RestDocumentationContextProvider restDocumentation) {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
					.apply(documentationConfiguration(restDocumentation)
							.snippets().withEncoding("UTF-8"))
					.build();
	}

    // 添加一个预定信息
    @Test
    void postBookReserve() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("prePrice", "20.0");
        map.put("totalPrice", "40.0");
        map.put("amount", 1);
        map.put("phone", "19851920392");
        map.put("location", "盘龙山庄33栋105室");
        map.put("user", "http://127.0.0.1:9001/api/users/1");
        map.put("inventory", "http://127.0.0.1:9001/api/bookInventories/1");
        String content = JSONObject.toJSONString(map);

        this.mockMvc
            .perform(post("/api/bookReserves")
            .contentType(MediaType.APPLICATION_JSON)
            .content(content))
            .andExpect(status().isCreated())
            .andDo(document("post-book-reserve"));
    }

    // 获取所有预定信息
    @Test
    void getBookReserves() throws Exception {
        this.mockMvc.perform(get("/api/bookReserves"))
            .andExpect(status().isOk())
            .andDo(document("get-book-reserves"));
    }

    // // 获取指定预定信息
    // @Test
    // void getBookReserve() throws Exception {
    //     this.mockMvc.perform(get("/api/bookReserves/{id}", 1))
    //         .andExpect(status().isOk())
    //         .andDo(document("get-book-reserve"));
    // }

    // // 更新指定预定信息
    // @Test
    // void putBookReserve() throws Exception {
    //     Map<String, Object> map = new HashMap<>();
    //     map.put("prePrice", "20.0");
    //     map.put("isCompleted", true);
    //     map.put("amount", 1);
    //     map.put("user", "http://127.0.0.1:9001/api/users/1");
    //     map.put("inventory", "http://127.0.0.1:9001/api/bookInventories/1");
    //     String content = JSONObject.toJSONString(map);

    //     this.mockMvc
    //         .perform(put("/api/bookReserves/{id}", 1)
    //         .contentType(MediaType.APPLICATION_JSON)
    //         .content(content))
    //         .andExpect(status().isNoContent())
    //         .andDo(document("put-book-reserve"));
    // }

    // // 部分更新指定预定信息
    // @Test
    // void patchBookReserve() throws Exception {
    //     Map<String, String> map = new HashMap<>();
    //     map.put("totalPrice", "40.0");
    //     String content = JSONObject.toJSONString(map);

    //     this.mockMvc.perform(patch("/api/bookReserves/{id}", 1)
    //         .contentType(MediaType.APPLICATION_JSON)
    //         .content(content))
    //         .andExpect(status().isOk())
    //         .andDo(document("patch-book-reserve"));
    // }

    // // 删除指定预定信息
    // @Test
    // void deleteBookReserve() throws Exception {
    //     this.mockMvc.perform(delete("/api/bookReserves/{id}", 1))
    //         .andExpect(status().isNoContent())
    //         .andDo(document("delete-book-reserve"));
    // }
}
