package com.example.springbootlab.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostReadCondition {
    @NotNull(message = "페이지 번호를 입력해주세요.")
    @PositiveOrZero(message = "올바른 페이지 번호를 입력해주세요.(0이상)")
    private Integer page;

    @NotNull(message = "페이지 크기를 입력해주세요.")
    @Positive(message = "올바른 페이지 크기를 입력해주세요.(1이상)")
    private Integer size;

    // 선택적으로 지정
    private List<Long> categoryId = new ArrayList<>();
    private List<Long> memberId = new ArrayList<>();
}
