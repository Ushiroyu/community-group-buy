<template>
  <el-card>
    <h3>个人信息</h3>
    <el-form :model="user" label-width="100px">
      <el-form-item label="用户名">
        <el-input v-model="user.username" disabled />
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input v-model="user.email" />
      </el-form-item>
      <el-form-item label="手机号">
        <el-input v-model="user.phone" />
      </el-form-item>
      <el-button type="primary" @click="update">保存修改</el-button>
    </el-form>
  </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import api from '@/services/api';
import { useAuthStore } from '@/store/auth';

const auth = useAuthStore();
const user = ref({ ...auth.user });

onMounted(async () => {
  const res = await api.get(`/users/${auth.user.id}`);
  Object.assign(user.value, res.data);
});

async function update() {
  await api.put(`/users/${auth.user.id}`, user.value);
  auth.user = { ...user.value };
  ElMessage.success('更新成功');
}
</script>
