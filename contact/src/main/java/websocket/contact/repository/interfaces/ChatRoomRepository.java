package websocket.contact.repository.interfaces;

import websocket.contact.domain.ChatRoom;
import websocket.contact.domain.Member;

import java.util.List;
import java.util.UUID;

public interface ChatRoomRepository {
    // 전체 채팅방 조회
    List<ChatRoom> findAll();

    // id로 채팅방 조회
    ChatRoom findById(UUID roomId);

    // 채팅방 추가
    void save(ChatRoom chatRoom);

    // 채팅방 내 모든 회원 조회
    List<Member> findMembers(UUID roomId);

    // 채팅방 내 회원 추가
    //    void addMember(UUID roomId, Long memberId);
    // 채팅방 내 회원 삭제
    //    void deleteMember(UUID roomId, Long memberId);
}
