<template>
  <el-table :data="items" style="width: 100%">
    <el-table-column prop="product.name" label="商品"></el-table-column>
    <el-table-column prop="quantity" label="数量">
      <template #default="{ row }">
        <el-input-number v-model="row.quantity" :min="1" @change="save" />
      </template>
    </el-table-column>
    <el-table-column label="单价" prop="product.price"></el-table-column>
    <el-table-column label="小计">
      <template #default="{ row }">
        ￥{{ (row.quantity * row.product.price).toFixed(2) }}
      </template>
    </el-table-column>
    <el-table-column label="操作">
      <template #default="{ row }">
        <el-button type="text" @click="remove(row.product.id)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>
  <div class="cart-footer">
    <span>总计：￥{{ totalAmount.toFixed(2) }}</span>
    <el-button type="primary" @click="toCheckout">去结算</el-button>
  </div>
</template>

<script setup>
import { computed } from 'vue';
import { useRouter } from 'vue-router';
import { useCartStore } from '@/store/cart';

const cart = useCartStore();
const router = useRouter();

const items = computed(() => cart.items);
const totalAmount = computed(() => cart.totalAmount);

function save() {
  localStorage.setItem('cart', JSON.stringify(cart.items));
}

function remove(id) {
  cart.removeFromCart(id);
}

function toCheckout() {
  router.push('/checkout');
}
</script>

<style>
.cart-footer {
  margin-top: 20px;
  text-align: right;
}
</style>
