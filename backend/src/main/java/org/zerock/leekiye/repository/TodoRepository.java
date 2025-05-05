package org.zerock.leekiye.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.leekiye.domain.Todo;
import org.zerock.leekiye.dto.PageRequestDTO;
import org.zerock.leekiye.repository.search.TodoSearch;

public interface TodoRepository extends JpaRepository<Todo, Long>, TodoSearch {
    // 각자가 쓴 Todo 만 조회 되도록 쿼리 메소드 추가
    // 0427 방금 생성함
    // 아래에 PageRequestDTO 에서 그냥 Pageable 로 바꾼 이유
    Page<Todo> findByWriter_UserID(Pageable pageable, String userID);
}
