package org.zerock.leekiye.repository.search;

import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.leekiye.domain.QQuotes;
import org.zerock.leekiye.domain.Quotes;
import org.zerock.leekiye.dto.PageRequestDTO;

import java.util.List;

@Log4j2
public class QuotesSearchImpl extends QuerydslRepositorySupport implements QuotesSearch {

    public QuotesSearchImpl() {
        super(Quotes.class);
    }

    @Override
    public Page<Quotes> quotesSearch(PageRequestDTO pageRequestDTO) {

        log.info("Quotes Search Ongoing");

        QQuotes quotes = QQuotes.quotes1;
        JPQLQuery<Quotes> query = from(quotes);

        // 정렬 집적 지정해서 타입별 정렬 오류 안나게 하기
        query.orderBy(quotes.qno.desc());

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() -1,
                pageRequestDTO.getSize()

                // 아래 주석친거는 위에서 직접 정렬 지정했기 때문에 필요 없어짐
                //Sort.by("qno").descending() // 여기에 ord 말고 qno 되어 있었어야 했는데
                // 그렇게 안되있어서 정렬기준 못 찾아서 오류 떴었음
        );

        this.getQuerydsl().applyPagination(pageable, query);

        List<Quotes> quotesList = query.fetch();

        long total = query.fetchCount();

        return new PageImpl<>(quotesList, pageable, total);
    }


}
