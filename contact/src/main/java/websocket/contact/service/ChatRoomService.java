package websocket.contact.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import websocket.contact.domain.ChatRoom;
import websocket.contact.domain.Member;
import websocket.contact.repository.interfaces.ChatRoomRepository;
import websocket.contact.repository.interfaces.MemberRepository;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional( readOnly = true )
@Service
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;

    // 채팅방 생성
    @Transactional( readOnly = false )
    public ChatRoom createRoom(String roomName, String roomPwd, boolean secretChk, int maxUserCnt) {
        ChatRoom chatRoom = ChatRoom.createRoom(roomName, roomPwd, secretChk, maxUserCnt);
        chatRoomRepository.save(chatRoom);
        return chatRoom;
    }

    // 채팅방 전부 조회
    public List<ChatRoom> findAll() {
        return chatRoomRepository.findAll();
    }

    // 채팅방 id로 조회
    public ChatRoom findById(UUID roomId) {
        return chatRoomRepository.findById(roomId);
    }

    // 채팅방 내 모든 회원 조회
    public List<Member> findMembers(UUID roomId) {
        return chatRoomRepository.findMembers(roomId);
    }
}
