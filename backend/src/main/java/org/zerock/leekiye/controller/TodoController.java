package org.zerock.leekiye.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.zerock.leekiye.dto.MemberDTO;
import org.zerock.leekiye.dto.PageRequestDTO;
import org.zerock.leekiye.dto.PageResponseDTO;
import org.zerock.leekiye.dto.TodoDTO;
import org.zerock.leekiye.service.TodoService;

import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/makemyday/todo")
public class TodoController {

    private final TodoService todoService;

    // 해당 게시글 조회
    @GetMapping("/{tno}")
    public TodoDTO get(@PathVariable Long tno, @AuthenticationPrincipal MemberDTO memberDTO) {
        return todoService.getForUser(memberDTO.getUserID(), tno);
    }

    // Todo 리스트 불러오기
    @GetMapping("/list")
    public PageResponseDTO<TodoDTO> list(PageRequestDTO pageRequestDTO,
                                         @AuthenticationPrincipal MemberDTO memberDTO) {
        log.info("list ====== " + pageRequestDTO);

        // 리스트 불러오기
        return todoService.getList(pageRequestDTO, memberDTO.getUserID());
        // return todoService.getListForUser(pageRequestDTO, memberDTO.getId());
    }

    // Todo 등록
    @PostMapping("/")
    public Map<String, Long> register(@RequestBody TodoDTO dto,
                                      @AuthenticationPrincipal MemberDTO memberDTO) {

        log.info("todoDTO: " + dto);
        // ! 포스트맨 에서 register 등록 실험 시에 터진 이유
        // => 인증된 사용자 정보는 MemberDTO로 존재
        //
        //이걸 바로 writer로 사용하면 JPA는 “얘 누구임? DB에 없음” 하며 터짐
        //
        //MemberDTO.getId() → memberRepository.findById()로 실제 엔티티 다시 불러오면 해결
        dto.setWriterID(memberDTO.getId()); // 작성자 설정
        Long tno = todoService.register(dto, memberDTO.getUserID());

        return Map.of("TNO", tno);
    }

    // Todo 수정
    @PutMapping("/{tno}")
    public Map<String, String> modify(@PathVariable("tno") Long tno,
                                      @RequestBody TodoDTO todoDTO,
                                      @AuthenticationPrincipal MemberDTO memberDTO) {
        // /{tno} 와 todoDTO 안의 tno가 일치하는지 확인
        todoDTO.setTno(tno);
        //todoDTO.setWriterId(memberDTO.getId()); // 작성자 검증
        log.info("Modify: " + todoDTO);
        todoService.modify(todoDTO, memberDTO.getUserID());

        return Map.of("RESULT", "SUCCESS");

    }

    // Todo 삭제
    @DeleteMapping("/{tno}")
    public Map<String, String> remove(@PathVariable(name="tno") Long tno,
                                      @AuthenticationPrincipal MemberDTO memberDTO) {
        log.info(tno + "번 게시글 삭제");

        // todoService.removeForUser(tno, memberDTO.getId()); 작성자 맞는지 확인하고 삭제
        todoService.remove(tno, memberDTO.getUserID());

        return Map.of("RESULT", "SUCCESS");
    }




}
