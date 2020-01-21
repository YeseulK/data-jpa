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
}