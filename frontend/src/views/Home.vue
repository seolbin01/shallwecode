<script setup>
import { computed, onMounted, ref } from 'vue';
import axios from "axios";

const currentPage = ref(1);
const currentProblemPage = ref(1); // 문제 페이징을 위한 변수
const friendItemsPerPage = 2;
const ROWS_PER_PAGE = 7;
const searchQuery = ref('');

const noTryCount = ref(0);
const unSolvedCount = ref(0);
const SolvedCount = ref(0);

const userProfile = ref({
  userId: 'USER01',
  email: 'testuser01@naver.com',
  stats: {
    pendingIssues: computed(() => noTryCount.value),
    unresolvedIssues: computed(() => unSolvedCount.value),
    resolvedIssues: computed(() => SolvedCount.value)
  }
});

const fetchTryProblemCount = async () => {
  try {
    const noTryResponse = await axios.get('http://localhost:8080/api/v1/problem/mylist/notry');
    noTryCount.value = noTryResponse.data;
  } catch (error) {
    console.error('미시도 문제 목록 개수를 불러오는데 에러가 발생했습니다.', error.response ? error.response.data : error.message);
  }

  try {
    const unSolvedResponse = await axios.get('http://localhost:8080/api/v1/problem/mylist/unsolved');
    unSolvedCount.value = unSolvedResponse.data;
  } catch (error) {
    console.error('미해결 문제 목록 개수를 불러오는데 에러가 발생했습니다.', error.response ? error.response.data : error.message);
  }

  try {
    const solvedResponse = await axios.get('http://localhost:8080/api/v1/problem/mylist/solved');
    SolvedCount.value = solvedResponse.data;
  } catch (error) {
    console.error('해결된 문제 목록 개수를 불러오는데 에러가 발생했습니다.', error.response ? error.response.data : error.message);
  }
};

const friendsList = ref([]);

const fetchFriendList = async () => {
  try {
    const response = await axios.get('http://localhost:8080/api/v1/friend');
    friendsList.value = response.data;
  } catch (error) {
    console.error('친구 목록을 가져오는 중 오류가 발생했습니다:', error);
  }
};

const totalPages = computed(() =>
    Math.ceil(friendsList.value.length / friendItemsPerPage)
);

const paginatedFriends = computed(() => {
  const start = (currentPage.value - 1) * friendItemsPerPage;
  const end = start + friendItemsPerPage;
  return friendsList.value.slice(start, end);
});

const nextPage = () => {
  if (currentPage.value < totalPages.value) {
    currentPage.value++;
  }
};

const prevPage = () => {
  if (currentPage.value > 1) {
    currentPage.value--;
  }
};

const goToPage = (page) => {
  currentPage.value = page;
};

const problems = ref([]);

const fetchProblemList = async () => {
  try {
    const response = await axios.get('http://localhost:8080/api/v1/problem/list');
    problems.value = response.data;
  } catch (error) {
    console.error('문제 목록을 불러오는 중 에러가 발생했습니다.', error.response ? error.response.data : error.message);
  }
};

const handleProblemClick = async (problem) => {
  try {
    console.log(problem.problemId);
  } catch (error) {
    console.error('코딩방 생성 중 에러가 발생했습니다.', error.response ? error.response.data : error.message);
  }
};

const displayedProblems = computed(() => {
  const startIdx = (currentProblemPage.value - 1) * ROWS_PER_PAGE;
  const endIdx = startIdx + ROWS_PER_PAGE;
  return problems.value.slice(startIdx, endIdx);
});

const emptyRowsCount = computed(() =>
    ROWS_PER_PAGE - displayedProblems.value.length
);

const totalProblemPages = computed(() =>
    Math.ceil(problems.value.length / ROWS_PER_PAGE)
);

const changeProblemPage = (page) => {
  if (page === 'prev' && currentProblemPage.value > 1) {
    currentProblemPage.value--; // 문제 페이징 변수 사용
  } else if (page === 'next' && currentProblemPage.value < totalProblemPages.value) {
    currentProblemPage.value++; // 문제 페이징 변수 사용
  } else if (typeof page === 'number') {
    currentProblemPage.value = page; // 문제 페이징 변수 사용
  }
};

onMounted(() => {
  fetchTryProblemCount();
  fetchProblemList();
  fetchFriendList();
});
</script>


<template>
  <div class="main-container">
    <div class="container">
      <div class="problem-container">
        <h1 class="title">문제 목록</h1>

        <div class="search-area">
          <input
              type="text"
              class="search-input"
              placeholder="문제 검색"
              v-model="searchQuery"
          >
        </div>

        <div class="filter-area">
          <button class="filter-button">상태 ▼</button>
          <button class="filter-button">난이도 ▼</button>
          <button class="filter-button">해결 ▼</button>
        </div>

        <table class="table">
          <thead>
          <tr>
            <th>번호</th>
            <th>제목</th>
            <th>난이도</th>
          </tr>
          </thead>
          <tbody>
          <tr
              v-for="(problem, index) in displayedProblems"
              @click="handleProblemClick(problem)"
          >
            <td>{{ (currentProblemPage - 1) * ROWS_PER_PAGE + index + 1 }}</td>
            <td>{{ problem.title }}</td>
            <td><span class="level-badge">Lv. {{ problem.level }}</span></td>
          </tr>
          </tbody>
        </table>

        <div class="pagination">
          <button
              @click="changeProblemPage('prev')"
              :disabled="currentProblemPage === 1"
          >◀
          </button>
          <button
              v-for="page in totalProblemPages"
              :key="page"
              @click="changeProblemPage(page)"
              :class="{ active: currentProblemPage === page }"
          >
            {{ page }}
          </button>
          <button
              @click="changeProblemPage('next')"
              :disabled="currentProblemPage === totalProblemPages"
          >▶
          </button>
        </div>
      </div>
    </div>
    <div class="profile-container">
      <div class="profile-card">
        <div class="user-header">
          <h2 class="user-id">{{ userProfile.userId }}</h2>
          <p class="user-email">{{ userProfile.email }}</p>
        </div>

        <div class="stats-container">
          <div class="stat-item">
            <p class="stat-label">미도전 문제</p>
            <p class="stat-value">{{ userProfile.stats.pendingIssues }}개</p>
          </div>
          <div class="stat-item">
            <p class="stat-label">도전한 문제</p>
            <p class="stat-value">{{ userProfile.stats.unresolvedIssues }}개</p>
          </div>
          <div class="stat-item">
            <p class="stat-label">해결한 문제</p>
            <p class="stat-value">{{ userProfile.stats.resolvedIssues }}개</p>
          </div>
        </div>
      </div>

      <div class="friends-card">
        <h3 class="friends-title">친구 목록</h3>

        <div class="friends-list">
          <div v-for="friend in paginatedFriends" :key="friend.id" class="friend-item">
            <div class="friend-avatar"></div>
            <span class="friend-name">{{ friend.nickname }}</span>
          </div>
        </div>

        <div class="pagination">
          <button
              class="pagination-btn"
              @click="prevPage"
              :disabled="currentPage === 1"
          >◀
          </button>

          <template v-for="page in totalPages" :key="page">
            <button
                class="pagination-btn"
                :class="{ active: currentPage === page }"
                @click="goToPage(page)"
            >
              {{ page }}
            </button>
          </template>

          <button
              class="pagination-btn"
              @click="nextPage"
              :disabled="currentPage === totalPages"
          >▶
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.main-container {
  background-color: var(--background-color);
  display: flex;
  padding: 20px;
  gap: 20px;
}

.container {
  width: 1000px;
  background: white;
  border-radius: 16px;
  padding: 24px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', 'Apple SD Gothic Neo', 'Noto Sans KR', sans-serif;
  display: flex;
  justify-content: center;
  gap: 70px;
}

.title {
  text-align: center;
  font-size: 20px;
  font-weight: 600;
  margin-bottom: 12px;
}

.search-area {
  margin-bottom: 12px;
  text-align: center;
}

.filter-area {
  margin-bottom: 12px;
  text-align: center;
}

.table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 24px;
}

.table th {
  background: #1a1b3a;
  color: white;
  padding: 12px;
  text-align: center;
  font-weight: normal;
}

.table td {
  width: 200px;
  padding: 12px;
  border-bottom: 1px solid #e1e1e1;
  font-size: 14px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  height: 20px;
  line-height: 20px;
  text-align: center;
}

.table tr:not(.empty-row) {
  cursor: pointer;
}

.table tr:not(.empty-row):hover {
  background-color: #f5f5f5;
}

button {
  cursor: pointer;
  margin-left: 4px;
  margin-right: 4px;
}

.level-badge {
  background: #f0f0f0;
  padding: 4px 8px;
  border-radius: 4px;
  font-size: 12px;
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

.profile-container {
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
  max-width: 400px;
  margin: 0 auto;
}

.profile-card, .friends-card {
  background: white;
  border-radius: 12px;
  padding: 20px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  margin-bottom: 20px;
}

.user-header {
  margin-bottom: 20px;
}

.user-id {
  color: #4285f4;
  font-size: 18px;
  margin: 0;
  font-weight: 600;
}

.user-email {
  color: #666;
  font-size: 14px;
  margin: 5px 0 0 0;
}

.stats-container {
  display: flex;
  justify-content: space-between;
  text-align: center;
}

.stat-item {
  flex: 1;
}

.stat-label {
  font-size: 14px;
  color: #666;
  margin: 0 0 5px 0;
}

.stat-value {
  font-size: 16px;
  font-weight: 600;
  color: #333;
  margin: 0;
}

.friends-title {
  font-size: 16px;
  color: #333;
  margin: 0 0 20px 0;
}

.friends-list {
  margin-bottom: 20px;
}

.friend-item {
  display: flex;
  align-items: center;
  padding: 10px 0;
}

.friend-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background-color: #f0f0f0;
  margin-right: 12px;
}

.friend-name {
  font-size: 14px;
  color: #333;
}

.pagination {
  display: flex;
  justify-content: center;
  gap: 8px;
}

.pagination-btn {
  border: none;
  background: none;
  padding: 5px 10px;
  cursor: pointer;
  color: #666;
  border-radius: 4px;
}

.pagination-btn:hover:not(:disabled) {
  background-color: #f0f0f0;
}

.pagination-btn.active {
  background-color: #4285f4;
  color: white;
}

.pagination-btn:disabled {
  color: #ccc;
  cursor: not-allowed;
}
</style>