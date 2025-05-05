package org.zerock.leekiye.dto;

import lombok.*;
import org.zerock.leekiye.domain.SavePeriod;

import java.time.LocalDate;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TodoDTO {

    // DTO의 변수명들은 도메인 클래스것과 똑같이 하는게 안 헷갈리고 좋다

    private Long tno;

    private Long writerID; // 이게 없었더라

    private String title;

    private String contents;

    private LocalDate dueDate;

    private boolean isComplete;

    private SavePeriod savePeriod;

    private LocalDate createdAt;

    public Long getWriterId() {
        return writerID;
    }

    public void setWriterID(Long writerID) {
        this.writerID = writerID;
    }

    // 어차피 로그인한 해당 사용자만 글 보기, 수정, 삭제 할 수 있는데 여기에
    // writer 도 넣어야 하나? 잘 모르겠네


}
