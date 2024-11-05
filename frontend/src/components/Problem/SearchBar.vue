<script setup>

</script>

<template>
  <!-- 필터바 -->
  <div class="filter-bar">
    <select>
      <option>난이도</option>
      <option>Lv. 1</option>
      <option>Lv. 2</option>
      <option>Lv. 3</option>
      <option>Lv. 4</option>
    </select>
    <input type="text" placeholder="검색할 문제 입력">
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