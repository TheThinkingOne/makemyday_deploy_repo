package org.zerock.leekiye.service;

import org.springframework.transaction.annotation.Transactional;
import org.zerock.leekiye.domain.Member;
import org.zerock.leekiye.domain.Todo;
import org.zerock.leekiye.dto.PageRequestDTO;
import org.zerock.leekiye.dto.PageResponseDTO;
import org.zerock.leekiye.dto.TodoDTO;

@Transactional
public interface TodoService {

    TodoDTO get (Long tno);

    TodoDTO getForUser (String userID, Long tno); // 매개변수를 뭘로 받아야하지

    PageResponseDTO<TodoDTO> getList(PageRequestDTO pageRequestDTO, String userID);

    Long register(TodoDTO dto, String userID);

    void modify(TodoDTO todoDTO, String userID);

    void remove(Long tno, String userID);

    //PageResponseDTO<TodoDTO> getList(PageRequestDTO pageRequestDTO)

    // dtoToEntity와 entityToDTO 공부해보기
    default Todo dtoToEntity(TodoDTO todoDTO) {
        Todo todo = Todo.builder()
                .tno(todoDTO.getTno())
                .title(todoDTO.getTitle())
                .contents(todoDTO.getContents())
                .dueDate(todoDTO.getDueDate())
                .isComplete(todoDTO.isComplete())
                .savePeriod(todoDTO.getSavePeriod())
                .createdAt(todoDTO.getCreatedAt())
                .build();

        todo.setWriter(Member.builder().id(todoDTO.getWriterId()).build()); // ✨ 작성자 설정
        return todo;
    }

    default TodoDTO entityToDTO(Todo todo) {
        TodoDTO todoDTO =
                TodoDTO.builder()
                        .tno(todo.getTno())
                        .title(todo.getTitle())
                        .contents(todo.getContents())
                        .dueDate(todo.getDueDate())
                        .isComplete(todo.isComplete())
                        .savePeriod(todo.getSavePeriod())
                        .createdAt(todo.getCreatedAt())
                        .build();

        return todoDTO;
    }
}
