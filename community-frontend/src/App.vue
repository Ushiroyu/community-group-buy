<template>
  <NavBar />

  <el-container style="min-height: calc(100vh - 121px)">
    <el-main style="padding: 20px;">
      <router-view />
    </el-main>
  </el-container>

  <Footer />
</template>

<script setup>
import NavBar from '@/components/NavBar.vue';
import Footer from '@/components/Footer.vue';
</script>
