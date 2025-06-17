<template>
  <el-row gutter="20">
    <el-col :span="8">
      <el-card>
        <h4>待审核申请</h4>
        <el-table :data="apps">
          <el-table-column prop="user.username" label="申请人" />
          <el-table-column prop="applyTime" label="时间" />
          <el-table-column label="操作">
            <template #default="{ row }">
              <el-button size="mini" @click="approve(row.id, true)">通过</el-button>
              <el-button size="mini" @click="approve(row.id, false)">拒绝</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-card>
    </el-col>
    <el-col :span="8">
      <el-card>
        <h4>订单统计</h4>
        <p>总订单数：{{ stats.totalOrders }}</p>
        <p>已成交：{{ stats.completed }}</p>
      </el-card>
    </el-col>
  </el-row>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import api from '@/services/api';

const apps = ref([]);
const stats = ref({ totalOrders: 0, completed: 0 });

onMounted(async () => {
  apps.value = (await api.get('/leader/applications')).data;
  stats.value = (await api.get('/leader/stats')).data;
});

async function approve(id, ok) {
  await api.post(`/leader/applications/${id}/approve`, { approve: ok });
  ElMessage.success(ok ? '已通过' : '已拒绝');
  apps.value = apps.value.filter(a => a.id !== id);
}
</script>
