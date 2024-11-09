<script setup>
import {computed, onMounted, ref, watch} from 'vue';
import axios from "axios";
import {getFetch} from "@/stores/apiClient.js";
import kakaoIcon from '@/assets/icons/kakao.svg'
import naverIcon from '@/assets/icons/naver.svg'
import googleIcon from '@/assets/icons/google.svg'
import {useAuthStore} from "@/stores/auth.js";

const store = useAuthStore();

const currentPage = ref(1);
const currentProblemPage = ref(1);
const friendItemsPerPage = 2;
const ROWS_PER_PAGE = 7;
const searchQuery = ref('');
const selectedLevel = ref(null);

const profile = ref('');

const fetchProfile = async () => {
  try {
    const response = await getFetch('http://localhost:8080/api/v1/user/profile');
    profile.value = response.data;
  } catch (error) {
    console.error('프로필을 불러오는데 에러가 발생했습니다.', error.response ? error.response.data : error.message);
  }
};

const providerIcon = computed(() => {
  if (!profile.value || !profile.value.provider) return '';

  switch (profile.value.provider) {
    case 'KAKAO':
      return kakaoIcon;
    case 'NAVER':
      return naverIcon;
    case 'GOOGLE':
      return googleIcon;
    default:
      return '';
  }
});

const friendsList = ref([]);

const fetchFriendList = async () => {
  try {
    const response = await getFetch('http://localhost:8080/api/v1/friend');
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

  if (friendsList.value.length === 0) return [];
  return friendsList.value.slice(start, end);
});

const nextPage = () => {
  if (currentPage.value < totalPages.value) {
    currentPage.value++;
  }
}

const prevPage = () => {
  if (currentPage.value > 1) {
    currentPage.value--
  }
}

const goToPage = (page) => {
  currentPage.value = page
}

const problems = ref([]);

const fetchProblemList = async () => {
  try {
    const response = await getFetch('http://localhost:8080/api/v1/problem');
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
  let filteredProblems = problems.value;

  if (searchQuery.value) {
    filteredProblems = filteredProblems.filter(problem =>
        problem.title.toLowerCase().includes(searchQuery.value.toLowerCase())
    );
  }

  if (selectedLevel.value) {
    filteredProblems = filteredProblems.filter(problem =>
        problem.level === selectedLevel.value
    );
  }

  const startIdx = (currentProblemPage.value - 1) * ROWS_PER_PAGE;
  const endIdx = startIdx + ROWS_PER_PAGE;
  return filteredProblems.slice(startIdx, endIdx);
});

const emptyRowsCount = computed(() =>
    ROWS_PER_PAGE - displayedProblems.value.length
);

const totalProblemPages = computed(() => {
  let filteredProblems = problems.value;

  if (searchQuery.value) {
    filteredProblems = filteredProblems.filter(problem =>
        problem.title.toLowerCase().includes(searchQuery.value.toLowerCase())
    );
  }

  if (selectedLevel.value) {
    filteredProblems = filteredProblems.filter(problem =>
        problem.level === selectedLevel.value
    );
  }

  return Math.ceil(filteredProblems.length / ROWS_PER_PAGE);
});

const changeProblemPage = (page) => {
  if (page === 'prev' && currentProblemPage.value > 1) {
    currentProblemPage.value--; // 문제 페이징 변수 사용
  } else if (page === 'next' && currentProblemPage.value < totalProblemPages.value) {
    currentProblemPage.value++; // 문제 페이징 변수 사용
  } else if (typeof page === 'number') {
    currentProblemPage.value = page; // 문제 페이징 변수 사용
  }
};

function getCookie(name) {
  const value = `; ${document.cookie}`;
  const parts = value.split(`; ${name}=`);
  if (parts.length === 2) return parts.pop().split(';').shift();
  return null;
}

watch(searchQuery, () => {
  currentProblemPage.value = 1;
});

watch(selectedLevel, () => {
  currentProblemPage.value = 1;
});

const handleLevelFilter = (level) => {
  selectedLevel.value = selectedLevel.value === level ? null : level;
};

onMounted(() => {

  if (!store.accessToken) {
    const token = getCookie('accessToken');  // 쿠키에서 'token' 값 가져오기
    const token2 = getCookie('refreshToken');
    if (token) {
      store.login(token, token2);  // 로그인설정
    } else {
      console.log('쿠키에 토큰이 없습니다.');
    }
  }

  if (store.accessToken) {
    fetchProfile();
    fetchFriendList();
  }
  fetchProblemList();

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
              placeholder="문제 제목 검색"
              v-model="searchQuery"
          >
        </div>

        <div class="filter-area">
          <button class="filter-button">상태 ▼</button>
          <div class="level-filter">
            <button
                v-for="level in [1, 2, 3, 4, 5]"
                :key="level"
                class="filter-button"
                :class="{ active: selectedLevel === level }"
                @click="handleLevelFilter(level)"
            >
              Lv.{{ level }}
            </button>
          </div>
          <button class="filter-button">해결 ▼</button>
        </div>

        <table class="table">
          <thead>
          <tr>
            <th>번호</th>
            <th>제목</th>
            <th>난이도</th>
            <th>상태</th>
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
            <td>
              <span :class="['status', problem.finished ? 'status-solved' : 'status-unsolved']">
                {{ problem.finished ? '해결' : '미해결' }}
              </span>
            </td>
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
          <h2 class="user-id">{{ profile.nickname }}</h2>
          <p class="user-email">
            {{ profile.email }}
            <img v-if="profile.provider" :src="providerIcon" :alt="profile.provider" class="provider-icon"/>
          </p>
        </div>

        <div class="stats-container">
          <div class="stat-item">
            <p class="stat-label">도전한 문제</p>
            <p class="stat-value">{{ profile.doingProblemCnt }}개</p>
          </div>
          <div class="stat-item">
            <p class="stat-label">미해결 문제</p>
            <p class="stat-value">{{ profile.notFinishedProblemCnt }}개</p>
          </div>
          <div class="stat-item">
            <p class="stat-label">해결한 문제</p>
            <p class="stat-value">{{ profile.finishedProblemCnt }}개</p>
          </div>
        </div>
      </div>

      <div class="friends-card">
        <h3 class="friends-title">친구 목록</h3>

        <div class="friends-list">
          <div v-for="friend in paginatedFriends" :key="friend.id" class="friend-item">
            <div class="friend-avatar">
              <img src="@/assets/icons/profile-friend.svg" alt="프로필 사진"/>
            </div>
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
  display: flex;
  justify-content: center;
  gap: 10px;
  margin-bottom: 12px;
}

.level-filter {
  display: flex;
  gap: 4px;
}

.filter-button {
  padding: 6px 12px;
  border: 1px solid #e1e1e1;
  border-radius: 4px;
  background: white;
  font-size: 14px;
  cursor: pointer;
  transition: all 0.2s;
}

.filter-button:hover {
  background: #f5f5f5;
}

.filter-button.active {
  background: #1a1b3a;
  color: white;
  border-color: #1a1b3a;
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
  width: 32px;
  height: 32px;
  margin-left: 12px;
  margin-right: 12px;
  border-radius: 50%;
  overflow: hidden;
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