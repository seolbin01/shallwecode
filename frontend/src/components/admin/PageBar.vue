<script setup>
import { computed } from 'vue';

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

const emit = defineEmits(['page-changed']);

const changePage = (page) => {
  if (page >= 1 && page <= props.totalPages) {
    emit('page-changed', page);
  }
}

const visiblePages = computed(() => {
  const pages = [];
  const range = 5;
  const start = Math.max(1, props.currentPage - range);
  const end = Math.min(props.totalPages, props.currentPage + range);

  for (let i = start; i <= end; i++) {
    pages.push(i);
  }

  return pages;
});
</script>

<template>
  <div class="pagination">
    <button
        class="page-btn"
        :disabled="currentPage === 1"
        @click="changePage(currentPage - 1)"
    >◀
    </button>
    <span v-for="page in visiblePages" :key="page">
      <button
          class="page-btn"
          :class="{ active: page === currentPage }"
          @click="changePage(page)"
      >
        {{ page }}
      </button>
    </span>
    <button
        class="page-btn"
        :disabled="currentPage === totalPages"
        @click="changePage(currentPage + 1)"
    >▶
    </button>
  </div>
</template>

<style scoped>
.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 8px;
  margin-top: 32px;
}

.page-btn {
  min-width: 36px;
  height: 36px;
  border: none;
  background: none;
  border-radius: 8px;
  font-size: 14px;
  color: #666;
  cursor: pointer;
  transition: all 0.2s;
}

.page-btn:hover:not(:disabled) {
  background: #f0f7ff;
  color: #1a73e8;
}

.page-btn.active {
  background: #1a73e8;
  color: white;
  font-weight: 500;
}

.page-btn:disabled {
  color: #ccc;
  cursor: not-allowed;
}

@media (max-width: 768px) {
  .pagination {
    gap: 4px;
  }

  .page-btn {
    min-width: 32px;
    height: 32px;
    font-size: 13px;
  }
}
</style>