<template>
  <el-card>
    <h3>社区管理</h3>
    <el-form :model="community" label-width="80px">
      <el-form-item label="名称">
        <el-input v-model="community.name" />
      </el-form-item>
      <el-form-item label="位置">
        <el-input v-model="community.location" />
      </el-form-item>
      <el-button type="primary" @click="update">保存</el-button>
    </el-form>
  </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import api from '@/services/api';
import { useAuthStore } from '@/store/auth';
import { ElMessage } from 'element-plus';

const auth = useAuthStore();
const community = ref({});

onMounted(async () => {
  community.value = (await api.get(`/leader/community`)).data;
});

async function update() {
  await api.put(`/leader/community`, community.value);
  ElMessage.success('更新成功');
}
</script>
