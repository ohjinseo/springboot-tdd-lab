package com.example.springbootlab.service.category;

import com.example.springbootlab.domain.category.Category;
import com.example.springbootlab.domain.category.CategoryRepository;
import com.example.springbootlab.dto.category.CategoryCreateRequest;
import com.example.springbootlab.dto.category.CategoryDto;
import com.example.springbootlab.exception.CategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CategoryService {
    private final CategoryRepository categoryRepository;


    // 플랫한 구조의 카테고리를 계층형 구조로 변환
    public List<CategoryDto> readAll() {
        List<Category> categories = categoryRepository.findAllOrderByParentIdAscNullsFirstCategoryIdAsc();
        return CategoryDto.toDtoList(categories);
    }

    @Transactional
    public void create(CategoryCreateRequest req) {
        categoryRepository.save(CategoryCreateRequest.toEntity(req, categoryRepository));
    }

    @Transactional
    public void delete(Long id) {
        // bug fix : existsById + delteById에 의해 select 쿼리가 2개 날아감 -> 쿼리 1개만 날아가도록 수정
        Category category = categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new);
        categoryRepository.delete(category);
    }
}
