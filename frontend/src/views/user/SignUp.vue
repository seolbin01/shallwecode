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
import {useRoute, useRouter} from 'vue-router';
import axios from 'axios';

const router = useRouter();
const route = useRoute();

const userId = ref(null);
const nickname = ref('');
const email = ref('');

const registerUser = async () => {
  try {
    console.log(email.value)
    await axios.put('http://localhost:8080/api/v1/user',
        {
          nickname: nickname.value,
          userId: userId.value,
          email: email.value,
        }
    );
    await router.push('/');
  } catch (error) {
    console.error('가입 실패:', error);
  }
};

onMounted(() => {
  userId.value = route.query.userId;
})
</script>

<style scoped>

</style>