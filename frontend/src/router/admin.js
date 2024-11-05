import AdminProbList from "@/views/problem/AdminProbList.vue";
import AdminProbSave from "@/views/problem/AdminProbSave.vue";
import AdminProbUpdate from "@/views/problem/AdminProbUpdate.vue";

export default [
    {
        path: '/admin/problemList',
        component: AdminProbList
    },
    {
        path: '/admin/problemSave',
        component: AdminProbSave
    },
    {
        path: '/admin/problemUpdate',
        component: AdminProbUpdate
    }
];