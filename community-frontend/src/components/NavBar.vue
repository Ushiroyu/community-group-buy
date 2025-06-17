<template>
  <el-header height="60px">
    <el-row align="middle" justify="space-between">
      <el-col>
        <router-link to="/"><img src="@/assets/logo.png" height="40" /></router-link>
      </el-col>
      <el-col>
        <el-menu mode="horizontal" :default-active="active" @select="onSelect">
          <el-menu-item index="/">首页</el-menu-item>
          <el-menu-item index="/products">商品</el-menu-item>
          <el-menu-item index="/cart">购物车<span v-if="cartCount">({{ cartCount }})</span></el-menu-item>
          <el-menu-item v-if="isLoggedIn" index="/orders">我的订单</el-menu-item>
        </el-menu>
      </el-col>
      <el-col>
        <template v-if="!isLoggedIn">
          <router-link to="/login">登录</router-link> |
          <router-link to="/register">注册</router-link>
        </template>
        <template v-else>
          <el-dropdown>
            <span class="el-dropdown-link">{{ user.username }} <i class="el-icon-arrow-down"/></span>
            <el-dropdown-menu>
              <el-dropdown-item @click.native="onSelect('Profile')">个人中心</el-dropdown-item>
              <el-dropdown-item v-if="role==='leader'" @click.native="onSelect('LeaderDashboard')">团长后台</el-dropdown-item>
              <el-dropdown-item v-if="role==='supplier'" @click.native="onSelect('SupplierDashboard')">供应商后台</el-dropdown-item>
              <el-dropdown-item v-if="role==='admin'" @click.native="onSelect('AdminDashboard')">管理后台</el-dropdown-item>
              <el-dropdown-item divided @click.native="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </template>
      </el-col>
    </el-row>
  </el-header>
</template>

<script setup>
import { ref, computed, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useAuthStore } from '@/store/auth';
import { useCartStore } from '@/store/cart';

const route = useRoute(), router = useRouter();
const active = ref(route.path);
watch(route, () => active.value = route.path);

const auth = useAuthStore(), cart = useCartStore();
const isLoggedIn = computed(() => auth.isLoggedIn);
const user = computed(() => auth.user || {});
const role = computed(() => auth.userRole);
const cartCount = computed(() => cart.totalCount);

function onSelect(index) {
  router.push(isNaN(index) && index in router.getRoutes().reduce((m, r) => (m[r.name]=r.path, m), {}) 
    ? { name: index } : index);
}
function logout() {
  auth.logout();
  router.push('/');
}
</script>
