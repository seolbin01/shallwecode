<script setup>
const props = defineProps({
  problem: {
    type: Object,
    required: true
  }
});
</script>

<template>
  <tr>
    <td>{{ problem.problemId }}</td>
    <td>{{ problem.title }}</td>
    <td :class="`level level-${problem.problemLevel}`">Lv. {{ problem.problemLevel }}</td>
    <td>수정</td>
    <td>삭제</td>
  </tr>
</template>

<style scoped>
.level {
  font-weight: bold;
}

.level-1 { color: #00cc44; }
.level-2 { color: #0099ff; }
.level-3 { color: #cc33ff; }
.level-4 { color: #ff3333; }

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