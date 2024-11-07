import { createApp } from 'vue';
import { createPinia } from "pinia";
import { install as VueMonacoEditorPlugin } from '@guolao/vue-monaco-editor';
import App from './App.vue';
import router from "@/router/index.js";

const app = createApp(App);
const pinia = createPinia();

app.use(router);
app.use(pinia);
app.use(VueMonacoEditorPlugin, {
   paths: {
       vs: 'https://cdn.jsdelivr.net/npm/monaco-editor@0.43.0/min/vs'
   }
});

app.mount('#app');
