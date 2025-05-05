package org.zerock.leekiye.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WallPaperDTO {

    // 사용자가 입력한 월페이퍼들의 리스트를 볼 수 있게 하자

    private String paperTitle;

    private String fileName;

    private Long ord;

    @Builder.Default
    private List<MultipartFile> files = new ArrayList<>(); // 넣을때 사용

    @Builder.Default
    private List<String> uploadFileNames = new ArrayList<>(); // 조회할때 사용
}
