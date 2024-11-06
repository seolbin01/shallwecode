<script setup>
import SearchBar from "@/components/Problem/SearchBar.vue";
import ProbListItemComponent from "@/components/Problem/ProbListItemComponent.vue";
import PageBar from "@/components/Problem/PageBar.vue";

const props = defineProps({
  problemList: {
    type: Array,
    required: true
  },
  currentPage: Number,
  totalPages: Number,
  totalItems: Number
});

const emit = defineEmits(['problemSearch']);

const problemSearch = (searchParam) => {
  emit('problemSearch', searchParam);
};

</script>

<template>
  <!-- 필터바 -->
  <SearchBar @problemSearch="problemSearch" />

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
    <ProbListItemComponent v-for="problem in props.problemList" :key="problem.problemId" :problem="problem"/>
    </tbody>
  </table>
  <!-- 페이징바 -->
  <PageBar :currentPage="props.currentPage"
           :totalPages="props.totalPages"
           :totalItems="props.totalItems"
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