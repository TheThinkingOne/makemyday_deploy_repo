package org.zerock.leekiye.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WallPaperImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private int ord;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallpaper_id")
    private WallPaper wallPaper;

    public void setOrd(int ord) {
        this.ord = ord;
    }
}

