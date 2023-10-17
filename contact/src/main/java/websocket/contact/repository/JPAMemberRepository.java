package websocket.contact.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import websocket.contact.domain.ChatRoom;
import websocket.contact.domain.Member;
import websocket.contact.repository.interfaces.MemberRepository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class JPAMemberRepository implements MemberRepository {
    private final EntityManager em;

    @Override
    public void save(Member member) {
        em.persist(member);
    }

    @Override
    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    @Override
    public Member findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getSingleResult();
    }
}
