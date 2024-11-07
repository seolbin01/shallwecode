<script setup>
import SearchBar from "@/components/admin/SearchBar.vue";
import ProbListItemComponent from "@/components/admin/ProbListItemComponent.vue";
import PageBar from "@/components/admin/PageBar.vue";
import {onMounted, ref} from "vue";
import axios from "axios";

const problemList = ref([]);
const currentPage = ref(0);
const totalPages = ref(0);
const totalItems = ref(0);
const keyword = ref('');
const option = ref('');

const token = ref('');

const fetchProblemList = async (page = 1) => {
  try {
    const response = await axios.get(`http://localhost:8080/api/v1/problem/adminList`, {
      headers: {
        Authorization: `Bearer ${token.value}`
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
    <ProbListItemComponent v-for="problem in problemList" :key="problem.problemId" :problem="problem"/>
    </tbody>
  </table>
  <PageBar :currentPage="currentPage"
           :totalPages="totalPages"
           :totalItems="totalItems"
          @page-change="problemSearch"/>
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
</style>