<template>
  <div class="problem-register">
    <h2>문제 수정 하기</h2>

    <div class="form-group">
      <label for="title">제목</label>
      <input type="text" id="title" v-model="title" placeholder="문제 제목을 입력하세요" />
    </div>

    <div class="form-group">
      <label for="content">내용</label>
      <textarea id="content" v-model="content" rows="10" placeholder="문제 설명을 입력하세요"></textarea>
    </div>

    <div class="form-group">
      <label for="problemLevel">난이도</label>
      <select id="problemLevel" v-model="problemLevel">
        <option value="1">Lv.1</option>
        <option value="2">Lv.2</option>
        <option value="3">Lv.3</option>
      </select>
    </div>

    <div class="form-group">
      <label>테스트 케이스</label>
      <button @click="addTestCase" class="add-button">추가</button>
      <table class="test-case-table">
        <thead>
        <tr>
          <th>입력</th>
          <th>출력</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(testCase, index) in testCases" :key="index">
          <td><input type="text" v-model="testCase.input" /></td>
          <td><input type="text" v-model="testCase.output" /></td>
        </tr>
        </tbody>
      </table>
    </div>

    <div class="button-group">
      <button @click="cancel" class="cancel-button">취소</button>
      <button @click="updateProblem" class="register-button">수정</button>
    </div>
  </div>
</template>

<script>
import { ref, onMounted } from "vue";
import axios from "axios";
import { useRouter, useRoute } from "vue-router";

export default {
  setup() {

    const router = useRouter();
    const route = useRoute(); // 현재 라우트 정보 가져오기
    const title = ref("");
    const content = ref("");
    const problemLevel = ref(1);
    const testCases = ref([{input: "", output: ""}]);

    // 문제 정보를 조회해서 초기값 설정
    onMounted(async () => {
      const problemId = route.params.problemId; // 문제 ID 추출
      try {
        const response = await axios.get(`http://localhost:8080/api/v1/problem/${problemId}`);
        const data = await response.data;
        console.log("문제 ID:", problemId);  // 문제 ID 확인
        console.log("데이터:", data);  // 문제 ID 확인

        console.log("등록된 문제:", {
          title: data.title,
          content: data.content,
          problemLevel: data.problemLevel,
          // testCases: data.testcases,
        });

        // 데이터 바인딩
        title.value = data.title;
        content.value = data.content;
        problemLevel.value = data.problemLevel;
      } catch (error) {
        console.error("문제 정보를 불러오는데 실패했습니다:", error);
        alert("문제 정보를 불러오는데 실패했습니다.");
      }
    });

    const addTestCase = () => {
      testCases.value.push({input: "", output: ""});
    };

    const cancel = () => {
      window.history.back();
    };

    const updateProblem = async () => {
      const problemId = route.params.id;
      const formData = {
        title: title.value,
        content: content.value,
        problemLevel: problemLevel.value,
        testcases: testCases.value,
      };

      try {
        const response = await axios.put(`http://localhost:8080/api/v1/problem/${problemId}`, formData);
        console.log("문제가 성공적으로 수정되었습니다:", response.data);
        await router.push("/admin");
      } catch (error) {
        console.error("문제 수정 중 오류가 발생했습니다:", error);
        alert("문제 수정에 실패했습니다. 다시 시도해주세요.");
      }
    };

    return {
      title,
      content,
      problemLevel,
      testCases,
      addTestCase,
      cancel,
      updateProblem,
    };
  },
};
</script>

<style scoped>
/* 기존 스타일 유지 */
</style>
