<script setup>
const props = defineProps({
  problem: {
    type: Object,
    required: true
  }
});


const emit = defineEmits(['edit-problem', 'delete-problem']);

const handleEdit = () => {
  const confirmed = confirm('이 문제를 수정하시겠습니까?');
  if (confirmed){
    emit('edit-problem', props.problem.problemId);
  }
};

const handleDelete = () => {
  const confirmed = confirm('이 문제를 삭제하시겠습니까?');
  if (confirmed) {
    emit('delete-problem', props.problem.problemId);
  }
};

</script>

<template>
  <tr>
    <td>{{ props.problem.problemId }}</td>
    <td>{{ props.problem.title }}</td>
    <td :class="`level level-${props.problem.problemLevel}`">Lv. {{ props.problem.problemLevel }}</td>
    <td><button @click="handleEdit">수정</button></td>
    <td><button @click="handleDelete">삭제</button></td>
  </tr>
</template>

<style scoped>

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