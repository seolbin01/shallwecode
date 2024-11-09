package com.shallwecode.backend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shallwecode.backend.common.exception.CustomException;
import com.shallwecode.backend.common.exception.ErrorCode;
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

    /* JSON -> 객체
     * 객체 -> JSON 변환해주는 Mapper */
    private final ObjectMapper objectMapper;

    /* 코딩방 별 채팅을 구분하기 위한 Session Map */
    private final Map<Integer, List<WebSocketSession>> codingRoomSessionMap = new HashMap<>();

    /* List 설계 미스... 세션 온오프라인 체크용 리스트 임시로 구축 */
    private final Map<String, String> sessionList = new HashMap<>();

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

        switch (type) {
            case "statusCheck" : {
                String userId = String.valueOf(jsonObject.getInt("userId"));
                String status = jsonObject.getString("status");
                System.out.println(userId);
                System.out.println(status);

                sessionList.put(userId, status);

                // "type"과 "sessionList"를 포함한 맵 생성
                Map<String, Object> responseMap = new HashMap<>();
                responseMap.put("type", "statusCheck");
                responseMap.put("sessionList", sessionList); // sessionList 는 리스트 형태로 추가

                // JSON 으로 직렬화하여 클라이언트에 전송할 채팅 준비
                String responsePayload = objectMapper.writeValueAsString(responseMap);

                System.out.println(responsePayload);

                sendMessageAllSession(sessionsInRoom, new TextMessage(responsePayload));
            } break;

            case "chat" : {
                sendMessageAllSession(sessionsInRoom, message);
            } break;
        }
    }

    /* WebSocket 연결 되었을 때
    * 채팅방 별로 관리할 List 에 추가 함. */
    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) throws Exception {
        /* 코딩방 Id 추출 */
        Integer codingRoomId = getCodingRoomUniqueNum(session);

        /* 코딩방 세션 추출 */
        List<WebSocketSession> sessionsInRoom = codingRoomSessionMap.get(codingRoomId);

        if(sessionsInRoom != null)
            System.out.println(sessionsInRoom.size());

        /* 세션이 비었거나 또는 Null 그리고 5명 이하라면 세션 추가 */
        if(sessionsInRoom == null || sessionsInRoom.isEmpty()) {
            codingRoomSessionMap.computeIfAbsent(getCodingRoomUniqueNum(session), k -> new ArrayList<>()).add(session);
        } else if(sessionsInRoom.size() < 5){
            /* 비어있지 않다면 해당 방 세션 리스트에 세션 추가 */
            sessionsInRoom.add(session);
        } else {
            try {
                /* 5명 이상으로 갈 시 제한 인원 초과한 예외 발생 */
                throw new CustomException(ErrorCode.TOO_MANY_REQUESTS_ROOM);
            }catch(CustomException e) {
                /* 에러 메시지 해당 클라이언트에게 전송 */
                session.sendMessage(new TextMessage("해당 코딩방의 인원이 가득 찼습니다."));
                session.close();
            }
        }
    }

    /* WebSocket 연결이 해제 되었을 때 */
    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus status) {
        // 코딩방 Id 추출
        Integer codingRoomId = getCodingRoomUniqueNum(session);

        // 해당 세션 리스트 추출
        List<WebSocketSession> sessionsInRoom = codingRoomSessionMap.get(codingRoomId);

        // 해당 session 삭제
        if(sessionsInRoom != null) {
            sessionsInRoom.remove(session);
        }
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
