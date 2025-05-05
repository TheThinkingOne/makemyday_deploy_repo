package org.zerock.leekiye.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.leekiye.dto.MemberDTO;
import org.zerock.leekiye.dto.QuotesDTO;
import org.zerock.leekiye.dto.WallPaperDTO;
import org.zerock.leekiye.service.QuotesService;
import org.zerock.leekiye.service.WallPaperService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/makemyday")
public class QuotesAndWallpaperRandomOutputController {

    private final QuotesService quotesService;

    private final WallPaperService wallPaperService;

//    // 근데이게 getmapping 이 똑같으면 안되는거로 아는데
//    @GetMapping("/")
//    public QuotesDTO getRandomQuote() {
//        return quotesService.getRandomQuote();
//    }
//
//    // 사용자별 월페이퍼 조회
//    @GetMapping("/")
//    public WallPaperDTO getRandomWallpaper(@AuthenticationPrincipal MemberDTO memberDTO) {
//        return wallPaperService.getRandomWallpaper();
//    }

    @GetMapping("/main")
    public Map<String, Object> getRandomQuoteAndWallpaper(@AuthenticationPrincipal MemberDTO memberDTO) {
        Map<String, Object> result = new HashMap<>();

        // 명언은 공용
        QuotesDTO quote = quotesService.getRandomQuote();
        result.put("quote", quote);

        // 배경화면은 사용자별
        if (memberDTO != null) {
            WallPaperDTO wallPaper = wallPaperService.getRandomWallpaper(memberDTO.getUserID());
            result.put("wallpaper", wallPaper);
        }

        return result;
    }

}
