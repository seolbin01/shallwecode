<script setup>
import { reactive } from 'vue';

/* 검색 객체 생성 */
const search = reactive({
  keyword: '',
  option: ''
});

/* 부모 이벤트 전달 객체 */
const emit = defineEmits(['problemSearch']);

/* 부모 컴포넌트로 이벤트 전달 */
const emitSearch = () => {
  emit('problemSearch', {
    option: search.option || null,   // 선택 안되면 null 입력
    keyword: search.keyword || null
  });
};
</script>

<template>
  <!-- 필터바 -->
  <div class="filter-bar">
    <select v-model="search.option">
      <option value="">난이도</option>
      <option value="1">Lv. 1</option>
      <option value="2">Lv. 2</option>
      <option value="3">Lv. 3</option>
      <option value="4">Lv. 4</option>
    </select>
    <input type="text" v-model="search.keyword" placeholder="검색할 문제 입력">
    <button @click="emitSearch">검색</button>
  </div>
</template>

<style scoped>
.filter-bar {
  max-width: 600px;
  margin: 0 auto;
  display: flex;
  gap: 12px;
  margin-bottom: 24px;
}

select {
  padding: 12px 16px;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  font-size: 14px;
  transition: all 0.2s;
  min-width: 120px;
  background-color: white;
  color: #333;
  cursor: pointer;
  appearance: none;
  background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='12' height='12' viewBox='0 0 12 12'%3E%3Cpath fill='%23333' d='M6 8.825L1.175 4 2.238 2.938 6 6.7l3.763-3.762L10.825 4z'/%3E%3C/svg%3E");
  background-repeat: no-repeat;
  background-position: right 16px center;
  padding-right: 40px;
}

select:focus {
  outline: none;
  border-color: #1a73e8;
  box-shadow: 0 0 0 3px rgba(26, 115, 232, 0.1);
}

input {
  flex: 1;
  padding: 12px 16px;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  font-size: 14px;
  transition: all 0.2s;
}

input:focus {
  outline: none;
  border-color: #1a73e8;
  box-shadow: 0 0 0 3px rgba(26, 115, 232, 0.1);
}

button {
  padding: 12px 24px;
  background-color: #1a73e8;
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s;
  white-space: nowrap;
}

button:hover {
  background-color: #1557b0;
  transform: translateY(-1px);
}

button:active {
  transform: translateY(0);
}

@media (max-width: 768px) {
  .filter-bar {
    flex-direction: column;
    gap: 8px;
  }

  select, input, button {
    width: 100%;
  }
}
</style>