package org.zerock.leekiye.repository.search;

import com.querydsl.jpa.JPQLQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Service;
import org.zerock.leekiye.domain.QTodo;
import org.zerock.leekiye.domain.Todo;
import org.zerock.leekiye.dto.PageRequestDTO;

import java.util.List;

@Log4j2
public class TodoSearchImpl extends QuerydslRepositorySupport implements TodoSearch {

    public TodoSearchImpl() {
        super(Todo.class);
    }

    // 검색에 관한 메소드
//    @Override
//    public Page<Todo> todoSearch(PageRequestDTO pageRequestDTO) {
//
//        log.info("Todo search ongoing....");
//
//        QTodo todo = QTodo.todo;
//        JPQLQuery<Todo> query = from(todo);
//
//        Pageable pageable = PageRequest.of(
//                pageRequestDTO.getPage() -1,
//                pageRequestDTO.getSize(),
//                Sort.by("tno").descending());
//
//        this.getQuerydsl().applyPagination(pageable, query);
//
//        List<Todo> list = query.fetch();
//
//        long total = query.fetchCount();
//
//        return new PageImpl<>(list, pageable, total);
//    }

    // 검색 메소드(사용자별 조회 조건 추가)
    @Override
    public Page<Todo> todoSearchByUser(PageRequestDTO pageRequestDTO, String userID) {

        log.info("Todo search for user: " + userID);

        QTodo todo = QTodo.todo;

        JPQLQuery<Todo> query = from(todo)
                .where(todo.writer.userID.eq(userID));  // 🔥 userID 필터링 추가

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() -1,
                pageRequestDTO.getSize(),
                Sort.by("tno").descending());

        this.getQuerydsl().applyPagination(pageable, query);

        List<Todo> list = query.fetch();
        long total = query.fetchCount();

        return new PageImpl<>(list, pageable, total);
    }

}
