package org.zerock.leekiye.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.leekiye.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // Role(권한)도 이메일이랑 같이 가져올것
    @EntityGraph(attributePaths = {"memberRoleList"}) // 여기 attributePaths 는 뭐지
    @Query("select m from Member m where m.userID = :userID")
    // 아래는 리팩토링 한거
    Optional<Member> getWithRoles(@Param("userID") String userID);
    Optional<Member> findByUserID(String userID);

    // 이미 존재하는 아이디인 경우 가입 불가 시키기
    // 리포지토리에 boolean 메소드 적는게 맞나?
    // @Query("SELECT COUNT(*) > 0 FROM Member WHERE userID = ?")
    boolean existsByUserID(String userID);

}
