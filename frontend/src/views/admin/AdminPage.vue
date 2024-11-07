<script setup>
import ProbListComponent from "@/components/admin/ProbListComponent.vue";
import {reactive, onMounted, ref} from "vue"
import axios from "axios";

const selectedMenu = ref('myinfo');

const selectMenu = (menu) => {
  selectedMenu.value = menu;
};

const adminProblemList = reactive({
  problemList: [],
  currentPage: 0,
  totalPages: 0,
  totalItems: 0,
  keyword: '',
  option: ''
});

/* 필요한 문제 목록 통신 */
const fetchProblemList = async (page = 1) => {
  try {
    const response = await axios.get(`http://localhost:8080/api/v1/problem/adminList`, {
      headers: {
        Authorization: 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjaGF0QGdtYWlsLmNvbSIsImF1dGgiOlsiUk9MRV9VU0VSIl0sImV4cCI6MTczMDI1MTc5N30.aM3knHn_RuZanzSNI9Hd-dsXIEQaaJUTEWD0fEg-9WCUfb7VahwTzza5e4tQ4EnrtzFPH_TnJsKtgKXqXFAWDQ'
      },
      params: {
        page,
        keyword: adminProblemList.keyword,
        option: adminProblemList.option
      },
      withCredentials: true
    });

    adminProblemList.problemList = response.data.problemList;
    adminProblemList.currentPage = response.data.currentPage;
    adminProblemList.totalPages = response.data.totalPages;
    adminProblemList.totalItems = response.data.totalItems;
  } catch (error) {
    console.error('문제 목록을 불러오는데 문제가 발생했습니다.');
  }
}

const problemSearch = (searchParams) => {
  adminProblemList.keyword = searchParams.keyword;
  adminProblemList.option = searchParams.option;
  fetchProblemList(1);
}

// DOM 로드 전 데이터 셋팅
onMounted(async () => {
  await fetchProblemList();
})
</script>

<template>
  <div class="container">
    <div class="menu-container">
      <div class="menu-button" :class="{ 'active': selectedMenu === 'myinfo' }" @click="selectMenu('myinfo')">
        <a>회원 관리</a>
      </div>
      <div class="menu-button" :class="{ 'active': selectedMenu === 'problem' }" @click="selectMenu('friend')">
        <a>문제 관리</a>
      </div>
    </div>
    <div class="content">
      <ProbListComponent :problemList="adminProblemList.problemList"
                         :currentPage="adminProblemList.currentPage"
                         :totalPages="adminProblemList.totalPages"
                         :totalItems="adminProblemList.totalItems"
                         @problemSearch="problemSearch"/>
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

.menu-content {
  flex: 1;
  min-width: 300px;
  max-width: 1035px;
  background-color: var(--background-color);
  padding: 20px;
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

/* 메인 컨텐츠 */
.content {
  flex-grow: 1;
  background-color: #ffffff;
  padding: 20px;
  border-radius: 10px;
  border: 1px solid #ddd;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}
</style>