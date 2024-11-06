package com.shallwecode.backend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shallwecode.backend.problem.application.dto.SendCodeDTO;
import com.shallwecode.backend.problem.domain.service.CodingRoomDomainService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;

@Component
@RequiredArgsConstructor
public class ChatHandler extends TextWebSocketHandler {

    private final CodingRoomDomainService codingRoomDomainService;

    /* JSON -> 객체
     * 객체 -> JSON 변환해주는 Mapper */
    private final ObjectMapper objectMapper;

    /* 코딩방에 접속한 Session 을 추적 관리할 List */
    private final List<WebSocketSession> userList = new ArrayList<>();

    /* 코딩방 별 채팅을 구분하기 위한 Session Map */
    private final Map<Integer, List<WebSocketSession>> codingRoomSessionMap = new HashMap<>();

    /* 세션별 채팅 전달 */
    @Override
    protected void handleTextMessage(@NonNull WebSocketSession session, @NonNull TextMessage message) throws Exception {

        List<WebSocketSession> sessionsInRoom = codingRoomSessionMap.get(getCodingRoomUniqueNum(session));

        /* 서버 데이터 수신 */
        String payload = message.getPayload();

        /* json Object 선언 */
        JSONObject jsonObject = new JSONObject(payload);

        /* type 추출 */
        String type = jsonObject.getString("type");

        if(type.equals("chat")) {
            /* 채팅은 별도의 작업없이 바로 모든 Session 에게 채팅 메시지 전달 */
            sendMessageAllSession(sessionsInRoom, message);
        } else if(type.equals("code")){
            /* 아니라면 코드이므로 코딩방에 협업 코드를 실시간으로 통신하고 DB에 갱신해야 함. */
            /* 먼저 데이터를 송신한다. */
            sendMessageAllSession(sessionsInRoom, message);

            /* JSON 데이터를 SendMessageDTO 객체로 변환 */
            SendCodeDTO sendCodeDTO = objectMapper.readValue(payload, SendCodeDTO.class);

            /* 코드 DB에 저장 */
            codingRoomDomainService.updateCode(sendCodeDTO);
        }
    }

    /* WebSocket 연결 되었을 때
    * 채팅방 별로 관리할 List 에 추가 함. */
    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) throws Exception {
        codingRoomSessionMap.computeIfAbsent(getCodingRoomUniqueNum(session), k -> userList).add(session);
    }

    /* WebSocket 연결이 해제 되었을 때 */
    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus status) throws Exception {
        userList.remove(session);
    }

    /* url 에서 code 방 고유번호 추출 */
    private int getCodingRoomUniqueNum(WebSocketSession session) {
        String uri = Objects.requireNonNull(session.getUri()).toString();
        return Integer.parseInt(uri.split("ws/coding-room/")[1]);
    }

    /* 모든 세션 메시지 전송 */
    private void sendMessageAllSession(List<WebSocketSession> sessionsInRoom, TextMessage message) throws IOException {
        if (sessionsInRoom != null) {
            for (WebSocketSession wsSession : sessionsInRoom) {
                wsSession.sendMessage(message);
            }
        }
    }
}
