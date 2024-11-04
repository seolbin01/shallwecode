<script setup>
import {ref, computed, watch, onMounted} from 'vue'
import axios from 'axios';

const ROWS_PER_PAGE = 7;
const itemsPerPage = 7;

const problems = ref([]);
const trys = ref([]);
const selectedProblem = ref(null);
const currentPage = ref(1);
const tryCurrentPage = ref(1);
const searchQuery = ref('');
const curTry = ref('');

const fetchMyProblemList = async () => {
  try {
    const response = await axios.get('http://localhost:8080/api/v1/problem/mylist');
    problems.value = response.data;
  } catch (error) {
    console.error('내 풀이 문제 목록을 불러오는 중 에러가 발생했습니다.', error.response ? error.response.data : error.message);
  }
};

const fetchTryList = async (problemId) => {
  try {
    const response = await axios.get(`http://localhost:8080/api/v1/problem/${problemId}/try`);
    trys.value = response.data;
  } catch (error) {
    console.error('풀이 시도 목록을 불러오는 중 에러가 발생했습니다.', error.response ? error.response.data : error.message);
  }
};

const handleTryClick = async (tryId) => {
  try {
    const response = await axios.get(
        `http://localhost:8080/api/v1/problem/${tryId}`
    );

    curTry.value = response.data;
    console.log(curTry)

  } catch (error) {
    console.error('풀이 시도 상세 조회 실패', error);
  }
};

const filteredProblems = computed(() => {
  if (!searchQuery.value) return problems.value;
  return problems.value.filter(problem =>
      problem.title.toLowerCase().includes(searchQuery.value.toLowerCase())
  );
});

const filteredTrys = computed(() => {
  return trys.value;
});

const totalProblemPages = computed(() =>
    Math.ceil(filteredProblems.value.length / ROWS_PER_PAGE)
);

const totalTryPages = computed(() =>
    Math.ceil(filteredTrys.value.length / ROWS_PER_PAGE)
);

const displayedProblems = computed(() => {
  const startIdx = (currentPage.value - 1) * ROWS_PER_PAGE;
  const endIdx = startIdx + ROWS_PER_PAGE;
  return filteredProblems.value.slice(startIdx, endIdx);
});

const displayedTrys = computed(() => {
  const startIdx = (tryCurrentPage.value - 1) * ROWS_PER_PAGE;
  const endIdx = startIdx + ROWS_PER_PAGE;
  return filteredTrys.value.slice(startIdx, endIdx);
});

const emptyRowsProblemCount = computed(() =>
    ROWS_PER_PAGE - displayedProblems.value.length
);

const emptyRowsTryCount = computed(() =>
    ROWS_PER_PAGE - displayedTrys.value.length
);

const changeProblemPage = (page) => {
  if (page === 'prev' && currentPage.value > 1) {
    currentPage.value--;
  } else if (page === 'next' && currentPage.value < totalProblemPages.value) {
    currentPage.value++;
  } else if (typeof page === 'number') {
    currentPage.value = page;
  }
};

const changeTryPage = (page) => {
  if (page === 'prev' && tryCurrentPage.value > 1) {
    tryCurrentPage.value--;
  } else if (page === 'next' && tryCurrentPage.value < totalTryPages.value) {
    tryCurrentPage.value++;
  } else if (typeof page === 'number') {
    tryCurrentPage.value = page;
  }
};

const selectProblem = async (problem) => {
  selectedProblem.value = problem;
  tryCurrentPage.value = 1;
  await fetchTryList(problem.problemId);
};

watch(searchQuery, () => {
  currentPage.value = 1;
});

onMounted(() => {
  fetchMyProblemList();
});
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
        <tr
            v-for="(problem, index) in displayedProblems"
            :key="problem.id"
            @click="selectProblem(problem)"
            :class="{ 'selected-row': selectedProblem?.problemId === problem.problemId }"
        >
          <td>{{ (currentPage - 1) * itemsPerPage + index + 1 }}</td>
          <td>{{ problem.title }}</td>
          <td><span class="level-badge">Lv. {{ problem.problemLevel }}</span></td>
          <td>
              <span :class="['status', problem.solved ? 'status-solved' : 'status-unsolved']">
                {{ problem.solved ? '해결' : '미해결' }}
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
            @click="changeProblemPage('prev')"
            :disabled="currentPage === 1"
        >◀</button>
        <button
            v-for="page in totalProblemPages"
            :key="page"
            @click="changeProblemPage(page)"
            :class="{ active: currentPage === page }"
        >
          {{ page }}
        </button>
        <button
            @click="changeProblemPage('next')"
            :disabled="currentPage === totalProblemPages"
        >▶</button>
      </div>
    </div>

    <div class="try-container">
      <h1 class="title">풀이 시도</h1>

      <div class="try-area">
        <h3 v-if="selectedProblem">
          "{{ selectedProblem.title }}" 문제의 풀이 시도
        </h3>
        <h3 v-else>
          왼쪽에서 문제를 선택해주세요
        </h3>
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
        <tr v-for="(try$, index) in displayedTrys"
            :key="try$.id"
            @click="handleTryClick(try$.tryId)"
        >
          <td>{{ (tryCurrentPage - 1) * itemsPerPage + index + 1 }}</td>
          <td>{{ try$.tryLanguage }}</td>
          <td>
              <span :class="['status', try$.solved ? 'status-solved' : 'status-unsolved']">
                {{ try$.solved ? '해결' : '미해결' }}
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
            @click="changeTryPage('prev')"
            :disabled="tryCurrentPage === 1"
        >◀</button>
        <button
            v-for="page in totalTryPages"
            :key="page"
            @click="changeTryPage(page)"
            :class="{ active: tryCurrentPage === page }"
        >
          {{ page }}
        </button>
        <button
            @click="changeTryPage('next')"
            :disabled="tryCurrentPage === totalTryPages"
        >▶</button>
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

.try-area h3 {
  margin: 0;
  font-size: 16px;
  color: #666;
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

.problem-table tr:not(.empty-row) {
  cursor: pointer;
}

.problem-table tr:not(.empty-row):hover {
  background-color: #f5f5f5;
}

.selected-row {
  background-color: #f0f7ff !important;
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