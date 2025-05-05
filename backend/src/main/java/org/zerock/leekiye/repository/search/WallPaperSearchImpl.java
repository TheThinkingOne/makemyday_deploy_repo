package org.zerock.leekiye.repository.search;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.leekiye.domain.QWallPaper;
import org.zerock.leekiye.domain.QWallPaperImage;
import org.zerock.leekiye.domain.WallPaper;
import org.zerock.leekiye.dto.PageRequestDTO;
import org.zerock.leekiye.dto.PageResponseDTO;
import org.zerock.leekiye.dto.WallPaperDTO;

import java.util.List;
import java.util.Objects;

@Log4j2
public class WallPaperSearchImpl extends QuerydslRepositorySupport implements WallPaperSearch {

    public WallPaperSearchImpl() {
        super(WallPaper.class);
        //super(WallPaper.class);
    }

    @Override
    public PageResponseDTO<WallPaperDTO> searchWallpaperList(PageRequestDTO pageRequestDTO) {

        log.info("searchWallpaperList 실행");

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() -1, // 페이지 번호
                pageRequestDTO.getSize(), // 페이지 request dto 의 크기
                Sort.by("pno").descending()
        );

        QWallPaper wallPaper = QWallPaper.wallPaper;
        QWallPaperImage wallPaperImage = QWallPaperImage.wallPaperImage;

        JPQLQuery<WallPaper> query = from(wallPaper);

        query.leftJoin(wallPaper.wallPaperImageList, wallPaperImage);

        query.where(wallPaperImage.ord.eq(0));

        Objects.requireNonNull(getQuerydsl()).applyPagination(pageable,query);

        List<Tuple> wallPaperList = query.select(wallPaper, wallPaperImage).fetch();

        long count = query.fetchCount();

        return null;
    }
}
