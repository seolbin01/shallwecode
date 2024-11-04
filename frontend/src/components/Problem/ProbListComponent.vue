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
    <input type="text" placeholder="문제 검색">
  </div>

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
    <tr>
      <td>1</td>
      <td>DFS, BFS</td>
      <td class="level level-1">Lv. 1</td>
      <td>수정</td>
      <td>삭제</td>
    </tr>
    <tr>
      <td>2</td>
      <td>DFS, BFS</td>
      <td class="level level-2">Lv. 2</td>
      <td>수정</td>
      <td>삭제</td>
    </tr>
    <tr>
      <td>3</td>
      <td>DFS, BFS</td>
      <td class="level level-2">Lv. 2</td>
      <td>수정</td>
      <td>삭제</td>
    </tr>
    <tr>
      <td>4</td>
      <td>DFS, BFS</td>
      <td class="level level-3">Lv. 3</td>
      <td>수정</td>
      <td>삭제</td>
    </tr>
    <tr>
      <td>5</td>
      <td>DFS, BFS</td>
      <td class="level level-3">Lv. 3</td>
      <td>수정</td>
      <td>삭제</td>
    </tr>
    <tr>
      <td>6</td>
      <td>DFS, BFS</td>
      <td class="level level-4">Lv. 4</td>
      <td>수정</td>
      <td>삭제</td>
    </tr>
    <tr>
      <td>7</td>
      <td>DFS, BFS</td>
      <td class="level level-2">Lv. 2</td>
      <td>수정</td>
      <td>삭제</td>
    </tr>
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

.level {
  font-weight: bold;
}

.level-1 { color: #00cc44; }
.level-2 { color: #0099ff; }
.level-3 { color: #cc33ff; }
.level-4 { color: #ff3333; }

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