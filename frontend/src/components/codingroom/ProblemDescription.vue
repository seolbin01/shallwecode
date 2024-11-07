<script setup>
const problemText = '정수 배열 numbers가 주어집니다. 이 배열의 각 원소에 대해 다음과 같은 규칙으로 가공하여 새로운 배열을 만들어 반환하는 함수를 작성해주세요:\n' +
    '짝수인 경우: 해당 숫자를 2로 나눕니다.\n' +
    '홀수인 경우: 해당 숫자에 2를 곱하고 1을 더합니다.\n' +
    '결과값이 10을 넘는 경우: 10을 뺍니다.';

const constraints = [
  '배열 numbers의 길이는 1 이상 100,000 이하입니다.',
  '배열의 각 원소는 1 이상 100 이하의 자연수입니다.'
];

</script>

<template>
  <div class="problem-header">
    문제 설명
  </div>
  <div class="scrollable-content">
    <div class="description">
      <div class="problem-section">
        <div class="section-content">
          {{ problemText }}
        </div>
      </div>
      <div class="constraints-section">
        <h3 class="constraints-title">제한사항</h3>
        <ul class="constraints-list">
          <li v-for="(constraint, index) in constraints" :key="index">
            {{ constraint }}
          </li>
        </ul>
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