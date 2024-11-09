import { defineStore } from "pinia";
import { onMounted, ref } from "vue";

export const useAuthStore = defineStore('auth', () => {
    const accessToken = ref(null);
    const refreshToken = ref(null);
    const userRole = ref(null);
    const userId = ref(null);

    onMounted(() => {
        const token1 = localStorage.getItem('accessToken');
        const token2 = localStorage.getItem('refreshToken');
        if (token1) {
            accessToken.value = token1;
            refreshToken.value = token2
            const payload = JSON.parse(atob(token1.split('.')[1]));
            userId.value = payload.userId;
            userRole.value = payload.auth;
        }
        console.log(accessToken.value);
        console.log(refreshToken.value);
    });

    function login(accssToken, refreToken) {
        accessToken.value = accssToken
        refreshToken.value = refreToken;
        localStorage.setItem('accessToken', accssToken);
        localStorage.setItem('refreshToken', refreToken);
        const payload = JSON.parse(atob(accssToken.split('.')[1]));
        userId.value = payload.userId;
        userRole.value = payload.auth;
    }

    function logout() {
        accessToken.value = null;
        refreshToken.value = null;
        userRole.value = null;
        userId.value = null;
        localStorage.removeItem('accessToken');
        localStorage.removeItem('refreshToken');
    }

    function isAuthorized(requiredRole) {
        if (!userRole.value) return false;
        return userRole.value.includes(requiredRole);
    }

    return { accessToken, refreshToken, userRole, userId, login, logout, isAuthorized };
});

function getCookie(name) {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) return parts.pop().split(';').shift();
    return null;
}
