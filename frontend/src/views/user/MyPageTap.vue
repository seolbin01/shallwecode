<script setup>
import {ref} from "vue";
import MyInfo from "@/components/user/MyInfo.vue";

const selectedMenu = ref('friends');

const selectMenu = (menu) => {
  selectedMenu.value = menu;
};
</script>

<template>
  <div class="padding">
    <div class="menu-container">
      <div class="menu-button" :class="{ 'active': selectedMenu === 'myinfo' }" @click="selectMenu('myinfo')">
        <a>회원 정보</a>
      </div>
      <div class="menu-button" :class="{ 'active': selectedMenu === 'friend' }" @click="selectMenu('friend')">
        <a>친구 관리</a>
      </div>
      <div class="menu-button" :class="{ 'active': selectedMenu === 'noti' }" @click="selectMenu('noti')">
        <a>알림 목록</a>
      </div>
      <div class="menu-button" :class="{ 'active': selectedMenu === 'problem' }" @click="selectMenu('problem')">
        <a>풀이 문제 목록</a>
      </div>
      <div class="menu-button" :class="{ 'active': selectedMenu === 'codingRoom' }" @click="selectMenu('codingRoom')">
        <a>참여중인 코딩방 목록</a>
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
}

.menu-container {
  width: 220px;
  background: #ffffff;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  padding: 8px;
  margin: 30px;
  border: 1px solid var(--gray-line);
}

.menu-button {
  font-size: 16px;
  font-weight: 600;
  color: var(--black);
  cursor: pointer;
  padding: 12px 16px;
}

.menu-content {
  width: 1035px;
  background-color: var(--background-color);
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