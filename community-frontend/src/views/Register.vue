<template>
  <el-form :model="form" class="auth-form" :rules="rules" ref="fref">
    <el-form-item label="用户名" prop="username">
      <el-input v-model="form.username" />
    </el-form-item>
    <el-form-item label="密码" prop="password">
      <el-input type="password" v-model="form.password" />
    </el-form-item>
    <el-form-item label="邮箱" prop="email">
      <el-input v-model="form.email" />
    </el-form-item>
    <el-button type="primary" @click="onRegister">注册</el-button>
  </el-form>
</template>

<script setup>
import { ref, reactive } from 'vue';
import { useRouter } from 'vue-router';
import api from '@/services/api';

const router = useRouter();
const form = reactive({ username: '', password: '', email: '' });
const fref = ref(null);
const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
  email: [{ required: true, message: '请输入邮箱', trigger: 'blur' }]
};

async function onRegister() {
  try {
    await fref.value.validate();
    await api.post('/auth/register', form);
    ElMessage.success('注册成功，请登录');
    router.push('/login');
  } catch (err) {
    ElMessage.error(err.response?.data?.message || '注册失败');
  }
}
</script>
