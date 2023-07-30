package bookstore;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class })
@SpringBootTest
class BookstoreApplicationTests {
	
	@Test
	void contextLoads() {
		
	}
}
