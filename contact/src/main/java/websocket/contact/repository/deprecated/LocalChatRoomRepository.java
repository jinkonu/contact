package websocket.contact.repository.deprecated;

import jakarta.annotation.PostConstruct;
import websocket.contact.domain.ChatRoom;
import websocket.contact.domain.Member;
import websocket.contact.repository.interfaces.ChatRoomRepository;

import java.util.*;

//@Repository
public class LocalChatRoomRepository implements ChatRoomRepository {
    private Map<UUID, ChatRoom> chatRooms;

    @PostConstruct
    public void init() {
        chatRooms = new LinkedHashMap<>();
    }

    // 전체 채팅방 조회
    @Override
    public List<ChatRoom> findAll() {
        List<ChatRoom> roomList = new ArrayList<>(chatRooms.values());
        Collections.reverse(roomList);  // 최근 채팅방 순으로 정렬
        return roomList;
    }

    // id로 채팅방 조회
    @Override
    public ChatRoom findById(UUID roomId) {
        return chatRooms.get(roomId);
    }

    // 생성된 채팅방 추가
    @Override
    public void save(ChatRoom chatRoom) {
        chatRoom.setRoomId(UUID.randomUUID());
        chatRooms.put(chatRoom.getRoomId(), chatRoom);
    }

    // 채팅방 내 모든 회원 조회
    @Override
    public List<Member> findMembers(UUID roomId) {
        return chatRooms.get(roomId).getMembers();
    }
}
