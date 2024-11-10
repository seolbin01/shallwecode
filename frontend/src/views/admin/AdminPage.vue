<script setup>
import { ref } from "vue"
import UserListComponent from "@/components/admin/UserListComponent.vue";
import ProbListComponent from "@/components/admin/ProbListComponent.vue";

const selectedMenu = ref('user');

const menuItems = {
  user: { label: '회원 관리', icon: '👥' },
  problem: { label: '문제 관리', icon: '📝' }
};

const selectMenu = (menu) => {
  selectedMenu.value = menu;
};
</script>

<template>
  <div class="admin-container">
    <div class="menu-container">
      <h2 class="menu-title">관리자 메뉴</h2>
      <div class="menu-divider"></div>
      <div
          v-for="(item, key) in menuItems"
          :key="key"
          class="menu-button"
          :class="{ 'active': selectedMenu === key }"
          @click="selectMenu(key)"
      >
        <div class="menu-icon">{{ item.icon }}</div>
        <span class="menu-text">{{ item.label }}</span>
        <div class="menu-indicator"></div>
      </div>
    </div>
    <div class="content">
      <UserListComponent v-if="selectedMenu === 'user'"/>
      <ProbListComponent v-else-if="selectedMenu === 'problem'"/>
    </div>
  </div>
</template>

<style scoped>
.admin-container {
  display: flex;
  gap: 24px;
  padding: 24px;
  min-width: 320px;
  background-color: var(--background-color);
  min-height: calc(100vh - 48px);
}

.menu-container {
  flex: 0 0 auto;
  min-width: 240px;
  max-width: 280px;
  height: fit-content;
  background: #ffffff;
  border-radius: 16px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
  padding: 20px 12px;
  border: 1px solid rgba(0, 0, 0, 0.06);
  transition: all 0.3s ease;
}

.menu-title {
  font-size: 18px;
  font-weight: 600;
  color: #1a1b3a;
  padding: 0 16px;
  margin: 0 0 16px 0;
}

.menu-divider {
  height: 1px;
  background: #eaeaea;
  margin: 8px 0;
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

.menu-text {
  flex: 1;
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

.content {
  flex: 1;
  min-width: 0;
  background: #ffffff;
  padding: 32px;
  border-radius: 16px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
  border: 1px solid rgba(0, 0, 0, 0.06);
}

@media (max-width: 768px) {
  .admin-container {
    flex-direction: column;
  }

  .menu-container {
    min-width: 100%;
    max-width: 100%;
  }

  .content {
    width: 100%;
  }
}
</style>