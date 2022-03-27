package com.example.springbootlab.controller.category;

import com.example.springbootlab.dto.category.CategoryCreateRequest;
import com.example.springbootlab.service.category.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.example.springbootlab.factory.dto.CategoryCreateRequestFactory.createCategoryCreateRequest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    @InjectMocks
    CategoryController categoryController;
    @Mock
    CategoryService categoryService;

    MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    void readAllTest() throws Exception {
        // given, when, then
        mockMvc.perform(
                get("/api/categories")
        ).andExpect(status().isOk());

        verify(categoryService).readAll();
    }

    @Test
    void createTest() throws Exception {
        // given
        CategoryCreateRequest req = createCategoryCreateRequest();

        // when
        mockMvc.perform(
                post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req))
        ).andExpect(status().isCreated());

        verify(categoryService).create(req);
    }

    @Test
    void deleteTest() throws Exception {
        // given
        Long id = 1L;

        // when, then
        mockMvc.perform(
                delete("/api/categories/{id}", id)
        ).andExpect(status().isOk());
        verify(categoryService).delete(anyLong());
    }
}