package museon_online.astor_butler;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(classes = AstorButlerApplication.class)
@TestPropertySource(locations = "classpath:application-test.yaml")
class AstorButlerApplicationTests {

	@Test
	void contextLoads() {
	}

}
