package org.zerock.leekiye.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Todo {

    // 글을 작성할 때부터 보관기간을 설정할 것인가 (해당 일의 마감일 이후 몇일 후 자동삭제 이런거)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 키 생성 전략
    private Long tno;

    @Column(length = 500, nullable = false)
    private String title; // 제목

    private String contents; // 일정 상세 내용

    private LocalDate dueDate; // 일정 날짜

    private boolean isComplete;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Member writer; // 작성자 정보 추가
    // Todo 들어갔을 때 해당 유저가 쓴 글만 보이게 할것임

    @Enumerated(EnumType.STRING)
    private SavePeriod savePeriod;

    private LocalDate createdAt;

    // 게시글 보관 만료일 계산 메소드 (하루, 일주일, 반영구보관)
    // 스케쥴러로 관리
    // 마감일 기준으로 (예: 2025 0401 이 마감일이면 하루 뒤 삭제, 일주일 뒤 삭제, 영구보관 등등)
    public LocalDate getExpireDate() {
        return switch (savePeriod) {
            case ONE_DAY -> dueDate.plusDays(1);
            case ONE_WEEK -> dueDate.plusWeeks(1);
            case PERMANENT -> LocalDate.of(9999, 12, 31);
        };
    }

    // 글 작성시에 작성일 저장하는 메소드
    @PrePersist
    public void perPersist() {
        this.createdAt = LocalDate.now();
    }

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeContent(String contents) {
        this.contents = contents;
    }

    public void changeDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void changeComplete(boolean complete) {
        this.isComplete = complete;
    }

    // 여기에 보관할 기간 바꾸는 메소드도 추가해야함
    public void changeSavePeriod(SavePeriod savePeriod) {
        this.savePeriod = savePeriod;
    }

     public void setWriter(Member member) {
      this.writer = member; // 근데 이게 맞나
     }

}
