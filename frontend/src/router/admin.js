import AdminPage from "@/views/admin/AdminPage.vue";
import ProbSaveComponent from "@/components/admin/ProbSaveComponent.vue";
import ProbUpdateComponent from "@/components/admin/ProbUpdateComponent.vue";

export default [
    {
        path: '/admin',
        name : 'AdminPage',
        component: AdminPage
    },
    {
        path: '/admin/problem',
        component: ProbSaveComponent
    },
    {
        path: '/admin/problem/:problemId',
        component: ProbUpdateComponent
    }
];