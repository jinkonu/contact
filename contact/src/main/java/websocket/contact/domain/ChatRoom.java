package websocket.contact.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class ChatRoom {
    @Id @GeneratedValue( strategy = GenerationType.UUID )
    @Column( name = "ROOM_ID" )
    private UUID roomId;                                // 채팅방 id
    private String roomName;                            // 채팅방 이름
    private boolean secret;                             // 채팅방 비밀번호 여부
    private String roomPwd;                             // 채팅방 비밀번호

    @OneToMany( fetch = FetchType.LAZY )
    @JoinColumn( name = "ROOM_ID" )
    private List<Member> members = new ArrayList<>();   // 채팅방 참가자
    private int userCount;                              // 채팅방 참가자 수
    private int maxUserCnt;                             // 채팅방 최대 참가자 수

    public static ChatRoom createRoom(String roomName, String roomPwd, boolean secretChk, int maxUserCnt) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setRoomName(roomName);
        chatRoom.setRoomPwd(roomPwd);
        chatRoom.setSecret(secretChk);
        chatRoom.setMaxUserCnt(maxUserCnt);
        return chatRoom;
    }
}
