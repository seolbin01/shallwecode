<script setup>
import SideComponent from "@/components/problem/SideComponent.vue";
import ProbListComponent from "@/components/problem/ProbListComponent.vue";


</script>

<template>
  <div class="container">
    <!-- 사이드바 -->
    <SideComponent />
    <!-- 메인 컨텐츠 -->
      <div class="content">
        <ProbListComponent />
      </div>
  </div>
</template>

<style scoped>
body {
  font-family: Arial, sans-serif;
  margin: 0;
  padding: 0;
  background-color: #f5f5f5;
}

/* 메인 컨테이너 */
.container {
  display: flex;
  padding: 20px;
}

/* 메인 컨텐츠 */
.content {
  flex-grow: 1;
  background-color: #ffffff;
  padding: 20px;
  border-radius: 10px;
  border: 1px solid #ddd;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}
</style>