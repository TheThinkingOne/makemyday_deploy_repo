package org.zerock.leekiye.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.leekiye.domain.Member;
import org.zerock.leekiye.dto.WallPaperDTO;
import org.zerock.leekiye.service.MemberService;
import org.zerock.leekiye.service.WallPaperService;

@SpringBootTest
@Log4j2
public class WallpaperRepositoryTest {


    @Autowired
    private WallPaperService wallPaperService;

    @Autowired
    private WallPaperRepository wallPaperRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void testInsertWallpaper() {
        String targetUserID = "testUser0413";

        Member writer = memberRepository.findByUserID(targetUserID).orElseThrow(() ->
                new RuntimeException(targetUserID + "의 아이디를 가진 작성자는 존재하지 않습니다"));

        WallPaperDTO wallPaperDTO = new WallPaperDTO();
        wallPaperDTO.setPaperTitle("test0413 wallpaper");
        //wallPaperDTO.git add
    }




}
