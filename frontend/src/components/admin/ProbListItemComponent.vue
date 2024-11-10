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
.level {
  display: inline-block;
  padding: 6px 12px;
  border-radius: 12px;
  font-weight: 500;
  font-size: 13px;
}

.level-1 {
  background-color: #e3f2fd;
  color: #1565c0;
}

.level-2 {
  background-color: #e8f5e9;
  color: #2e7d32;
}

.level-3 {
  background-color: #fff3e0;
  color: #e65100;
}

.level-4 {
  background-color: #ffebee;
  color: #c62828;
}

button {
  padding: 6px 16px;
  border-radius: 6px;
  font-size: 13px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s;
  border: none;
}

tr td:nth-child(4) button {
  background-color: #f0f7ff;
  color: #1a73e8;
  border: 1px solid #1a73e8;
}

tr td:nth-child(4) button:hover {
  background-color: #1a73e8;
  color: white;
}

tr td:nth-child(5) button {
  background-color: #fff1f0;
  color: #ff4d4f;
  border: 1px solid #ff4d4f;
}

tr td:nth-child(5) button:hover {
  background-color: #ff4d4f;
  color: white;
}

td {
  padding: 16px;
  border-bottom: 1px solid #eaeaea;
  font-size: 14px;
  color: #333;
  text-align: center;
}

td:nth-child(2) {
  text-align: left;
  font-weight: 500;
  color: #1a1b3a;
}

tr:hover {
  background-color: #f8f9ff;
}

@media (max-width: 768px) {
  td {
    padding: 12px 8px;
    font-size: 13px;
  }

  button {
    padding: 4px 12px;
    font-size: 12px;
  }

  .level {
    padding: 4px 8px;
    font-size: 12px;
  }
}
</style>