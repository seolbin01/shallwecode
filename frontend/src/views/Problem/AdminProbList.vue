<script setup>
import SideComponent from "@/components/Problem/SideComponent.vue";
import ProbListComponent from "@/components/Problem/ProbListComponent.vue";
import {reactive, onMounted, onUnmounted} from "vue"
import axios from "axios";

/* 필요한 객체 생성부 */
const adminProblemList = reactive({
  problemList : [],
  currentPage: '',
  totalPages: '',
  totalItems: ''
});

/* 필요한 문제 목록 통신 */
const fetchProblemList = async () => {
  try {
    const response = await axios.get(`http://localhost:8080/api/v1/problem/adminList`, {
      headers : {
        Authorization : 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjaGF0QGdtYWlsLmNvbSIsImF1dGgiOlsiUk9MRV9VU0VSIl0sImV4cCI6MTczMDI1MTc5N30.aM3knHn_RuZanzSNI9Hd-dsXIEQaaJUTEWD0fEg-9WCUfb7VahwTzza5e4tQ4EnrtzFPH_TnJsKtgKXqXFAWDQ'
      },
      withCredentials : true
    });

    /* 데이터 매핑 */
    adminProblemList.problemList = response.data.problemList;
    adminProblemList.currentPage = response.data.currentPage;
    adminProblemList.totalPages = response.data.totalPages;
    adminProblemList.totalItems = response.data.totalItems;
  } catch (error) {
    console.error('문제 목록을 불러오는데 문제가 발생했습니다.');
  }
}
// DOM 로드 전 데이터 셋팅
onMounted(async () => {
  await fetchProblemList();
})
</script>

<template>
  <div class="container">
    <!-- 사이드바 -->
    <SideComponent />
    <!-- 메인 컨텐츠 -->
      <div class="content">
        <ProbListComponent :problemList="adminProblemList.problemList" />
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

/* 메인 컨테이너 */
.container {
  display: flex;
  padding: 20px;
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