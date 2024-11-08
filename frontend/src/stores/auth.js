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

    function login(accessToken, refreshToken) {
        accessToken.value = accessToken;
        refreshToken.value = refreshToken;
        localStorage.setItem('accessToken', accessToken);
        localStorage.setItem('refreshToken', refreshToken);
        const payload = JSON.parse(atob(accessToken.split('.')[1]));
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

    return { accessToken, userRole, userId, login, logout, isAuthorized };
});
