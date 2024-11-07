<script setup>
import {ref} from "vue";

const username = ref('반짝이는 성운');
const email = ref('testuser01@naver.com');
const stats = ref({ challenge: 3, unSolved: 154, solved: 3 });
const isEditing = ref(false);
const tempUsername = ref('');

const handleUpdateClick = () => {
  if (isEditing.value) {
    username.value = tempUsername.value;
    isEditing.value = false;
  } else {
    tempUsername.value = username.value;
    isEditing.value = true;
  }
}
</script>

<template>
  <div class="profile-container">
    <div class="info-section">

      <h2 class="section-title">회원 정보</h2>

      <div class="basic-info">
        <div class="profile-image">
          <img src="@/assets/icons/profile.svg" alt="프로필 사진"/>
        </div>

        <div class="user-details">
          <div class="name-info">
            <div class="username" v-if="!isEditing">
              {{ username }}
            </div>
            <input
                v-else
                v-model="tempUsername"
                class="username-input"
                type="text"
                @keyup.enter="handleUpdateClick"
            />
            <div class="update"
                 v-if="!isEditing"
                 @click="handleUpdateClick">
              수정
            </div>
            <div class="update"
                 v-else
                 @click="handleUpdateClick">
              저장
            </div>
          </div>
          <div class="email">
            {{ email }}
            <span class="naver-badge">N</span>
          </div>
        </div>
      </div>

      <div class="stats-container">
        <div class="stat-item">
          <span class="stat-label">도전한 문제</span>
          <span class="stat-value">{{ stats.challenge }}개</span>
        </div>
        <div class="stat-item">
          <span class="stat-label">미해결 문제</span>
          <span class="stat-value">{{ stats.unSolved }}개</span>
        </div>
        <div class="stat-item">
          <span class="stat-label">해결한 문제</span>
          <span class="stat-value">{{ stats.solved }}개</span>
        </div>
      </div>
    </div>

    <!-- 계정 탈퇴 섹션 -->
    <div class="withdrawal-section">
      <h2 class="section-title">계정 탈퇴</h2>
      <div class="withdrawal-content">
        <p>계정 탈퇴 시 프로필이 삭제됩니다.</p>
        <button class="withdrawal-btn">탈퇴하기</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.profile-container {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
}

.section-title {
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 20px;
  color: #333;
}

.info-section, .withdrawal-section {
  background-color: #fff;
  border-radius: 12px;
  padding: 24px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  margin-bottom: 20px;
}

.basic-info {
  display: flex;
  align-items: center;
  margin-bottom: 24px;
}

.profile-image {
  width: 80px;
  height: 80px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-left: 24px;
  margin-right: 24px;
  background-color: #B2DFDB;
  border-radius: 50%;
  overflow: hidden;
}

.profile-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  opacity: 1;
}

.user-details {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.name-info {
  display: flex;
  gap: 6px;
  align-items: center;
}

.username {
  font-size: 18px;
  font-weight: 600;
  color: #2196F3;
  margin-bottom: 4px;
}

.update {
  font-size: 12px;
  cursor: pointer;
}

.email {
  font-size: 16px;
  color: #666;
  display: flex;
  align-items: center;
  gap: 8px;
}

.naver-badge {
  background-color: #03C75A;
  color: white;
  padding: 2px 4px;
  border-radius: 2px;
  font-size: 12px;
  font-weight: bold;
}

.stats-container {
  display: flex;
  justify-content: space-between;
  border-top: 1px solid #eee;
  padding-top: 20px;
}

.stat-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  flex: 1;
}

.stat-label {
  font-size: 14px;
  color: #666;
  margin-bottom: 8px;
}

.stat-value {
  font-size: 16px;
  font-weight: 500;
  color: #333;
}

.withdrawal-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.withdrawal-content p {
  color: #666;
  font-size: 14px;
}

.withdrawal-btn {
  background-color: white;
  color: #FF5252;
  border: 1px solid #FF5252;
  padding: 8px 16px;
  border-radius: 4px;
  font-size: 14px;
  cursor: pointer;
  transition: all 0.2s;
}

.withdrawal-btn:hover {
  background-color: #FF5252;
  color: white;
}

.username-input {
  font-size: 18px;
  font-weight: 500;
  color: #404040;
  background-color: #F4F4F4;
  border: 1px solid #D9D9D9;
  border-radius: 4px;
  padding: 4px 8px;
  outline: none;
  width: 200px;
}

.username-input:focus {
  border-color: #D9D9D9;
  box-shadow: 0 0 0 2px rgba(33, 150, 243, 0.1);
}

.update {
  font-size: 12px;
  cursor: pointer;
  color: #404040;
  padding: 4px 8px;
  border-radius: 4px;
  transition: background-color 0.2s;
}

.update:hover {
  background-color: rgba(33, 150, 243, 0.1);
}
</style>