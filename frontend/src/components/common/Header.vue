<script setup>
import {onMounted, onUnmounted, ref} from 'vue'

const showNotifications = ref(false)
const notifications = ref([
  {
    id: 1,
    message: 'user02님이 친구 요청을 보냈습니다.',
    date: '2024-10-30'
  },
  {
    id: 2,
    message: 'user02님이 친구 요청을 보냈습니다.',
    date: '2024-10-30'
  },
  {
    id: 3,
    message: 'user02님이 친구 요청을 보냈습니다.',
    date: '2024-10-30'
  },
  {
    id: 4,
    message: 'user02님이 친구 요청을 보냈습니다.',
    date: '2024-10-30'
  },
  {
    id: 5,
    message: 'user02님이 친구 요청을 보냈습니다.',
    date: '2024-10-30'
  },
  {
    id: 6,
    message: 'user02님이 친구 요청을 보냈습니다.',
    date: '2024-10-30'
  }
])

const toggleNotifications = () => {
  showNotifications.value = !showNotifications.value
}

// 알림창 외부 클릭시 닫기
const closeNotifications = (event) => {
  const dropdown = document.querySelector('.notifications-dropdown')
  const notificationBtn = document.querySelector('.notification-btn')

  if (dropdown && !dropdown.contains(event.target) && !notificationBtn.contains(event.target)) {
    showNotifications.value = false
  }
}

// 컴포넌트 마운트 시 이벤트 리스너 추가
onMounted(() => {
  document.addEventListener('click', closeNotifications)
})

// 컴포넌트 언마운트 시 이벤트 리스너 제거
onUnmounted(() => {
  document.removeEventListener('click', closeNotifications)
})
</script>

<template>
  <header class="header">
    <router-link to="/" class="logo">ShallWeCode</router-link>
    <div class="menu">
      <router-link to="/user-list" class="menu-item">회원 목록</router-link>
      <router-link to="/logout" class="menu-item">로그아웃</router-link>
      <div class="notification-container">
        <button @click="toggleNotifications" class="menu-item notification-btn">
          알림
        </button>
        <div v-if="showNotifications" class="notifications-dropdown">
          <div v-if="notifications.length" class="notifications-list">
            <div v-for="notification in notifications"
                 :key="notification.id"
                 class="notification-item">
              <span class="notification-message">{{ notification.message }}</span>
              <span class="notification-date">{{ notification.date }}</span>
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
</style>