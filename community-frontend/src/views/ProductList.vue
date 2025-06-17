<template>
  <el-row gutter="20">
    <el-col :span="6" v-for="p in products" :key="p.id">
      <el-card :body-style="{ padding: '10px' }">
        <img :src="p.imageUrl" style="width:100%" />
        <h3>{{ p.name }}</h3>
        <p>￥{{ p.price }}</p>
        <el-button type="primary" @click="toDetail(p.id)">查看</el-button>
      </el-card>
    </el-col>
  </el-row>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import api from '@/services/api';
import { useRouter } from 'vue-router';

const products = ref([]);
const router = useRouter();

onMounted(async () => {
  const res = await api.get('/products');
  products.value = res.data;
});

function toDetail(id) {
  router.push(`/products/${id}`);
}
</script>
