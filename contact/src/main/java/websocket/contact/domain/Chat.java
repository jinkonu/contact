package websocket.contact.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Data
@AllArgsConstructor
public class Chat {
    public enum MessageType { ENTER, TALK, LEAVE }

    private MessageType type;           // 메시지 타입
    private UUID        roomId;         // 방 번호
    private Long        senderId;       // 송신자의 회원 식별자
    private String      message;        // 메시지
}
