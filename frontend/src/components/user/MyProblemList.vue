<script setup>
import {ref, computed, watch} from 'vue'

const ROWS_PER_PAGE = 7

const problems = ref([
  {id: 1, title: '어려운 문제', level: 1, status: '해결'},
  {id: 2, title: '진짜 어려운 문제', level: 2, status: '해결'},
  {id: 3, title: '매우 어려운 문제', level: 3, status: '해결'},
  {id: 4, title: '너무 어려운 문제', level: 3, status: '미해결'},
  {id: 5, title: '어렵지만 어려운 문제', level: 3, status: '미해결'},
  {id: 6, title: '어렵고 어려운 문제', level: 3, status: '해결'},
  {id: 7, title: '복잡한예제', level: 3, status: '해결'}
])

const trys = ref([
  {id: 1, language: 'JAVA', status: '해결', createdAt: '2024-10-30\n' + '16:09:20'},
  {id: 2, language: 'JAVA', status: '해결', createdAt: '2024-10-30\n' + '16:09:20'},
  {id: 3, language: 'PYTHON', status: '해결', createdAt: '2024-10-30\n' + '16:09:20'}
])

const currentPage = ref(1)
const searchQuery = ref('')

const filteredProblems = computed(() => {
  if (!searchQuery.value) return problems.value

  return problems.value.filter(problem =>
      problem.title.toLowerCase().includes(searchQuery.value.toLowerCase())
  )
})

const filteredTrys = computed(() => {
  if (!searchQuery.value) return trys.value

  return trys.value.filter(try$ =>
      trys.title.toLowerCase().includes(searchQuery.value.toLowerCase())
  )
})

const totalProblemPages = computed(() =>
    Math.ceil(filteredProblems.value.length / ROWS_PER_PAGE)
)

const totalTryPages = computed(() =>
    Math.ceil(filteredTrys.value.length / ROWS_PER_PAGE)
)

const displayedProblems = computed(() => {
  const startIdx = (currentPage.value - 1) * ROWS_PER_PAGE
  const endIdx = startIdx + ROWS_PER_PAGE
  return filteredProblems.value.slice(startIdx, endIdx)
})

const displayedTrys = computed(() => {
  const startIdx = (currentPage.value - 1) * ROWS_PER_PAGE
  const endIdx = startIdx + ROWS_PER_PAGE
  return filteredTrys.value.slice(startIdx, endIdx)
})

const emptyRowsProblemCount = computed(() =>
    ROWS_PER_PAGE - displayedProblems.value.length
)

const emptyRowsTryCount = computed(() =>
    ROWS_PER_PAGE - displayedTrys.value.length
)

const changePage = (page) => {
  if (page === 'prev' && currentPage.value > 1) {
    currentPage.value--
  } else if (page === 'next' && currentPage.value < totalPages.value) {
    currentPage.value++
  } else if (typeof page === 'number') {
    currentPage.value = page
  }
}

watch(searchQuery, () => {
  currentPage.value = 1
})
</script>
<template>
  <div class="container">
    <div class="problem-container">
      <h1 class="title">풀이 문제 목록</h1>

      <div class="search-area">
        <input
            type="text"
            class="search-input"
            placeholder="문제 검색"
            v-model="searchQuery"
        >
        <button class="filter-button">해결 ▼</button>
      </div>

      <table class="problem-table">
        <thead>
        <tr>
          <th>번호</th>
          <th>문제명</th>
          <th>난이도</th>
          <th>상태</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="problem in displayedProblems" :key="problem.id">
          <td>{{ problem.id }}</td>
          <td>{{ problem.title }}</td>
          <td><span class="level-badge">Lv. {{ problem.level }}</span></td>
          <td>
            <span :class="['status', problem.status === '해결' ? 'status-solved' : 'status-unsolved']">
              {{ problem.status }}
            </span>
          </td>
        </tr>
        <tr v-for="i in emptyRowsProblemCount" :key="`empty-${i}`" class="empty-row">
          <td>-</td>
          <td>-</td>
          <td>-</td>
          <td>-</td>
        </tr>
        </tbody>
      </table>

      <div class="pagination">
        <button
            @click="changePage('prev')"
            :disabled="currentPage === 1"
        >◀
        </button>
        <button
            v-for="page in totalProblemPages"
            :key="page"
            @click="changePage(page)"
            :class="{ active: currentPage === page }"
        >
          {{ page }}
        </button>
        <button
            @click="changePage('next')"
            :disabled="currentPage === totalProblemPages"
        >▶
        </button>
      </div>
    </div>
    <div class="try-container">
      <h1 class="title">풀이 시도</h1>

      <div class="try-area">
        <h1>왼쪽에서 선택된 문제의 풀이 시도가 보입니다.</h1>
        <button class="filter-button">해결 ▼</button>
      </div>

      <table class="problem-table">
        <thead>
        <tr>
          <th>번호</th>
          <th>언어</th>
          <th>상태</th>
          <th>제출 일자</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="try$ in displayedTrys" :key="try$.id">
          <td>{{ try$.id }}</td>
          <td>{{ try$.language }}</td>
          <td>
            <span :class="['status', try$.status === '해결' ? 'status-solved' : 'status-unsolved']">
              {{ try$.status }}
            </span>
          </td>
          <td>{{ try$.createdAt }}</td>
        </tr>
        <tr v-for="i in emptyRowsTryCount" :key="`empty-${i}`" class="empty-row">
          <td>-</td>
          <td>-</td>
          <td>-</td>
          <td>-</td>
        </tr>
        </tbody>
      </table>

      <div class="pagination">
        <button
            @click="changePage('prev')"
            :disabled="currentPage === 1"
        >◀
        </button>
        <button
            v-for="page in totalTryPages"
            :key="page"
            @click="changePage(page)"
            :class="{ active: currentPage === page }"
        >
          {{ page }}
        </button>
        <button
            @click="changePage('next')"
            :disabled="currentPage === totalTryPages"
        >▶
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.container {
  width: 1000px;
  background: white;
  border-radius: 16px;
  padding: 24px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', 'Apple SD Gothic Neo', 'Noto Sans KR', sans-serif;
  display: flex;
  justify-content: center;
  gap: 50px;
}

.title {
  text-align: center;
  font-size: 20px;
  font-weight: 600;
  margin-bottom: 24px;
}

.try-area {
  display: flex;
  justify-content: space-between;
  margin-bottom: 16px;
  gap: 12px;
  align-items: center;
}

.search-area {
  display: flex;
  justify-content: space-between;
  margin-bottom: 16px;
  gap: 12px;
}

.search-input {
  flex: 1;
  padding: 8px 16px;
  border: 1px solid #e1e1e1;
  border-radius: 8px;
  font-size: 14px;
}

.filter-button {
  padding: 8px 16px;
  border: 1px solid #e1e1e1;
  border-radius: 8px;
  background: white;
  font-size: 14px;
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 4px;
}

.problem-table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 24px;
}

.problem-table th {
  background: #1a1b3a;
  color: white;
  padding: 12px;
  text-align: left;
  font-weight: normal;
}

.problem-table td {
  padding: 12px;
  border-bottom: 1px solid #e1e1e1;
  font-size: 14px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.level-badge {
  background: #f0f0f0;
  padding: 4px 8px;
  border-radius: 4px;
  font-size: 12px;
}

.status {
  border-radius: 4px;
  font-size: 12px;
}

.status-solved {
  color: #1a8cff;
}

.status-unsolved {
  color: #666;
}

.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 8px;
}

.pagination button {
  width: 32px;
  height: 32px;
  border: none;
  background: none;
  cursor: pointer;
  border-radius: 4px;
  font-size: 14px;
}

.pagination button.active {
  background: #1a1b3a;
  color: white;
}

.pagination button:hover:not(.active) {
  background: #f5f5f5;
}
</style>