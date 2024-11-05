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
    <button @click="problemSearch">검색</button>
  </div>
</template>

<style scoped>
.filter-bar {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
}

.filter-bar select, .filter-bar input {
  padding: 5px;
  font-size: 1rem;
  border: 1px solid #ddd;
  border-radius: 5px;
}

.filter-bar input {
  width: 200px;
}
</style>