import Login from "@/views/user/Login.vue";
import UserList from "@/views/user/UserList.vue";
import MyPage from "@/views/user/MyPage.vue";

export default [
    {
        path: '/login',
        component: Login
    },
    {
        path: '/user-list',
        component: UserList
    },
    {
        path: '/mypage',
        component: MyPage
    }
];