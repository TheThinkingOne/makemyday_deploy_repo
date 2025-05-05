package org.zerock.leekiye.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.leekiye.domain.Member;
import org.zerock.leekiye.domain.WallPaper;
import org.zerock.leekiye.domain.WallPaperImage;
import org.zerock.leekiye.dto.PageRequestDTO;
import org.zerock.leekiye.dto.PageResponseDTO;
import org.zerock.leekiye.dto.WallPaperDTO;
import org.zerock.leekiye.repository.MemberRepository;
import org.zerock.leekiye.repository.WallPaperRepository;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class WallPaperServiceImpl implements WallPaperService {

    private final WallPaperRepository wallPaperRepository;
    private final MemberRepository memberRepository;

    @Override // 해당 사용자가 등록한 게시글만 불러오게 하는 거
    public WallPaperDTO getForUser(Long ord, String userID) {
        WallPaper wallPaper = wallPaperRepository.findById(ord)
                .filter(w -> w.getWriter().getUserID().equals(userID))
                .orElseThrow(() -> new RuntimeException("해당 유저가 등록한 월페이퍼가 아닙니다."));
        return entityToDTO(wallPaper);
    }

    // 해당 월페이퍼의 등록번호 가져오기
    @Override
    public WallPaperDTO get(Long ord) {
        Optional<WallPaper> result = wallPaperRepository.findById(ord);

        WallPaper wallPaper = result.orElseThrow();

        return entityToDTO(wallPaper);
    }

    // 이거도 역시 다시 공부해야 한다
    // 월페이퍼 리스트 가져오기
    @Override
    // // public PageResponseDTO<TodoDTO> getList(PageRequestDTO pageRequestDTO, Long userID)
    public PageResponseDTO<WallPaperDTO> getList(PageRequestDTO pageRequestDTO, Long writerId) {

        // Pageable pageable = // 흠 이부분은 어떻게 해야할지 모르겠다
        // TodoServiceImpl 은 이 부분에 todoSearchByUser 라는 메소드를 만들어서 하라고 하던데
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage()-1,
                pageRequestDTO.getSize(),
                Sort.by("ord").descending());

        Page<Object[]> result = wallPaperRepository.selectListForUser(writerId, pageable);
        // object[] => 0 product 1 productImage
        // object[] => 0 product 1 productImage
        // object[] => 0 product 1 productImage

        List<WallPaperDTO> dtoList = result.get().map(arr -> { // 쿼리 결과를 ProductDTO 로 변환
            // 이 부분은 특정 상품 상세 페이지가 아니고
            // 상품 목록 페이지(예 : 농산물 카테고리)와 더 유사함
            // 쿠팡에 옥수수 쳐서 나온 페이지들을 dto 에 담아서 보내줌

            WallPaperDTO wallPaperDTO = null;

            WallPaper wallPaper = (WallPaper) arr[0]; // 상품의 데이터
            WallPaperImage wallPaperImage = (WallPaperImage) arr[1]; // 대표 이미지 데이터

            wallPaperDTO = wallPaperDTO.builder()
                    .ord(wallPaper.getOrd())
                    .paperTitle(wallPaper.getPaperTitle())
                    .build();

            String imageStr = wallPaperImage.getFileName(); // 대표 이미지 파일 이름
            wallPaperDTO.setUploadFileNames(List.of(imageStr)); // 프로덕트 이미지 안에 이미지가 한개 들어가게 된다?

            return wallPaperDTO;

        }).collect(Collectors.toList());

        long totalCount = result.getTotalElements();

        return PageResponseDTO.<WallPaperDTO>withAll()
                .dtoList(dtoList)
                .totalCount(totalCount)
                .pageRequestDTO(pageRequestDTO)
                .build();
    }

    // 여기 인티저로 해야하나 Long 으로 해야하나 모르겟노
    @Override
    // // public Long register(WallpaperDTO wallpaperDTO, Long userID)
    public Long register(WallPaperDTO wallPaperDTO, String userID) {
        WallPaper wallPaper = dtoToEntity(wallPaperDTO);

        Member writer = memberRepository.findByUserID(userID)
                        .orElseThrow(() -> new RuntimeException("해당 유저에 대한 작성자 정보가 없습니다."));

        //wallPaper.setWriter(Member.builder().userID(userID).build()); // 이렇게 하니까 jpa 의존성 오류 발생
        wallPaper.setWriter(writer);
        // 대충 이렇게 적으면 되나

        log.info("------------------------");
        log.info(wallPaper);
        log.info(wallPaper.getWallPaperImageList());

        return wallPaperRepository.save(wallPaper).getOrd();

    }

    public void modify(WallPaperDTO wallPaperDTO, String userID) {
        WallPaper wallpaper = wallPaperRepository.findById(wallPaperDTO.getOrd())
                .filter(t -> t.getWriter().getUserID().equals(userID))
                .orElseThrow(() -> new RuntimeException("수정 권한 없음"));

        // 제목 변경
        wallpaper.changePaperTitle(wallPaperDTO.getPaperTitle());

        // 기존 이미지 목록 삭제 후 새로 추가
        wallpaper.clearWallPaperList();
        List<String> uploadFileNames = wallPaperDTO.getUploadFileNames();
        if(uploadFileNames != null && !uploadFileNames.isEmpty()) {
            uploadFileNames.forEach(wallpaper::addImageString);
        }

        wallPaperRepository.save(wallpaper);
    }



    // 해당 월페이퍼 게시글(?) 삭제
    @Override
    // public void remove(Long ord, Long userID)
    public void remove(Long ord, String userID) {

        WallPaper wallPaper = wallPaperRepository.findById(ord)
                .filter(w -> w.getWriter().getUserID().equals(userID))
                .orElseThrow(() -> new RuntimeException("해당 월페이퍼를 올린 유저만 삭제 가능합니다."));

        wallPaperRepository.delete(wallPaper);
    }

    // 이 부분이 좀 어렵구만
    private WallPaper dtoToEntity(WallPaperDTO wallPaperDTO) {
        // 월페이퍼는 해당 로그인 사용자만 볼 거기 때문에(월페이퍼를 모든 사용자가 공유하는게 아님)
        WallPaper wallPaper = WallPaper.builder()
                .ord(wallPaperDTO.getOrd())
                .paperTitle(wallPaperDTO.getPaperTitle())
                .build();

        List<String> uploadFileNames = wallPaperDTO.getUploadFileNames();

        uploadFileNames.forEach(wallPaper::addImageString);

        return wallPaper;
    }

    // 이거도 다시보니 이해하기 좀 어렵네
    private WallPaperDTO entityToDTO(WallPaper wallPaper) {
        WallPaperDTO wallPaperDTO = WallPaperDTO.builder()
                .ord(wallPaper.getOrd())
                .paperTitle(wallPaper.getPaperTitle())
                .build();

        List<WallPaperImage> wallPaperImageList = wallPaper.getWallPaperImageList();

        if(wallPaperImageList == null || wallPaperImageList.isEmpty()) {
            return wallPaperDTO;
        }

        // 저장된 이미지의 이름 문자열 조회?
        List<String> fileNameList = wallPaperImageList.stream().map(productImage ->
                productImage.getFileName()).toList();

        wallPaperDTO.setUploadFileNames(fileNameList);

        return wallPaperDTO;
    }

    @Override
    public WallPaperDTO getRandomWallpaper(String userID) {List<WallPaper> wallpapers = wallPaperRepository.findByWriter_UserID(userID);
        if (wallpapers.isEmpty()) {
            return null;  // 에러 대신 null 반환으로 바꾸자
        }

        int index = new Random().nextInt(wallpapers.size());
        return entityToDTO(wallpapers.get(index));
    }
}
