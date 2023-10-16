package websocket.contact.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Member {
    @Id @GeneratedValue
    @Column( name = "MEMBER_ID" )
    private Long id;
    private String name;
    private String password;
    private String email;
    private String nickname;
}
