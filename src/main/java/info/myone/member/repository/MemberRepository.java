package info.myone.member.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import info.myone.member.domain.Member;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

	private final EntityManager em;
	
	public void save(Member member) {
		em.persist(member);
	}
	
	public Optional<Member> findOne(String memberId) {
		return Optional.ofNullable(em.find(Member.class, memberId));
	}
	
	public List<Member> findByName(String name) {
		return em.createQuery("select m from Member m where m.name = :name", Member.class).setParameter("name", name).getResultList();
	}
	
	public List<Member> findAll() {
		return em.createQuery("select m from Member m", Member.class).getResultList();
	}
	
	
	
}
