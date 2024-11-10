<script setup>
import SearchBar from "@/components/admin/SearchBar.vue";
import ProbListItemComponent from "@/components/admin/ProbListItemComponent.vue";
import PageBar from "@/components/admin/PageBar.vue";
import {onMounted, ref} from "vue";
import axios from "axios";
import router from "@/router/index.js";
import {useAuthStore} from "@/stores/auth.js";

const problemList = ref([]);
const currentPage = ref(0);
const totalPages = ref(0);
const totalItems = ref(0);
const keyword = ref('');
const option = ref('');

const authStore = useAuthStore();

const fetchProblemList = async (page = 1) => {
  try {
    const response = await axios.get(`http://localhost:8080/api/v1/problem/admin`, {
      headers: {
        Authorization: `Bearer ${authStore.accessToken}`
      },
      params: {
        page,
        keyword: keyword.value,
        option: option.value
      },
      withCredentials: true
    });

    problemList.value = response.data.problemList;
    currentPage.value = response.data.currentPage;
    totalPages.value = response.data.totalPages;
    totalItems.value = response.data.totalItems;
  } catch (error) {
    console.error('문제 목록을 불러오는데 문제가 발생했습니다.');
  }
}

const problemSearch = (searchParams) => {
  keyword.value = searchParams.keyword;
  option.value = searchParams.option;
  fetchProblemList(1);
}

function goToProblemSave(){
  router.push('/admin/problem')
}

// 문제 수정 처리 함수


const handleEditProblem = (problemId) => {

  console.log('수정할 문제 ID:', problemId);
  // 문제 수정 페이지로 이동
  router.push(`/admin/problem/${problemId}`);
};


// 문제 삭제 처리 함수
const handleDeleteProblem = async (problemId) => {

  try {
    await axios.delete(`http://localhost:8080/api/v1/problem/${problemId}`, {
      headers: {
        Authorization: `Bearer ${authStore.accessToken}`,
      },
    });
    console.log('문제가 삭제되었습니다:', problemId);
    await fetchProblemList(currentPage.value); // 페이지를 새로고침하여 목록 업데이트
  } catch (error) {
    console.error('문제 삭제 중 오류가 발생했습니다.', error);
    alert('문제 삭제에 실패했습니다.');
  }

};


onMounted(async () => {
  await fetchProblemList();
})

</script>

<template>
  <SearchBar @problemSearch="problemSearch" />

  <table class="table">
    <thead>
    <tr>
      <th>번호</th>
      <th>제목</th>
      <th>난이도</th>
      <th>수정</th>
      <th>삭제</th>
    </tr>
    </thead>
    <tbody>
    <ProbListItemComponent v-for="problem in problemList" :key="problem.problemId" :problem="problem"
                           @edit-problem="handleEditProblem"
                           @delete-problem="handleDeleteProblem"/>
    </tbody>
  </table>
  <PageBar :currentPage="currentPage"
           :totalPages="totalPages"
           :totalItems="totalItems"
           @page-change="problemSearch"/>

  <!-- 문제 등록하기 버튼 -->
  <div class="line">

    <button @click="goToProblemSave" class="register-button">문제 등록하기</button>
  </div>

</template>

<style scoped>
.table {
  width: 100%;
  border-collapse: collapse;
}

.table th, .table td {
  border: 1px solid #ddd;
  padding: 10px;
  text-align: center;
}

.table th {
  background-color: #f0f0f0;
  font-weight: bold;
}

.line {
  display: flex;
  justify-content: flex-end;

}

</style>