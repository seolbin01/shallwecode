<script setup>
const handleKakaoLogin = () => {

}

const handleNaverLogin = () => {

}

const handleGoogleLogin = () => {

}
</script>

<template>
  <div class="container">
    <div class="login-container">
      <h1 class="login-title">Sign In</h1>

      <button
          class="login-button kakao-button"
          @click="handleKakaoLogin"
      >
        <span class="button-icon">K</span>
        카카오 로그인
      </button>

      <button
          class="login-button naver-button"
          @click="handleNaverLogin"
      >
        <span class="button-icon">N</span>
        네이버 로그인
      </button>

      <button
          class="login-button google-button"
          @click="handleGoogleLogin"
      >
        <span class="button-icon">G</span>
        Continue with Google
      </button>
    </div>
  </div>
</template>

<style scoped>
.container {
  background-color: var(--background-color);
  display: flex;
  justify-content: center;
  align-items: center;
}

.login-container {
  background: white;
  width: 400px;
  padding: 40px;
  margin: 300px;
  border-radius: 16px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  text-align: center;
}

.login-title {
  font-size: 44px;
  font-weight: bold;
  margin-bottom: 30px;
  color: #333;
}

.login-button {
  width: 100%;
  padding: 12px;
  margin: 8px 0;
  border: none;
  border-radius: 8px;
  font-size: 20px;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.2s ease;
}

.kakao-button {
  background-color: #FEE500;
  color: #000000;
}

.naver-button {
  background-color: #1EC800;
  color: white;
}

.google-button {
  background-color: white;
  color: #757575;
  border: 1px solid #dadce0;
}

.button-icon {
  margin-right: 8px;
  width: 20px;
  height: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: bold;
}

/* 호버 효과 */
.login-button:hover {
  transform: translateY(-1px);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.login-button:active {
  transform: translateY(0);
}
</style>