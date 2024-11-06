<script setup>
import { computed } from 'vue';

/* 부모 컴포넌트로부터 전달 받음 */
const props = defineProps({
  currentPage: {
    type: Number,
    required: true
  },
  totalPages: {
    type: Number,
    required: true
  },
  totalItems: {
    type: Number,
    required: true
  }
});

/* 부모 컴포넌트로 이벤트 전달 */
const emit = defineEmits(['page-changed']);

const changePage = (page) => {
  if(page >= 1 && page <= props.totalPages) {
    emit('page-changed', page);
  }
}

const visiblePages = computed(() => {
  const pages = [];
  const range = 5; // 현재 페이지를 기준으로 몇개의 페이지를 표시할 갯수
  const start = Math.max(1, props.currentPage - range); // max 는 페이지 값이 음수가 나오지 않고 1로 나올 수 있게 보장하기 위함.
  const end = Math.min(props.totalPages, props.currentPage + range); // min 은 페이지 값이 오버되지 않도록 할 수 있게 함.

  for(let i = start; i <= end; i++) {
    pages.push(i);
  }

  return pages;
});
</script>

<template>
  <!-- 페이지네이션 -->
  <div class="pagination">
    <button
      :disabled="currentPage === 1"
      @click="changePage(currentPage - 1)">
      &lt;
    </button>
    <span v-for="page in visiblePages" :key="page">
      <button
        :class="{active: page === currentPage}"
        @click="changePage(page)">
        {{ page }}
      </button>
    </span>
    <button
     :disabled="currentPage === totalPages"
     @click="changePage(currentPage + 1)">
      &gt;
    </button>
  </div>
</template>

<style scoped>
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