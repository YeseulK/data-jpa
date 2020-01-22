package study.datajpa.repository;

import org.springframework.stereotype.Repository;
import study.datajpa.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberJpaRepository {

    // 기본적 CRUD
    @PersistenceContext
    private EntityManager em;

    public Member save(Member member) {
        em.persist(member); //저장
        return member;
    }

    public void delete(Member member){
        em.remove(member);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList(); //리스트로 반환
    }
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }
    public long count() {
        return em.createQuery("select count(m) from Member m", Long.class)
                .getSingleResult(); //단건으로 반환
    }

    public Member find(Long id) {
        return em.find(Member.class, id); //자동 조회
    }

    // 순수 JPA
    public List<Member> findByUsernameAndAgeGreaterThan(String username, int age){
        return em.createQuery("select  m from Member m where m.username = :username and m.age > :age")
                .setParameter("username", username)
                .setParameter("age", age)
                .getResultList();
    }

    // 순수 JPA 페이징과 정렬
    public List<Member> findByPage(int age, int offset, int limit) {
        return em.createQuery("select m from Member m where m.age = :age order by m.username desc")
                        .setParameter("age", age)
                        .setFirstResult(offset) // 어디서부터
                        .setMaxResults(limit) // 개수 몇개
                        .getResultList();
    }
    public long totalCount(int age) {
        return em.createQuery("select count(m) from Member m where m.age = :age", Long.class)
                .setParameter("age", age)
                .getSingleResult(); //카운트 1개니까 SingleResult
    }

    //JPA를 사용한 벌크성 수정 쿼리 테스트
    public int bulkAgePlus(int age) {
        int resultCount = em.createQuery(
                "update Member m set m.age = m.age + 1" +
                        "where m.age >= :age")
                .setParameter("age", age)
                .executeUpdate();
        return resultCount;
    }

}