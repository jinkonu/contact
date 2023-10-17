package websocket.contact.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class TmpMember {
    @Id
    private Long id;
    private String name;
}
