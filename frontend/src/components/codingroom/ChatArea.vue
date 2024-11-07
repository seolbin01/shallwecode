<script setup>
import {ref} from "vue";

const participants = ref([
  {id: 1, name: 'AI 어시스턴트', status: 'online', type: 'other'},
  {id: 2, name: '사용자', status: 'online', type: 'user'}
]);

const messages = ref([]);
const newMessage = ref('');
const isTyping = ref(false);

// 보낸 사람 이름 찾기 함수
const getSenderName = (senderType) => {
  return participants.value.find(p => p.type === senderType)?.name || '';
};

// 채팅 메시지 전송 함수
const sendMessage = () => {
  if (newMessage.value.trim()) {
    messages.value.push({
      id: Date.now(),
      text: newMessage.value,
      sender: 'user',
      senderName: getSenderName('user'),
      timestamp: new Date().toLocaleTimeString()
    });
    newMessage.value = '';

    // 어시스턴트 응답 시뮬레이션
    isTyping.value = true;
    setTimeout(() => {
      messages.value.push({
        id: Date.now(),
        text: "코드에 대해 도움이 필요하시다면 말씀해 주세요!",
        sender: 'other',
        senderName: getSenderName('other'),
        timestamp: new Date().toLocaleTimeString()
      });
      isTyping.value = false;
    }, 1000);
  }
};
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
          <div v-for="participant in participants"
               :key="participant.id"
               class="participant-item">
            <div class="participant-status"
                 :class="participant.status"></div>
            <div class="participant-name"
                 :class="participant.type">
              {{ participant.name }}
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