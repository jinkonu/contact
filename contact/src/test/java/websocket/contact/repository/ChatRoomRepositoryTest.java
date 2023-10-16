package websocket.contact.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import websocket.contact.domain.ChatRoom;
import websocket.contact.domain.Member;
import websocket.contact.repository.interfaces.ChatRoomRepository;
import websocket.contact.repository.interfaces.MemberRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Transactional( readOnly = true )
@SpringBootTest
class ChatRoomRepositoryTest {
    @Autowired
    ChatRoomRepository chatRoomRepository;
    @Autowired
    MemberRepository memberRepository;

    static ChatRoom room1;
    static ChatRoom room2;

    @Transactional( readOnly = true )
    @BeforeEach
    void setUp() {
        room1 = ChatRoom.createRoom("room1", "123", false, 8);
        room2 = ChatRoom.createRoom("room2", "123", false, 8);

        chatRoomRepository.save(room1);
        chatRoomRepository.save(room2);
    }

    // *** findAll()은 최근 채팅방 순으로 정렬하기 때문에 먼저 개설된 room1이 [1]의 인덱스를 가짐 ***
    @Test
    void findAll() {
        // given
        chatRoomRepository.save(room1);
        chatRoomRepository.save(room2);

        // when
        List<ChatRoom> roomList = chatRoomRepository.findAll();

        // then
        System.out.println(roomList.size());
//        assertThat(roomList.get(1).getRoomId()).isEqualTo(room1.getRoomId());
//        assertThat(roomList.get(0).getRoomId()).isEqualTo(room2.getRoomId());
    }

    @Test
    void findById() {
        // given

        // when
        ChatRoom findChatRoom = chatRoomRepository.findById(room1.getRoomId());

        // then
        assertThat(findChatRoom.getRoomId()).isEqualTo(room1.getRoomId());
    }

    @Test
    @Transactional( readOnly = false )
    @Rollback( value = false )
    void saveRoom() {
        // given
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setRoomName("room");
        chatRoomRepository.save(chatRoom);

        // when
        ChatRoom findChatRoom = chatRoomRepository.findById(chatRoom.getRoomId());

        // then
        assertThat(findChatRoom).isSameAs(chatRoom);
    }

    @Test
    void findMembers() {
        // given
        Member member1 = new Member();
        member1.setName("m1");
        Member member2 = new Member();
        member2.setName("m2");

        memberRepository.save(member1);
        memberRepository.save(member2);

        // then
        List<Member> members = chatRoomRepository.findMembers(room1.getRoomId());
        assertThat(members.size()).isEqualTo(2);
    }
}