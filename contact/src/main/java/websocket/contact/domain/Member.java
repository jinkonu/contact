package websocket.contact.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Data
@Entity
public class Member {
    @Id @GeneratedValue( strategy = GenerationType.SEQUENCE )
    private Long id;
    private String name;
    private String nickname;
    private String password;

    @ManyToOne( fetch = FetchType.LAZY, cascade = CascadeType.ALL )
    @JoinColumn( name = "ROOM_ID" )
    private ChatRoom chatRoom;

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
        chatRoom.getMembers().add(this);
    }
}
