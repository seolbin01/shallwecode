<script setup>
import { RouterView } from "vue-router";
import '@/assets/css/resetcss.css';
import Header from "@/components/common/Header.vue";
import Footer from "@/components/common/Footer.vue";
import {provide, ref} from "vue";

const refreshNotiList = ref(() => {});

provide('refreshNotiList', refreshNotiList);
</script>

<template>
  <Header/>
  <main>
    <RouterView/>
  </main>
  <Footer/>
</template>

<style scoped>

</style>
