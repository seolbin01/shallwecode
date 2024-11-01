import Login from "@/views/user/Login.vue";
import UserList from "@/views/user/UserList.vue";

export default [
    {
        path: '/login',
        component: Login
    },
    {
        path: '/members',
        component: UserList
    }
];