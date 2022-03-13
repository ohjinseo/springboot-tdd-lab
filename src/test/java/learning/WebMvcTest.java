package learning;

import com.example.springbootlab.controller.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class WebMvcTest {
    @InjectMocks
    TestController testController;
    MockMvc mockMvc;

    @Controller
    public static class TestController {
        @GetMapping("/test/ignore-null-value")
        public Response ignoreNullValueTest() {
            return Response.success();
        }
    }

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(testController).build();
    }

    // Response 클래스 JSON 응답에 null 값이 제외되는지 테스트
    @Test
    void ignoreNullValueInJsonResponseTest() throws Exception {
        mockMvc.perform(
                        get("/test/ignore-null-value")
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.result").doesNotExist());
    }
}
