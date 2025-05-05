package org.zerock.leekiye.service;

import org.springframework.transaction.annotation.Transactional;
import org.zerock.leekiye.domain.WallPaper;
import org.zerock.leekiye.dto.PageRequestDTO;
import org.zerock.leekiye.dto.PageResponseDTO;
import org.zerock.leekiye.dto.WallPaperDTO;

@Transactional
public interface WallPaperService {

    // 여기다가 그냥 userID 를 불러오는 메소드를 만들어서 이걸 전역으로 써먹을까? 아니면 그냥
    // 지금 땜빵한거 처럼 일일이 Long userID 를 쓰는게 나을란가

    WallPaperDTO get(Long ord);

    WallPaperDTO getForUser(Long ord, String userID);

    PageResponseDTO<WallPaperDTO> getList(PageRequestDTO pageRequestDTO, Long writerId);

    // 사용자별로 해당 사용자가 등록한 월페이퍼 랜덤으로 불러오는 메소드
    // 메소드 타입 뭐로 할지 모르겠군
    // WallpaperDTO 로 할까?

    Long register(WallPaperDTO wallPaperDTO, String userID);

    void modify(WallPaperDTO wallPaperDTO, String userID);

    void remove(Long ord, String userID);

    WallPaperDTO getRandomWallpaper(String userID);
}
