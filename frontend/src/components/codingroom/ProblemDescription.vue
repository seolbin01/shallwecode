<script setup>
const props = defineProps({
  content: {
    type: String,
    required: true
  }
});
</script>

<template>
  <div class="problem-header">
    문제 설명
  </div>
  <div class="scrollable-content">
    <div class="description">
      <div class="problem-section">
        <div class="section-content">
          {{ props.content }}
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.problem-header {
  height: 40px;
  padding: 0 20px;
  display: flex;
  align-items: center;
  color: var(--background-color);
  border-bottom: 1px var(--background-color) solid;
  background-color: var(--editor-color);
  position: sticky;
  top: 0;
  z-index: 1;
}

.description {
  color: var(--background-color);
  padding: 0;
  margin-bottom: 16px;
  height: auto;
  overflow: visible;
}

.problem-section {
  margin-bottom: 24px;
}

.section-content{
  margin-bottom: 16px;
  line-height: 1.6;
  padding-left: 4px;
}

.scrollable-content {
  flex: 1;
  overflow-y: auto;
  padding: 16px;
}

.description::-webkit-scrollbar {
  width: 6px;
}

.description::-webkit-scrollbar-track {
  background: rgba(255, 255, 255, 0.05);
  border-radius: 3px;
}

.description::-webkit-scrollbar-thumb {
  background: rgba(255, 255, 255, 0.2);
  border-radius: 3px;
}

.description::-webkit-scrollbar-thumb:hover {
  background: rgba(255, 255, 255, 0.3);
}

.constraints-section {
  background-color: rgba(0, 0, 0, 0.2);
  border-radius: 8px;
  padding: 16px 20px;
  margin-top: 24px;
}

.constraints-title {
  font-size: 16px;
  font-weight: 600;
  margin-bottom: 12px;
  color: #FFD700;
}

.constraints-list {
  list-style: none;
  padding: 0;
  margin: 0;
}

.constraints-list li {
  position: relative;
  padding-left: 16px;
  margin-bottom: 8px;
}

.constraints-list li::before {
  content: "•";
  position: absolute;
  left: 0;
  color: #FFD700;
}
</style>