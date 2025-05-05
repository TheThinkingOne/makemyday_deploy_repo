package org.zerock.leekiye.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.zerock.leekiye.domain.Member;
import org.zerock.leekiye.domain.Todo;
import org.zerock.leekiye.dto.PageRequestDTO;
import org.zerock.leekiye.dto.PageResponseDTO;
import org.zerock.leekiye.dto.TodoDTO;
import org.zerock.leekiye.repository.TodoRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    @Override
    public TodoDTO getForUser(String userID, Long tno) {
        Todo todo = todoRepository.findById(tno)
                .filter(t -> t.getWriter().getUserID().equals(userID))
                .orElseThrow(() -> new RuntimeException("해당 유저가 등록한 할 일이 아님. 포스트맨 조작 막음"));
        return entityToDTO(todo);
    }

    private final TodoRepository todoRepository;

    // ServiceImpl 이 비즈니스 로직 구현해놓는 곳

    // 해당 번호의 Todo 게시글 조회 이건 아마 그럼 안쓰게 될 것 같기도 하고
    @Override
    public TodoDTO get(Long tno) {
        Optional<Todo> result = todoRepository.findById(tno);

        Todo todo = result.orElseThrow();

        return entityToDTO(todo);
    }



    // Todo 게시글 리스트 불러오기
    @Override
    // public PageResponseDTO<TodoDTO> getList(PageRequestDTO pageRequestDTO, Long userID)
    public PageResponseDTO<TodoDTO> getList(PageRequestDTO pageRequestDTO, String userID) {

        // Page<Todo> result = todoRepository.todoSearchByUser(pageRequestDTO, userId);
        // TodoSearchImpl 에서 유저별 조회로 수정했으니 여기도 수정
        Page<Todo> result = todoRepository.todoSearchByUser(pageRequestDTO, userID);

        List<TodoDTO> dtoList = result
                .get()
                .map(this::entityToDTO)
                .collect(Collectors.toList());

        return PageResponseDTO.<TodoDTO>withAll()
                .dtoList(dtoList)
                .pageRequestDTO(pageRequestDTO)
                .totalCount(result.getTotalElements())
                .build();
    }

    @Override
    // public Long register(TodoDTO dto, Long userId)
    public Long register(TodoDTO dto, String userID) {
        Todo todo = dtoToEntity(dto);

        // todo.setWriter(Member.builder().id(userID).build());

        return todoRepository.save(todo).getTno();

        // 이 부분의 setWriter 를 어떻게 만들면 될지 물어봐야겠군
        // Todo todo = dtoToEntity(dto);
        //        todo.setWriter(Member.builder().id(userId).build()); // 사용자 설정
        //        return todoRepository.save(todo).getTno();
    }

    // Todo 게시글 수정
    @Override
    // public void modify(TodoDTO dto, Long userId)
    public void modify(TodoDTO todoDTO, String userID) {

         Todo todo = todoRepository.findById(todoDTO.getTno())
                        .filter(t -> t.getWriter().getUserID().equals(userID))
                        .orElseThrow(() -> new RuntimeException("해당 게시글을 작성한 사람만 수정 가능합니다."));

//        Optional<Todo> result = todoRepository.findById(todoDTO.getTno());
//
//        Todo todo = result.orElseThrow();

        todo.changeTitle(todoDTO.getTitle()); // good
        todo.changeContent(todoDTO.getContents()); // good
        todo.changeComplete(todoDTO.isComplete()); // good
        todo.changeDueDate(todoDTO.getDueDate()); // good
        todo.changeSavePeriod(todoDTO.getSavePeriod()); // good

        todoRepository.save(todo);
    }

    // 게시글 삭제
    @Override
    // public void remove(Long tno, Long userId)
    public void remove(Long tno, String userID) {
        Todo todo = todoRepository.findById(tno)
                .filter(t -> t.getWriter().getUserID().equals(userID))
                .orElseThrow(() -> new RuntimeException("해당 게시글의 작성자만 삭제 가능합니다."));
        todoRepository.delete(todo);
    }

    @Override
    public Todo dtoToEntity(TodoDTO todoDTO) {
        return TodoService.super.dtoToEntity(todoDTO);
    }

    @Override
    public TodoDTO entityToDTO(Todo todo) {
        return TodoService.super.entityToDTO(todo);
    }


    // Repository 먼저 작성하자
}
