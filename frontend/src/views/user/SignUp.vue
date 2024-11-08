<template>
  <div>
    <h1>회원가입</h1>
    <form @submit.prevent="registerUser">
      <div>
        <label for="nickname">닉네임</label>
        <input type="text" v-model="nickname" id="nickname" required />
      </div>
      <div>
        <label for="email">이메일</label>
        <input type="email" v-model="email" id="email" required />
      </div>
      <button type="submit">가입하기</button>
    </form>
  </div>
</template>

<script setup>
import {onMounted, ref} from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios'; // axios를 사용하여 HTTP 요청
import { useAuthStore } from "@/stores/auth.js";

const router = useRouter(); // 라우터 접근
const nickname = ref(''); // 반응형 데이터 정의
const email = ref(''); // 이메일을 위한 반응형 데이터 정의
const store = useAuthStore();

const registerUser = async () => {
  try {
    const accessToken = store.accessToken; // 로컬 스토리지에서 토큰 가져오기
    const userId = store.userId; // 예시: userId도 로컬 스토리지에서 가져오는 경우
    console.log(accessToken);
    console.log(email.value)
    await axios.put('http://localhost:8080/api/v1/user',
        {
          nickname: nickname.value,
          userId: userId, // userId 추가
          email: email.value, // 이메일 추가
        },
        {
          headers: {
            Authorization: `Bearer ${accessToken}` // Authorization 헤더에 토큰 추가
          }
        }
    );
    router.push('/'); // 메인페이지로 리다이렉션
  } catch (error) {
    console.error('가입 실패:', error);
  }
};

function getCookie(name) {
  const value = `; ${document.cookie}`;
  const parts = value.split(`; ${name}=`);
  if (parts.length === 2) return parts.pop().split(';').shift();
  return null;
}

onMounted(() => {
  if (!store.accessToken){
    const token = getCookie('accessToken');  // 쿠키에서 'token' 값 가져오기
    if (token) {
      console.log('쿠키에서 토큰을 가져왔습니다:', token);
      store.login(token);  // 로그인설정
    } else {
      console.log('쿠키에 토큰이 없습니다.');
    }
  }
})
</script>

<style scoped>

</style>