<template>
  <el-card>
    <h3>选择收货地址</h3>
    <el-select v-model="addrId" placeholder="请选择地址">
      <el-option
        v-for="a in addresses"
        :key="a.id"
        :label="`${a.province} ${a.city} ${a.detail}`"
        :value="a.id"
      />
    </el-select>
    <div class="order-summary">
      <p>共 {{ cart.totalCount }} 件，合计 ￥{{ cart.totalAmount.toFixed(2) }}</p>
      <el-button type="primary" @click="doCheckout">确认下单</el-button>
    </div>
  </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import api from '@/services/api';
import { useCartStore } from '@/store/cart';
import { useAuthStore } from '@/store/auth';
import { useRouter } from 'vue-router';

const cart = useCartStore();
const auth = useAuthStore();
const router = useRouter();

const addresses = ref([]);
const addrId = ref(null);

onMounted(async () => {
  const res = await api.get(`/users/${auth.user.id}/addresses`);
  addresses.value = res.data;
});

async function doCheckout() {
  if (!addrId.value) return ElMessage.warning('请选择地址');
  try {
    const order = await cart.checkout(addrId.value);
    ElMessage.success('下单成功，订单号：' + order.id);
    router.push(`/orders/${order.id}`);
  } catch {
    ElMessage.error('下单失败');
  }
}
</script>

<style>
.order-summary {
  margin-top: 20px;
  text-align: right;
}
</style>
