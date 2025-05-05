package org.zerock.leekiye.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.zerock.leekiye.dto.PageRequestDTO;
import org.zerock.leekiye.dto.PageResponseDTO;
import org.zerock.leekiye.dto.QuotesDTO;
import org.zerock.leekiye.dto.TodoDTO;
import org.zerock.leekiye.service.QuotesService;

import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/makemyday/quotes")
public class QuotesController {

    private final QuotesService quotesService;

    // 이건 관리자가 볼 수 있는 quotes 게시글 페이지
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    public PageResponseDTO<QuotesDTO> list(PageRequestDTO pageRequestDTO) {
        log.info("Quotes list ===> " + pageRequestDTO);

        // quotes 리스트 불러오기(모든 사용자들이 작성한 거)
        return quotesService.getList(pageRequestDTO);
    }

    @PostMapping("/register")
    public Map<String, Long> register(@RequestBody QuotesDTO quotesDTO) {
        log.info("Quotes register ===> " + quotesDTO);

        Long qno = quotesService.register(quotesDTO);

        return Map.of("qno", qno);
    }

    // 해당 quotes 내용 보기(불러오기)
    @GetMapping("/{qno}")
    public QuotesDTO get(@PathVariable(name = "qno") Long qno) {
        return quotesService.get(qno);
    }

    // 해당 quotes 삭제
    @DeleteMapping("/{qno}")
    public Map<String, String> remove(@PathVariable(name="qno") Long qno) {
        log.info(qno + "번 명언 삭제");

        quotesService.remove(qno);

        return Map.of("RESULT", "SUCCESS");
    }

    // 명언 랜덤 출력 로직

}
