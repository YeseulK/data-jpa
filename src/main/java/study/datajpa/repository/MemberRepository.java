package study.datajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

import javax.persistence.Entity;
import javax.persistence.QueryHint;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom{

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);
    //메소드 이름으로 쿼리 생성기능은 파라미터가 3개이상되면 너무 길어진다는 단점

    // @Query 어노테이션을 사용해서 리파지토리 인터페이스에 쿼리 직접 정의
    // 장점: 복잡한 JPQL을 해결할 수 있다. 애플리케이션 로딩 시점에 오류를 잡아낸다.
    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member m")
    List<String> findUsernameList();

    //DTO 조회하기
    @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    //파라미터 바인딩
    @Query("select m from Member m where m.username = :name")
    Member findMembers(@Param("name") String username);

    //파라미터 바인딩 - 컬렉션 (Collection 타입으로 in절 지원)
    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") Collection<String> names);

    //반환 타입
    List<Member> findListByUsername(String name); //컬렉션
    Member findMemberByUsername(String name); //단건
    Optional<Member> findOptionalByUsername(String name); //단건 Optional

    //페이징과 정렬 사용 예제
    Page<Member> findByAge(int age, Pageable pageable);

    /**
    Page<Member> findByUsername(String name, Pageable pageable); //count 쿼리 사용
    Slice<Member> findByUsername(String name, Pageable pageable); //count 쿼리 사용 안함
    List<Member> findByUsername(String name, Pageable pageable); //count 쿼리 사용 안함
    List<Member> findByUsername(String name, Sort sort);
     */

    // 스프링 데이터 JPA를 사용한 벌크성 수정 쿼리
    @Modifying(clearAutomatically = true) //영속성 컨텍스트 초기화
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);


    // N+1 문제 해결을 위한 JPQL fetch join
    @Query("select m from Member m left join fetch m.team")
    List<Member> findMemberFetchJoin();

    // # 공통 메서드 오버라이드
    // fetch join과 동등하다고 보면 됨
    // JPQL없이도 객체 그래프를 한번에 엮어서 성능최적화
    @Override
    @EntityGraph(attributePaths = {"team"})
    List<Member> findAll();

    // # JPQL + 엔티티 그래프
    @EntityGraph(attributePaths = {"team"})
    @Query("select m from Member m")
    List<Member> findMemberEntityGraph();

    // # 메서드 이름으로 쿼리에서 특히 편리하다.
    @EntityGraph(attributePaths = {"team"})
    List<Member> findEntityGraphByUsername(@Param("username") String username);


    // JPA 쿼리 힌트 (SQL 힌트가 아니라 JPA 구현체에게 제공하는 힌트)
    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Member findReadOnlyByUsername(String username);






}
