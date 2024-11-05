<script setup>
import SearchBar from "@/components/Problem/SearchBar.vue";
import ProbListItemComponent from "@/components/Problem/ProbListItemComponent.vue";

const props = defineProps({
  problemList: {
    type: Array,
    required: true
  },
  currentPage: Number,
  totalPages: Number,
  totalItems: Number
});
</script>

<template>
  <!-- 필터바 -->
  <SearchBar />

  <!-- 문제 테이블 -->
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

  <!-- 페이지네이션 -->
  <div class="pagination">
    <button>&lt;</button>
    <button>1</button>
    <button>2</button>
    <button>3</button>
    <button>&gt;</button>
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

.pagination {
  display: flex;
  justify-content: center;
  margin-top: 20px;
  gap: 10px;
}

.pagination button {
  background: none;
  border: none;
  cursor: pointer;
  font-size: 1rem;
}
</style>