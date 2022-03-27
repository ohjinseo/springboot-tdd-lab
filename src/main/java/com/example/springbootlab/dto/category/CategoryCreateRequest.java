package com.example.springbootlab.dto.category;

import com.example.springbootlab.domain.category.Category;
import com.example.springbootlab.domain.category.CategoryRepository;
import com.example.springbootlab.exception.CategoryNotFoundException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Optional;

@ApiModel(value = "카테고리 생성 요청")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryCreateRequest {
    @ApiModelProperty(value = "카테고리 명", notes = "카테고리 명을 입력해주세요", required = true, example = "my category")
    @NotBlank(message = "{categoryCreateRequest.name.notBlank}")
    @Size(min = 2, max = 30, message = "{categoryCreateRequest.name.size}")
    private String name;

    @ApiModelProperty(value = "부모 카테고리 아이디", notes = "부모 카테고리 아이디를 입력해주세요", example = "7")
    private Long parentId;

    public static Category toEntity(CategoryCreateRequest req, CategoryRepository categoryRepository) {
        return new Category(req.getName(),
                // 루트 카테고리인 경우 parentId가 null이므로 Category 생성자의 두 번째 인자도 null이어야 한다.
                // 하지만 parentId에 해당하는 id가 없다면 CategoryNotFoundException 예외가 발생하므로 Optional을 사용
                Optional.ofNullable(req.getParentId()).map(id -> categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new)).orElse(null));
    }

}
