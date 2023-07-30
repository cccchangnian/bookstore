package bookstore.rent;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
public class RentedBookReturnTests {
    
    @RegisterExtension
	public RestDocumentationExtension restDocumentation = new RestDocumentationExtension("target/generated-snippets/rent/book-return");
	
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
	
    // 获取所有归还记录
    @Test
    void getBookReturns() throws Exception {
        this.mockMvc.perform(get("/api/rentedBookReturns").header("Bearer", token))
            .andExpect(status().isOk())
            .andDo(document("get-rent-book-returns"));
    }

    // // 获取一条归还记录
    // @Test
    // void getBookReturn() throws Exception {
    //     this.mockMvc.perform(get("/api/rentedBookReturns/{id}", 1))
    //         .andExpect(status().isOk())
    //         .andDo(document("get-rent-book-return"));
    // }
}
