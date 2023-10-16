package websocket.contact.repository.interfaces;

import websocket.contact.domain.ChatRoom;
import websocket.contact.domain.Member;

import java.util.List;

public interface MemberRepository {
    // 회원 저장
    void save(Member member);

    // 회원 식별자로 조회
    Member findOne(Long id);

    // 회원 전부 조회
    List<Member> findAll();

    // 회원 아이디로 조회
    Member findByName(String name);
}