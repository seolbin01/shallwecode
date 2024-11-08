<script setup>
import {ref} from "vue"
import UserListComponent from "@/components/admin/UserListComponent.vue";
import ProbListComponent from "@/components/admin/ProbListComponent.vue";

const selectedMenu = ref('user');

const selectMenu = (menu) => {
  selectedMenu.value = menu;
};
</script>

<template>
  <div class="container">
    <div class="menu-container">
      <div class="menu-button" :class="{ 'active': selectedMenu === 'user' }" @click="selectMenu('user')">
        <a>회원 관리</a>
      </div>
      <div class="menu-button" :class="{ 'active': selectedMenu === 'problem' }" @click="selectMenu('problem')">
        <a>문제 관리</a>
      </div>
    </div>
    <div class="content">
      <UserListComponent v-if="selectedMenu === 'user'"/>
      <ProbListComponent v-else-if="selectedMenu === 'problem'"/>
    </div>
  </div>
</template>

<style scoped>
body {
  font-family: Arial, sans-serif;
  margin: 0;
  padding: 0;
  background-color: #f5f5f5;
}

.container {
  display: flex;
  padding: 20px;
  background-color: var(--background-color);
}

.menu-container {
  flex: 0 0 auto;
  min-width: 180px;
  max-width: 220px;
  width: 20%;
  height: fit-content;
  background: #ffffff;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  padding: 8px;
  margin: 20px;
  border: 1px solid var(--gray-line);
}

.content {
  flex-grow: 1;
  background-color: #ffffff;
  padding: 20px;
  border-radius: 10px;
  border: 1px solid #ddd;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.menu-button {
  font-size: clamp(14px, 2vw, 16px);
  font-weight: 600;
  color: var(--black);
  cursor: pointer;
  padding: 12px 16px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.menu-button:hover {
  background-color: var(--hover);
}

.menu-button:active {
  background-color: var(--active);
}

.menu-button:not(:last-child) {
  border-bottom: 1px solid var(--gray-line);
}
</style>