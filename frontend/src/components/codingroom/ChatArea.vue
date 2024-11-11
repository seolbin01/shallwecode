<script setup>
import {ref, reactive, onMounted, onUnmounted} from "vue";
import { useAuthStore } from "@/stores/auth.js";
import axios from "axios";

const useAuth = useAuthStore();

const props = defineProps({
  codingRoomId : {
    type : String,
    required : true
  }
});

const tempObjectInfo = {
  userId : useAuth.userId,
  accessToken : useAuth.accessToken,
  refreshToken : useAuth.refreshToken
}

// 필요한 객체 생성
const coopMember = reactive([]);
const webSocket = ref({});

// 필요한 정보 조회, 협업 친구 조회
const communicateCoopInfo = async(codingRoomId) => {
  try {
    const response = await axios.get(`http://localhost:8080/api/v1/codingroom/friendList/${codingRoomId}`, {
      headers: {
        Authorization: `Bearer ${tempObjectInfo.accessToken}`
      }
    });

    for(let i = 0; i < response.data.coopList.length; i++) {
      if(tempObjectInfo.userId === response.data.coopList[i].userId) {
        const tempObject = {
          codingRoomId: response.data.coopList[i].codingRoomId,
          userId: response.data.coopList[i].userId,
          userNickname: response.data.coopList[i].userNickname,
          status: 'online'
        }
        coopMember.push(tempObject);
      } else {
        const tempObject = {
          codingRoomId: response.data.coopList[i].codingRoomId,
          userId: response.data.coopList[i].userId,
          userNickname: response.data.coopList[i].userNickname,
          status: 'offline'
        }
        coopMember.push(tempObject);
      }
    }
  } catch (error) {
    console.error('협업 친구 목록을 조회하는 데 오류가 발생했습니다.', error);
  }
}

const messages = ref([]);
const newMessage = ref('');
const isTyping = ref(false);

// 채팅 메시지 전송 함수
const sendMessage = () => {
  if (newMessage.value.trim()) {
    let userNickName = { };
    for(let i = 0; i < coopMember.length; i++) {
      if(tempObjectInfo.userId === coopMember[i].userId)
        userNickName = coopMember[i].userNickname;
    }

    const sendMess = {
      type: "chat",
      id: tempObjectInfo.userId,
      text: newMessage.value,
      sender: 'user',
      senderName: userNickName,
      timestamp: new Date().toLocaleTimeString()
    }
    messages.value.push(sendMess);
    webSocket.value.send(JSON.stringify(sendMess));
    newMessage.value = '';
  }

};

// 웹 소켓 연결 함수
const connectWebSocket = (codingRoomId) => {
  webSocket.value = new WebSocket(`ws://localhost:8080/ws/coding-room/${codingRoomId}`);
  // 연결시 온, 오프라인 구별을 위해 정보를 송신
  webSocket.value.onopen = () => {
    const statusCheck = {
      type : "statusCheck",
      userId : tempObjectInfo.userId,
      status : "online"
    };

    // statusCheck 를 송신
    webSocket.value.send(JSON.stringify(statusCheck));
  };

  // WebSocket 수신 - onmessage 는 단 한번만 설정 됨.
  webSocket.value.onmessage = (message) => {
    // 받은 메시지를 변환
    const receiveMessage = JSON.parse(message.data);

    // 상태 체크 - 접속시 보내온 상태정보 업데이트
    if(receiveMessage.type === "statusCheck") {
      // console.log(receiveMessage);
      // console.log(Object.entries(receiveMessage.sessionList));
      // for(let i = 0; i < coopMember.length; i++) {
      //   // 온라인 정보만 기록한다.
      //   if(receiveMessage.coopMember[i].status === "online")
      //   coopMember[i].status = receiveMessage.coopMember[i].status;
      // }
    }

    // 본인 메시지는 제외
    if(receiveMessage.id === tempObjectInfo.userId) return;

    if(receiveMessage.type === "chat") {
      const receiveMess = {
        type: "chat",
        id: receiveMessage.id,
        text: receiveMessage.text,
        sender: 'other',
        senderName: receiveMessage.senderName,
        timestamp: new Date().toLocaleTimeString()
      }

      messages.value.push(receiveMess);
    }
  };
}

// 웹 소켓 연결 해제
const disConnectWebSocket = () => {
  // 해제전 offline 으로 바꾸고 소캣 해제
  for(let i = 0; i < coopMember.length; i++){
    if(coopMember[i].userId === tempObjectInfo.userId)
      coopMember[i].status = "offline";
  }

  // 보낼 객체 생성
  const statusCheck = {
    type : "statusCheck",
    coopMember : []
  };
  statusCheck.coopMember = coopMember;

  // 전송 후
  webSocket.value.send(JSON.stringify(statusCheck))

  // 웹 소캣 닫음.
  webSocket.value.close();
}

// DOM 로딩 전
onMounted(async () => {
  await communicateCoopInfo(props.codingRoomId);
  connectWebSocket(props.codingRoomId);
})

onUnmounted(async() => {
  disConnectWebSocket();
});
</script>

<template>
  <div class="chat-container">
    <div class="chat-header">
      <h3>채팅</h3>
    </div>
    <div class="chat-layout">
      <div class="participants-section">
        <div class="participants-header">참여자</div>
        <div class="participants-list">
          <div v-for="coopMember in coopMember"
               :key="coopMember.userId"
               class="participant-item">
            <div class="participant-status"
                 :class="coopMember.status"></div>
            <div class="participant-name"
                 :class="coopMember.type">
              {{ coopMember.userNickname }}
            </div>
          </div>
        </div>
      </div>
      <div class="messages-section">
        <div class="chat-messages" ref="chatMessages">
          <div v-for="message in messages"
               :key="message.id"
               :class="['message', message.sender]">
            <div class="sender-name">{{ message.senderName }}</div>
            <div class="message-content">
              {{ message.text }}
            </div>
            <div class="message-timestamp">
              {{ message.timestamp }}
            </div>
          </div>
          <div v-if="isTyping" class="typing-indicator">
            <span></span>
            <span></span>
            <span></span>
          </div>
        </div>
        <div class="chat-input">
          <input
              v-model="newMessage"
              @keyup.enter="sendMessage"
              placeholder="메시지를 입력하세요..."
              type="text"
          />
          <button @click="sendMessage" :disabled="!newMessage.trim()">
            전송
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.chat-layout {
  display: flex;
  flex-direction: row;
  gap: 16px;
  height: calc(100% - 50px);
  padding: 16px;
}

.chat-container {
  height: 300px;
  margin: 16px;
  background-color: transparent;
  border-radius: 8px;
  display: flex;
  flex-direction: column;
  color: var(--background-color);
}

.chat-header {
  padding: 12px 16px;
  background-color: rgba(0, 0, 0, 0.2);
  border-radius: 8px;
  margin-bottom: 0;
}

.chat-header h3 {
  margin: 0;
}

.participants-section {
  width: 180px;
  background-color: rgba(0, 0, 0, 0.2);
  border-radius: 8px;
  padding: 12px;
  height: 200px;
}

.participants-header {
  color: #FFD700;
  font-size: 14px;
  font-weight: 600;
  margin-bottom: 12px;
  padding-bottom: 8px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
}

.participants-list {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.participant-item {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 4px 8px;
  border-radius: 4px;
  transition: background-color 0.2s;
}

.participant-item:hover {
  background-color: rgba(255, 255, 255, 0.1);
}

.participant-status {
  width: 8px;
  height: 8px;
  border-radius: 50%;
}

.participant-status.online {
  background-color: #4CAF50;
}

.participant-status.offline {
  background-color: #808080;
}

.participant-name {
  font-size: 14px;
  color: white;
}

.participant-name.other {
  color: #4CAF50;
}

.participant-name.user {
  color: #FFD700;
}

.sender-name {
  font-size: 0.8em;
  margin-bottom: 4px;
  font-weight: 500;
}

.message.user .sender-name {
  color: #FFD700;
  text-align: right;
}

.message.other .sender-name {
  color: #4CAF50;
}

.messages-section {
  flex: 1;
  display: flex;
  flex-direction: column;
  height: 100%;
}

.chat-messages {
  flex: 1;
  overflow-y: auto;
  background-color: rgba(0, 0, 0, 0.2);
  border-radius: 8px;
  padding: 12px;
  margin-bottom: 12px;
}

.message {
  font-size: 12px;
  max-width: 100%;
  padding: 8px 12px;
  border-radius: 12px;
  margin: 4px 0;
}

.message.user {
  justify-self: right;
  align-self: flex-end;
  background-color: #4CAF50;
  color: white;
}

.message.other {
  justify-self: left;
  align-self: flex-start;
  background-color: rgba(255, 255, 255, 0.1);
  color: white;
}

.message-content {
  margin-bottom: 4px;
}

.message-timestamp {
  font-size: 0.7em;
  opacity: 0.7;
  text-align: right;
}

.chat-input {
  display: flex;
  gap: 8px;
  padding: 12px;
  background-color: rgba(0, 0, 0, 0.2);
  border-radius: 8px;
}

.chat-input input {
  flex: 1;
  padding: 8px 12px;
  border: none;
  border-radius: 4px;
  background-color: rgba(255, 255, 255, 0.1);
  color: white;
}

.chat-input input::placeholder {
  color: rgba(255, 255, 255, 0.5);
}

.chat-input button {
  padding: 8px 16px;
  border: none;
  border-radius: 4px;
  background-color: #4CAF50;
  color: white;
  cursor: pointer;
  transition: background-color 0.2s;
}

.chat-input button:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.chat-input button:hover:not(:disabled) {
  background-color: #45a049;
}

.typing-indicator {
  display: flex;
  gap: 4px;
  padding: 8px 12px;
  background-color: rgba(255, 255, 255, 0.1);
  border-radius: 12px;
  width: fit-content;
}

.typing-indicator span {
  width: 8px;
  height: 8px;
  background-color: rgba(255, 255, 255, 0.5);
  border-radius: 50%;
  display: inline-block;
  animation: typing 1s infinite ease-in-out;
}

.typing-indicator span:nth-child(2) {
  animation-delay: 0.2s;
}

.typing-indicator span:nth-child(3) {
  animation-delay: 0.4s;
}

@keyframes typing {
  0%, 100% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-4px);
  }
}

.chat-messages::-webkit-scrollbar {
  width: 6px;
}

.chat-messages::-webkit-scrollbar-track {
  background: rgba(255, 255, 255, 0.05);
  border-radius: 3px;
}

.chat-messages::-webkit-scrollbar-thumb {
  background: rgba(255, 255, 255, 0.2);
  border-radius: 3px;
}

.chat-messages::-webkit-scrollbar-thumb:hover {
  background: rgba(255, 255, 255, 0.3);
}
</style>