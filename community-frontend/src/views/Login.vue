<template>
  <el-form :model="form" @submit.prevent="onSubmit" class="login-form">
    <el-form-item label="用户名" prop="username">
      <el-input v-model="form.username" />
    </el-form-item>
    <el-form-item label="密码" prop="password">
      <el-input type="password" v-model="form.password" />
    </el-form-item>
    <el-button type="primary" @click="onSubmit">登录</el-button>
  </el-form>
</template>

<script setup>
import { reactive } from 'vue';
import { useRouter } from 'vue-router';
import { useAuthStore } from '@/store/auth';

const router = useRouter();
const auth = useAuthStore();
const form = reactive({ username: '', password: '' });

async function onSubmit() {
  try {
    await auth.login(form);
    router.push('/');
  } catch (e) {
    alert('登录失败，请检查用户名和密码');
  }
}
</script>
