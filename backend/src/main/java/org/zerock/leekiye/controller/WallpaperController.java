package org.zerock.leekiye.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.leekiye.dto.MemberDTO;
import org.zerock.leekiye.dto.PageRequestDTO;
import org.zerock.leekiye.dto.PageResponseDTO;
import org.zerock.leekiye.dto.WallPaperDTO;
import org.zerock.leekiye.service.WallPaperService;
import org.zerock.leekiye.util.CustomFileUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/makemyday/wallpaper")
public class WallpaperController {

    private final CustomFileUtil fileUtil;

    private final WallPaperService wallPaperService;

    // 파일(이미지) 보는 컨트롤러
    @GetMapping("/view/{fileName}") // 해당 이미지 눌렀을 때 보이게 하는 컨트롤러 메소드
    public ResponseEntity<Resource> viewFileGet(@PathVariable("fileName") String fileName) {
//        Resource resource = (Resource) fileUtil.getFile(fileName);
//
//        // 🔹 Content-Type이 올바르게 설정되지 않으면 브라우저가 차단할 수 있음
//        HttpHeaders headers = new HttpHeaders();
//        try {
//            headers.add("Content-Type", Files.probeContentType(resource.getFile().toPath()));
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to determine file type", e);
//        }
//
//        return ResponseEntity.ok()
//                .headers(headers)
//                .body(resource);
        return fileUtil.getFile(fileName);   // 이렇게 간단하게 수정?
    }

    // 해당 월페이퍼 불러오기(사용자 전용 조회)
    @GetMapping("/{ord}")
    public WallPaperDTO get(@PathVariable(name = "ord") Long ord,
                            @AuthenticationPrincipal MemberDTO memberDTO) {
        // return wallPaperService.getForUser(ord, memberDTO.getId());
        return wallPaperService.getForUser(ord, memberDTO.getUserID());
    }

    @GetMapping("/list")
    public PageResponseDTO<WallPaperDTO> list(PageRequestDTO pageRequestDTO,
                                              @AuthenticationPrincipal MemberDTO memberDTO) {
        log.info("list ====== " + pageRequestDTO);

        // 리스트 불러오기
        // return wallPaperService.getListForUser(pageRequestDTO, memberDTO.getId());
        return wallPaperService.getList(pageRequestDTO, memberDTO.getId());
    }

    // queryString
    // /list?page=3 => 매번 다른 컨텐츠가 된다
    // 매번 바뀌는경우 pathVariable 로 설계하는게 아닌 queryString 사용 권장

    // 월페이퍼 등록
    @PostMapping("/register")
    public Map<String, Long> register(@ModelAttribute WallPaperDTO dto,
                                      @AuthenticationPrincipal MemberDTO memberDTO) {

        // dto.setWriterId(memberDTO.getId()); 작성자 설정하는것도 필요함 권한땜에
        List<MultipartFile> files = dto.getFiles();

        List<String> uploadedFileNames = fileUtil.saveFiles(files);

        dto.setUploadFileNames(uploadedFileNames);

        log.info("wallPaperDTO: " + dto);
        log.info("uploaded wallpaper file names: " + uploadedFileNames);

        // 월페이퍼 등록하고 월페이퍼 ID(게시글 번호) 생성
        Long ord = wallPaperService.register(dto, memberDTO.getUserID());

        return Map.of("ord", ord);
    }

    // 월페이퍼 게시글 수정
    // 스프링은 PUT 방식의 multipart/form-data를 기본적으로 처리하지 않는다고 함
    @PostMapping("/modify/{ord}")
    public Map<String, String> modify(@PathVariable("ord") Long ord,
                                      @ModelAttribute WallPaperDTO wallPaperDTO,
                                      @AuthenticationPrincipal MemberDTO memberDTO) {
        // /{tno} 와 todoDTO 안의 tno가 일치하는지 확인
        wallPaperDTO.setOrd(ord);

        // wallPaperDTO.setWriterId(memberDTO.getId()); // 작성자 설정 필요 유저에 따른 조회 땜에

        WallPaperDTO oldWallpaperDTO = wallPaperService.get(ord);

        List<MultipartFile> files = wallPaperDTO.getFiles();
        List<String> currentUploadFileNames = fileUtil.saveFiles(files);

        // Keep files (이전에 있었는데 이번에 수정할 때도 계속 남아있는 파일) (String)
        // 이미지 3개중에 한개 지우면 2개는 그대로 있는것이 예시
        List<String> uploadedFileNames = wallPaperDTO.getUploadFileNames();
        if(currentUploadFileNames != null && !currentUploadFileNames.isEmpty()) {
            uploadedFileNames.addAll(currentUploadFileNames); // addAll 로 싹 다 넣기
        }

        log.info("Modify: " + wallPaperDTO);
        wallPaperService.modify(wallPaperDTO, memberDTO.getUserID());

        List<String> oldFileNames = oldWallpaperDTO.getUploadFileNames();
        if(oldFileNames != null && !oldFileNames.isEmpty()) { // 있는지 없는지 먼저 찾아내기

            List<String> removeFiles = // 삭제될 애들을 리스트에 담아서
                    oldFileNames.stream().filter(fileName ->
                            uploadedFileNames.indexOf(fileName) == -1).collect(Collectors.toList());
            fileUtil.deleteFiles(removeFiles); // 삭제
        } // End of if

        return Map.of("RESULT", "SUCCESS");

    }

    // 월페이퍼
    @DeleteMapping("/{ord}")
    public Map<String, String> remove( @PathVariable(name="ord") Long ord,
                                       @AuthenticationPrincipal MemberDTO memberDTO){

//        List<String> oldFileNames = wallPaperService.get(ord).getUploadFileNames();
//
//        log.info("Remove:  " + ord);
//
//        // wallPaperService.removeForUser(ord, memberDTO.getId()); // 해당 사용자가 지우는게 맞는지 검증필요
//        wallPaperService.remove(ord, memberDTO.getUserID());
//
//        fileUtil.deleteFiles(oldFileNames);

        WallPaperDTO dto = wallPaperService.getForUser(ord, memberDTO.getUserID());
        wallPaperService.remove(ord, memberDTO.getUserID());

        fileUtil.deleteFiles(dto.getUploadFileNames());

        return Map.of("RESULT", "SUCCESS");
    }




}
