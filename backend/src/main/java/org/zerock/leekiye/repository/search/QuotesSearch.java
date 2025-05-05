package org.zerock.leekiye.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.zerock.leekiye.domain.Quotes;
import org.zerock.leekiye.dto.PageRequestDTO;

public interface QuotesSearch {
    Page<Quotes> quotesSearch(PageRequestDTO pageRequestDTO);
}
