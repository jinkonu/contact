package websocket.contact.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import websocket.contact.domain.ChatRoom;
import websocket.contact.service.ChatRoomService;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/chat")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    // 채팅방 리스트 화면
    @GetMapping
    public String rooms(Model model) {
        List<ChatRoom> rooms = chatRoomService.findAllRooms();
        model.addAttribute("list", rooms);

        log.info("SHOW ALL chatrooms {}", rooms);
        return "/chat/roomList";
    }

    // 채팅방 생성
    // 생성 후 채팅방 리스트 화면으로 복귀
    @PostMapping("/createRoom")
    public String createRoom(@RequestParam("roomName") String roomName,
                             @RequestParam("roomPwd") String roomPwd,
                             @RequestParam("secretChk") boolean secretChk,
                             @RequestParam(value = "maxUserCnt", defaultValue = "2") String maxUsercnt) {
        ChatRoom chatRoom = chatRoomService.createRoom(roomName, roomPwd, secretChk, Integer.parseInt(maxUsercnt));

        log.info("CREATE chatroom {}", chatRoom);
        return "redirect:/chat";
    }

    // 채팅방 입장
    @GetMapping("/room")
    public String roomDetail(Model model, String roomId) {
        UUID roomUUID = UUID.fromString(roomId);
        model.addAttribute("chatRoom", chatRoomService.findOneRoom(roomUUID));

        log.info("ENTER CHATROOM {}", chatRoomService.findOneRoom(roomUUID));
        return "/chat/chatRoom";
    }

    // 특정 채팅방 조회
    @GetMapping("/room/find/{roomId}")
    @ResponseBody
    public ChatRoom roomInfo(@PathVariable String roomId) {
        UUID roomUUID = UUID.fromString(roomId);
        return chatRoomService.findOneRoom(roomUUID);
    }
}

