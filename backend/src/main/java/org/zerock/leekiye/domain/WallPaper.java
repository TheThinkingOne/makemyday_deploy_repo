package org.zerock.leekiye.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WallPaper {

    // 월페이퍼에 사진에 관한건 월페이퍼이미지 라는 도메인 새로 파서 하자
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 여길 아마 int 에서 Long 으로 바꿔야 할듯
    private Long ord;

    private String paperTitle;

    @OneToMany(mappedBy = "wallPaper", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<WallPaperImage> wallPaperImageList = new ArrayList<>();

    // 이걸 어캐 구현해야 하지
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Member writer; // 작성자 정보 추가(근데 이게 꼭 필요한가?)
    // 로그인 한 사용자 별로 해당 사용자가 등록한 월페이퍼만 보이게 하려고 필요한 것인가?)
    // 이렇게 하고 나서 나중에 관리자가 야짤 같은거 지울..?
    // Todo 들어갔을 때 해당 유저가 쓴 글만 보이게 할것임

    // 해당 월페이퍼의 제목 변경
    public void changePaperTitle(String paperTitle) {
        this.paperTitle = paperTitle;
    }

    //
    public void addWallPaperImage(WallPaperImage wallPaperImage) {
        wallPaperImage.setOrd(wallPaperImageList.size());
        wallPaperImage.setWallPaper(this); //
        wallPaperImageList.add(wallPaperImage);
    }

    public void addImageString(String fileName) {
        WallPaperImage wallPaperImage = WallPaperImage.builder()
                .fileName(fileName)
                .build();

        addWallPaperImage(wallPaperImage);
    }

    public void clearWallPaperList() {
        this.wallPaperImageList.clear();
    }

    public void setWriter(Member member) {
        this.writer = member;
    }


}
