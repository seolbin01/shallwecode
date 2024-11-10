<script setup>
import { ref } from "vue";
import MyInfo from "@/components/user/MyInfo.vue";
import MyFriendManagement from "@/components/user/MyFriendManagement.vue";
import MyNotiList from "@/components/user/MyNotiList.vue";
import MyProblemList from "@/components/user/MyProblemList.vue";
import MyCodingRoomList from "@/components/user/MyCodingRoomList.vue";

const selectedMenu = ref('myinfo');

const menuItems = {
  myinfo: { label: '회원 정보', icon: '👤' },
  friend: { label: '친구 관리', icon: '👥' },
  noti: { label: '알림 목록', icon: '🔔' },
  problem: { label: '풀이 문제 목록', icon: '📝' },
  codingRoom: { label: '참여중인 코딩방 목록', icon: '💻' }
};

const selectMenu = (menu) => {
  selectedMenu.value = menu;
};
</script>

<template>
  <div class="padding">
    <div class="menu-container">
      <div
          v-for="(item, key) in menuItems"
          :key="key"
          class="menu-button"
          :class="{ 'active': selectedMenu === key }"
          @click="selectMenu(key)"
      >
        <div class="menu-icon">{{ item.icon }}</div>
        <a>{{ item.label }}</a>
        <div class="menu-indicator"></div>
      </div>
    </div>

    <div class="menu-content">
      <MyInfo v-if="selectedMenu === 'myinfo'" />
      <MyFriendManagement v-else-if="selectedMenu === 'friend'" />
      <MyNotiList v-else-if="selectedMenu === 'noti'" />
      <MyProblemList v-else-if="selectedMenu === 'problem'" />
      <MyCodingRoomList v-else-if="selectedMenu === 'codingRoom'" />
    </div>
  </div>
</template>

<style scoped>
.padding {
  display: flex;
  gap: 24px;
  padding: 24px;
  min-width: 320px;
  background-color: var(--background-color);
}

.menu-container {
  flex: 0 0 auto;
  min-width: 200px;
  max-width: 240px;
  width: 20%;
  height: fit-content;
  background: #ffffff;
  border-radius: 16px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
  padding: 12px;
  border: 1px solid rgba(0, 0, 0, 0.06);
  transition: all 0.3s ease;
}

.menu-content {
  flex: 1;
  min-width: 300px;
  max-width: 1035px;
  background-color: #ffffff;
  padding: 24px;
  border-radius: 16px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
}

.menu-button {
  position: relative;
  display: flex;
  align-items: center;
  font-size: 15px;
  font-weight: 500;
  color: #666;
  cursor: pointer;
  padding: 14px 16px;
  margin: 4px 0;
  border-radius: 12px;
  transition: all 0.2s ease;
}

.menu-icon {
  margin-right: 12px;
  font-size: 18px;
  opacity: 0.8;
}

.menu-button:hover {
  background-color: rgba(0, 0, 0, 0.03);
  color: #333;
}

.menu-button.active {
  background-color: #f0f7ff;
  color: #1a73e8;
  font-weight: 600;
}

.menu-button.active .menu-icon {
  opacity: 1;
}

.menu-button .menu-indicator {
  position: absolute;
  left: 0;
  top: 50%;
  transform: translateY(-50%);
  width: 3px;
  height: 0;
  background-color: #1a73e8;
  border-radius: 0 4px 4px 0;
  transition: height 0.2s ease;
}

.menu-button.active .menu-indicator {
  height: 24px;
}

.menu-button:not(:last-child) {
  margin-bottom: 4px;
}

@media (max-width: 768px) {
  .padding {
    flex-direction: column;
  }

  .menu-container {
    min-width: 100%;
    max-width: 100%;
  }
}
</style>