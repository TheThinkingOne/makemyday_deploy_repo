package org.zerock.leekiye.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.leekiye.domain.WallPaper;
import org.zerock.leekiye.repository.search.WallPaperSearch;

import java.util.List;
import java.util.Optional;

public interface WallPaperRepository extends JpaRepository<WallPaper, Long>, WallPaperSearch {
    // 상품 등록번호가 int 로 되어 있어서 extends JpaRepository<WallPaper, Integer> 가 맞나?

    @EntityGraph(attributePaths = "wallPaperImageList")
    @Query("SELECT w FROM WallPaper w WHERE w.ord = :ord")
    Optional<WallPaper> selectOne(@Param("ord") Long ord);

    @Query("SELECT w, wl FROM WallPaper w LEFT JOIN w.wallPaperImageList wl where wl.ord = 0")
    Page<Object[]> selectList(Pageable pageable);

    // 여기 쿼리 만들어야 함
    @Query("SELECT w, i FROM WallPaper w LEFT JOIN w.wallPaperImageList i " +
            "WHERE w.writer.id = :writerId AND (i.ord = 0 OR i IS NULL)")
    Page<Object[]> selectListForUser(@Param("writerId") Long writerId, Pageable pageable);

    // 여기 추가해야 함
    List<WallPaper> findByWriter_UserID(String userID);


    // @Query
    // Page<Object[]> selectListForUser(Pageable, pageable)

}
