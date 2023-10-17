package websocket.contact.controller;

// WebSocket 세션은 http 세션과 다르다.
// WebSocket 세션은 Handshake를 통해 넘어온 http 프로토콜을 webSocket 프로토콜로 변환하면서 생성된다.

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import websocket.contact.domain.Chat;
import websocket.contact.domain.Member;
import websocket.contact.domain.TmpMember;
import websocket.contact.repository.interfaces.ChatRoomRepository;
import websocket.contact.repository.interfaces.MemberRepository;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ChatController {
    private final SimpMessageSendingOperations template;
    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;

    // MessageMapping을 통해 클라이언트가 보내는 메시지의 경로를 일치시킬 수 있다.
    // 즉, 단순한 http 요청과 다르기 때문에 이러한 별도의 어노테이션이 있는 것.
    // 클라이언트는 "/chat/enter"로 메시지를 보내고, enter()로 매핑되어 처리한다.
    // 처리된 결과는 다시 메시지로서 "/sub/chat/room/{roomId}"로 반환되어, 모든 sub에게 돌아간다.
    @MessageMapping("/chat/enter")
    public void enter(@Payload Chat chat, SimpMessageHeaderAccessor headerAccessor) {
        // 채팅방에 유저 추가 로직 구현
        // 유저 입장에서는 자신이 이 채팅방에 접속한 것을 추가,
        // 서버 입장에서는 채팅방에 유저가 추가된 것을 기록
//        Member member = memberRepository.findOne(chat.getSenderId());

        // 반환 결과를 socket session에 userUUID와 roomId를 저장
        // 즉, session에 사용자를 식별할 userUUID와 참여중인 채팅방을 추가하는 것
        // 추후에 로그인 후 채팅방을 접근하는 식으로 바꾸면 이 부분도 변경될듯

        headerAccessor.getSessionAttributes().put("userName", chat.getSender());
        headerAccessor.getSessionAttributes().put("roomId", chat.getRoomId());

        // 클라이언트에게 보낼 챗을 설정하고, convertAndSend를 통해 되돌림
        chat.setMessage(chat.getSender()+ " 님이 입장하셨습니다 ...");
        template.convertAndSend("/sub/chat/room/" + chat.getRoomId(), chat);
    }

    // 유저의 메시지 전송
    @MessageMapping("/chat/send")
    public void send(@Payload Chat chat) {
        log.info("CHAT {}", chat);
        template.convertAndSend("/sub/chat/room/" + chat.getRoomId(), chat);
    }

    // 유저 퇴장 시에는 EventListener를 통해 퇴장 확인
    @EventListener
    public void webSocketDisconnectListener(SessionDisconnectEvent event) {
        log.info("DISCONNECT EVENT {}", event);

        // "세션 해제"라는 이벤트를 보낸 메시지를 가지고 StompHeaderAccessor 객체를 가져옴
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        // 메시지 헤더의 세션으로부터 유저 id와 채팅방 번호를 가져온다.
        Member member = memberRepository.findOne((Long) headerAccessor.getSessionAttributes().get("userId"));
        UUID roomId = (UUID) headerAccessor.getSessionAttributes().get("roomId");

        log.info("USER DISCONNECTED : {}", member.getName());
        // 퇴장 메시지 클라이언트에 전송
        Chat chat = new Chat(
                Chat.MessageType.LEAVE,
                roomId,
                member.getId(),
                member.getName() + " 님이 채팅방을 나가셨습니다...",
                "konu"
        );

        template.convertAndSend("sub/chat/room/" + roomId, chat);
    }

    // 채팅에 참여중인 유저 리스트 반환
    @GetMapping("/chat/userList")
    @ResponseBody
    public List<TmpMember> userList(UUID roomId) {
        return chatRoomRepository.findMembers(roomId);
    }
}
