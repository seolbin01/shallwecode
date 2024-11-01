import { defineStore } from "pinia";
import { onMounted, ref } from "vue";

export const useAuthStore = defineStore('auth', () => {
    const accessToken = ref(null);
    const userRole = ref(null);

    onMounted(() => {
        const token = localStorage.getItem('accessToken');
        if (token) {
            accessToken.value = token;
            const payload = JSON.parse(atob(token.split('.')[1]));
            userRole.value = payload.auth;
        }
    });

    function login(token) {
        accessToken.value = token;
        localStorage.setItem('accessToken', token);
        const payload = JSON.parse(atob(token.split('.')[1]));
        userRole.value = payload.auth;
    }

    function logout() {
        accessToken.value = null;
        userRole.value = null;
        localStorage.removeItem('accessToken');
    }

    function isAuthorized(requiredRole) {
        if (!userRole.value) return false;
        return userRole.value.includes(requiredRole);
    }

    return { accessToken, userRole, login, logout, isAuthorized };
});
