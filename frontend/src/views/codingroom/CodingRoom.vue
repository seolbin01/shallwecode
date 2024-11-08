<script setup>
import CodeEditor from "@/components/codingroom/CodeEditor.vue";
import ProblemDescription from "@/components/codingroom/ProblemDescription.vue";
import Chat from "@/components/codingroom/ChatArea.vue";
</script>

<template>
  <div class="container">
    <div class="problem-container">
      <div class="content-wrapper">
        <ProblemDescription/>
        <Chat/>
      </div>
    </div>
    <div class="problem-container">
      <div class="content-wrapper">
        <CodeEditor/>
        <CodeResult/>
      </div>
    </div>
  </div>
</template>

<style scoped>
.container {
  display: flex;
  max-width: 100%;
}

.content-wrapper {
  display: flex;
  flex-direction: column;
  height: 100%;
}

.problem-container {
  flex: 1;
  display: flex;
  flex-direction: column;
  width: 50%;
  min-width: 600px;
  height: 641px;
  background-color: var(--editor-color);
  overflow: hidden;
}
</style>