<template>
  <div v-if="product">
    <el-card>
      <img :src="product.imageUrl" style="width:100%;max-height:300px" />
      <h2>{{ product.name }}</h2>
      <p>￥{{ product.price }}</p>
      <p>{{ product.description }}</p>
      <el-input-number v-model="qty" :min="1" />
      <el-button type="primary" @click="addCart">加入购物车</el-button>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { useProductsStore } from '@/store/products';
import { useCartStore } from '@/store/cart';

const route = useRoute();
const prodStore = useProductsStore();
const cartStore = useCartStore();
const qty = ref(1);

onMounted(async () => {
  await prodStore.fetchDetail(route.params.id);
});

function addCart() {
  cartStore.addToCart(prodStore.detail, qty.value);
  ElMessage.success('已加入购物车');
}
</script>
