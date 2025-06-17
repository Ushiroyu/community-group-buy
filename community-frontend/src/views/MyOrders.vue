<template>
  <el-card>
    <h3>我的订单</h3>
    <el-table :data="orders">
      <el-table-column prop="id" label="订单号" />
      <el-table-column prop="status" label="状态" />
      <el-table-column label="操作">
        <template #default="{ row }">
          <el-button v-if="row.status==='DELIVERED'" size="mini" @click="confirm(row.id)">
            确认收货
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import api from '@/services/api';
import { useAuthStore } from '@/store/auth';

const auth = useAuthStore();
const orders = ref([]);

onMounted(async () => {
  orders.value = (await api.get(`/orders?userId=${auth.user.id}`)).data;
});

async function confirm(id) {
  await api.patch(`/orders/${id}/confirm`);
  orders.value = orders.value.map(o => o.id === id ? { ...o, status:'CANCELLED' } : o);
  ElMessage.success('已确认收货');
}
</script>
