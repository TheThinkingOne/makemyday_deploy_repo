package org.zerock.leekiye.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.leekiye.domain.Quotes;
import org.zerock.leekiye.repository.search.QuotesSearch;

public interface QuotesRepository extends JpaRepository<Quotes, Long>, QuotesSearch {
    // 메롱
}
