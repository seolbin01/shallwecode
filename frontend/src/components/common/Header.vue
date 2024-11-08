<script setup>
import {inject, onMounted, onUnmounted, ref} from 'vue'
import axios from "axios";
import {useAuthStore} from "@/stores/auth.js";

const showNotis = ref(false);
const notis = ref([]);
const refreshNotiList = inject('refreshNotiList');
const store = useAuthStore();

const fetchMyNotReadNotiList = async () => {
  try {
    const response = await axios.get('http://localhost:8080/api/v1/noti');
    notis.value = response.data;
  } catch (error) {
    console.error('알림 목록을 불러오는 중 에러가 발생했습니다.', error.response ? error.response.data : error.message);
  }
};

const handleNotiClick = async (noti) => {
  try {
    await axios.put(`http://localhost:8080/api/v1/noti`, {
      notiId: noti.notiId
    });
    await fetchMyNotReadNotiList();
    refreshNotiList.value();
  } catch (error) {
    console.error('알림 읽음 상태 변경 중 에러가 발생했습니다.', error.response ? error.response.data : error.message);
  }
};

const toggleNotis = async () => {
  showNotis.value = !showNotis.value
  if (showNotis.value) {
    await fetchMyNotReadNotiList();
  }
}

// 알림창 외부 클릭시 닫기
const closeNotis = (event) => {
  const dropdown = document.querySelector('.notifications-dropdown')
  const notiBtn = document.querySelector('.notification-btn')

  if (dropdown && !dropdown.contains(event.target) && !notiBtn.contains(event.target)) {
    showNotis.value = false
  }
}

const formatDate = (dateString) => {
  const date = new Date(dateString);
  return date.toLocaleString('ko-KR', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit',
    second: '2-digit'
  });
};

const handleLogoutClick = () => {
  store.logout();
}

onMounted(() => {
  fetchMyNotReadNotiList();
  document.addEventListener('click', closeNotis)
})

onUnmounted(() => {
  document.removeEventListener('click', closeNotis)
})
</script>

<template>
  <header class="header">
    <router-link to="/" class="logo">ShallWeCode</router-link>
    <div class="menu">
      <router-link to="/user-list" class="menu-item">회원 목록</router-link>
      <button @click="handleLogoutClick">로그아웃</button>
      <div class="notification-container">
        <button @click="toggleNotis" class="menu-item notification-btn">
          알림
        </button>
        <div v-if="showNotis" class="notifications-dropdown">
          <div v-if="notis.length" class="notifications-list">
            <div v-for="noti in notis"
                 class="notification-item"
                 @click="handleNotiClick(noti)">
              <span class="notification-message">{{ noti.content }}</span>
              <span class="notification-date">{{ formatDate(noti.createdAt) }}</span>
            </div>
          </div>
          <div v-else class="no-notifications">
            새로운 알림이 없습니다.
          </div>
        </div>
      </div>
    </div>
  </header>
</template>

<style scoped>
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0.75rem 2rem;
  background-color: #1a1b2e;
  color: white;
  height: 30px;
}

.logo {
  font-size: 1.25rem;
  font-weight: bold;
  color: #8be9fd;
  text-decoration: none;
}

.menu {
  display: flex;
  gap: 1.5rem;
}

.menu-item {
  color: white;
  text-decoration: none;
  font-size: 0.875rem;
  background: none;
  border: none;
  padding: 0;
  cursor: pointer;
}

.menu-item:hover {
  color: #8be9fd;
}

.notification-container {
  position: relative;
}

.notifications-dropdown {
  position: absolute;
  top: calc(100% + 10px);
  right: 0;
  background-color: white;
  border: 1px solid #eee;
  border-radius: 4px;
  width: 300px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  z-index: 1000;
}

.notifications-list {
  max-height: 300px;
  overflow-y: auto;
}

.notification-item {
  padding: 12px;
  border-bottom: 1px solid #eee;
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.notification-item:last-child {
  border-bottom: none;
}

.notification-message {
  color: #333;
  font-size: 0.875rem;
}

.notification-date {
  color: #666;
  font-size: 0.75rem;
}

.no-notifications {
  padding: 12px;
  text-align: center;
  color: #666;
  font-size: 0.875rem;
}


.notification-item {
  cursor: pointer;
}

.notification-item:hover {
  background-color: #f5f5f5;
}

</style>