package org.zerock.leekiye.repository;

import lombok.extern.log4j.Log4j2;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.zerock.leekiye.domain.Member;
import org.zerock.leekiye.domain.SavePeriod;
import org.zerock.leekiye.domain.Todo;
import org.zerock.leekiye.dto.TodoDTO;
import org.zerock.leekiye.service.MemberService;
import org.zerock.leekiye.service.TodoService;

import java.time.LocalDate;

@SpringBootTest
@Log4j2
public class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private TodoService todoService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testInsertTodo0413() {

        // Member writer
        // 근데 맴버에서 현재 로그인 한 사용자 따로 가져오는게 있나? 아니면 투두에서

        // MemberRepository.findByUserID("testUser0415") 사용해서 작성자 조회
        //해당 Member를 Todo의 writer로 설정

        String targetUserID = "testUser0413";

        Member writer = memberRepository.findByUserID(targetUserID).orElseThrow(() ->
                new RuntimeException(targetUserID + "의 아이디를 가진 작성자는 존재하지 않습니다"));

        TodoDTO todoDTO = new TodoDTO();
        todoDTO.setTitle("테스트 0413 Todo");
        todoDTO.setContents("자신의 감정을 소중히 여길 수 있도록");
        todoDTO.setDueDate(LocalDate.now().plusDays(3));
        todoDTO.setComplete(false);
        todoDTO.setCreatedAt(LocalDate.now());
        todoDTO.setSavePeriod(SavePeriod.PERMANENT);  // 예시 enum 값

        // Entity 변환 및 작성자 설정
        Todo todo = dtoToEntity(todoDTO);
        todo.setWriter(writer);

        // 게시글 저장
        Todo save = todoRepository.save(todo);

        // 검증해보기
        Assertions.assertNotNull(save.getTno());
        Assertions.assertEquals("testUser0413", save.getWriter().getUserID());

        log.info("기존 사용자 작성 Todo 저장 성공. tno: {}", save.getTno());
    }

    @Test
    public void testInsertTodo0413_2() {

        // Member writer
        // 근데 맴버에서 현재 로그인 한 사용자 따로 가져오는게 있나? 아니면 투두에서

        // MemberRepository.findByUserID("testUser0415") 사용해서 작성자 조회
        //해당 Member를 Todo의 writer로 설정

        String targetUserID = "testUser0413";

        Member writer = memberRepository.findByUserID(targetUserID).orElseThrow(() ->
                new RuntimeException(targetUserID + "의 아이디를 가진 작성자는 존재하지 않습니다"));

        TodoDTO todoDTO = new TodoDTO();
        todoDTO.setTitle("테스트 0413 Todo ver2");
        todoDTO.setContents("요동치는 마음이 형태가 세상에 드러날 수 있게..");
        todoDTO.setDueDate(LocalDate.now().plusDays(3));
        todoDTO.setComplete(false);
        todoDTO.setCreatedAt(LocalDate.now());
        todoDTO.setSavePeriod(SavePeriod.PERMANENT);  // 예시 enum 값

        // Entity 변환 및 작성자 설정
        Todo todo = dtoToEntity(todoDTO);
        todo.setWriter(writer);

        // 게시글 저장
        Todo save = todoRepository.save(todo);

        // 검증해보기
        Assertions.assertNotNull(save.getTno());
        Assertions.assertEquals("testUser0413", save.getWriter().getUserID());

        log.info("기존 사용자 작성 Todo 저장 성공. tno: {}", save.getTno());
    }


    @Test
    public void testInsertTodo0415() {

        // Member writer
        // 근데 맴버에서 현재 로그인 한 사용자 따로 가져오는게 있나? 아니면 투두에서

        // MemberRepository.findByUserID("testUser0415") 사용해서 작성자 조회
        //해당 Member를 Todo의 writer로 설정

        String targetUserID = "testUser0415";

        Member writer = memberRepository.findByUserID(targetUserID).orElseThrow(() ->
                new RuntimeException(targetUserID + "의 아이디를 가진 작성자는 존재하지 않습니다"));

        TodoDTO todoDTO = new TodoDTO();
        todoDTO.setTitle("테스트 0415 Todo");
        todoDTO.setContents("삶을 속삭여줬어");
        todoDTO.setDueDate(LocalDate.now().plusDays(3));
        todoDTO.setComplete(false);
        todoDTO.setCreatedAt(LocalDate.now());
        todoDTO.setSavePeriod(SavePeriod.PERMANENT);  // 예시 enum 값

        // Entity 변환 및 작성자 설정
        Todo todo = dtoToEntity(todoDTO);
        todo.setWriter(writer);

        // 게시글 저장
        Todo save = todoRepository.save(todo);

        // 검증해보기
        Assertions.assertNotNull(save.getTno());
        Assertions.assertEquals("testUser0415", save.getWriter().getUserID());

        log.info("기존 사용자 작성 Todo 저장 성공. tno: {}", save.getTno());
    }

    @Test
    public void testModifyTodo() {

        String targetUserID = "testUser0413";
        Member writer = memberRepository.findByUserID(targetUserID).orElseThrow(() ->
                new RuntimeException(targetUserID + "의 아이디를 가진 작성자는 존재하지 않습니다"));

        TodoDTO todoDTO = new TodoDTO();
        // 밑줄에서 해당 게시글이 사용자가 적은게 맞는지 조회하는거랑 tno 를 불러오는 걸 해보자
        // 직접 2L 넣는것도 좋은데 해당 사용자가 작성한 Todo 조회해서 2L 가져오는건 좀 더 상위적인 레벨인가
        Todo currentTodo = todoRepository.findById(2L).orElseThrow(()-> new RuntimeException(
                "해당 Todo는 존재하지 않습니다. 다시 확인 바람"));

        // 작성자 확인
        Assertions.assertEquals(writer.getId(), currentTodo.getWriter().getId());

        // 변경
        currentTodo.changeTitle("수정된 제목 test0413");
        currentTodo.changeContent("수정된 내용 test0413");
        currentTodo.changeDueDate(LocalDate.of(2025, 4, 30));
        currentTodo.changeComplete(true);

        todoRepository.save(currentTodo); // JPA flush 일어나지 않을 수도 있으므로 명시
        log.info("게시글 수정 완료 확인");
    }

    private Todo dtoToEntity(TodoDTO dto) {
        return Todo.builder()
                .title(dto.getTitle())
                .contents(dto.getContents())
                .dueDate(dto.getDueDate())
                .isComplete(dto.isComplete())
                .createdAt(dto.getCreatedAt())
                .savePeriod(dto.getSavePeriod())
                .build();
    }
}
