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
  if(page >= 1 && page <= props.totalPages) {
    emit('page-changed', page);
  }
}

const visiblePages = computed(() => {
  const pages = [];
  const range = 5;
  const start = Math.max(1, props.currentPage - range);
  const end = Math.min(props.totalPages, props.currentPage + range);

  for(let i = start; i <= end; i++) {
    pages.push(i);
  }

  return pages;
});
</script>

<template>
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