<template>
  <el-card>
    <h3>确认收货</h3>
    <p>订单号：{{ order.id }}</p>
    <el-divider />
    <el-table :data="order.items" style="width: 100%">
      <el-table-column prop="product.name" label="商品" />
      <el-table-column prop="quantity" label="数量" />
      <el-table-column prop="unitPrice" label="单价" />
    </el-table>
    <div style="margin-top:20px; text-align: right;">
      <el-button type="primary" @click="confirm">确认收货</el-button>
    </div>
  </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import api from '@/services/api';

const route = useRoute();
const router = useRouter();
const order = ref({ items: [] });

onMounted(async () => {
  const res = await api.get(`/orders/${route.params.id}`);
  order.value = res.data;
});

async function confirm() {
  await api.patch(`/orders/${route.params.id}/confirm`);
  ElMessage.success('已确认收货');
  router.push({ name: 'MyOrders' });
}
</script>

<style scoped>
/* 可根据需求调整样式 */
</style>
