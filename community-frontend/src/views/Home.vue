<template>
  <el-card shadow="hover" class="home-welcome">
    <h1>欢迎来到社区团购系统</h1>
  </el-card>

  <!-- 商品列表距离标题再留点空隙 -->
  <div class="product-list-container">
    <ProductList />
  </div>
</template>

<script setup>
import ProductList from './ProductList.vue';
</script>

<style scoped>
.home-welcome {
  text-align: center;
  margin-bottom: 20px;
}
.home-welcome h1 {
  font-size: 24px;
  margin: 0;
}

.product-list-container {
  margin-top: 20px;
}
</style>
