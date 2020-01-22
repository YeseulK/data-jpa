package study.datajpa.repository;

import study.datajpa.entity.Member;

import java.util.List;

/**
 * <확장 기능> 사용자 정의 리포지토리 구현
 * - 다양한 이유로 인터페이스의 메서드를 직접 구현하고 싶다면?
 * JPA 직접 사용( EntityManager )
 * 스프링 JDBC Template 사용
 * MyBatis 사용
 * 데이터베이스 커넥션 직접 사용
 * Querydsl 사용
 */
public interface MemberRepositoryCustom {
    List<Member> findMemberCustom();

}
