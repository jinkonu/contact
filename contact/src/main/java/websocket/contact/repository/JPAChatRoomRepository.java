package websocket.contact.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import websocket.contact.domain.ChatRoom;
import websocket.contact.domain.Member;
import websocket.contact.repository.interfaces.ChatRoomRepository;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class JPAChatRoomRepository implements ChatRoomRepository {
    private final EntityManager em;

    @Override
    public List<ChatRoom> findAll() {
        return em.createQuery("select cr from ChatRoom cr", ChatRoom.class).getResultList();
    }

    @Override
    public ChatRoom findById(UUID roomId) {
        return em.find(ChatRoom.class, roomId);
    }

    @Override
    public void save(ChatRoom chatRoom) {
        em.persist(chatRoom);
    }

    @Override
    public List<Member> findMembers(UUID roomId) {
        return findById(roomId).getMembers();
    }
}
