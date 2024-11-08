import Login from "@/views/user/Login.vue";
import UserList from "@/views/user/UserList.vue";
import MyPage from "@/views/user/MyPage.vue";
import SignUp from "@/views/user/SignUp.vue";

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
    },
    {
        path: '/sign-up',
        name: 'SignUp',
        component: SignUp // 회원가입 컴포넌트
    }
];