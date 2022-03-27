package com.example.springbootlab.domain.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("select c from Category c left join c.parent p order by p.id asc nulls first, c.id asc")
    List<Category> findAllOrderByParentIdAscNullsFirstCategoryIdAsc();
    // 부모의 아이디로 오름차순 정렬하되 NULL을 우선적으로하고, 그 다음으로 자신의 ID로 오름차순 정렬
}
