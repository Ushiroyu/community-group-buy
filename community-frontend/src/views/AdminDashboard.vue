<template>
  <el-tabs v-model="tab">
    <el-tab-pane label="用户管理" name="users">
      <el-table :data="users">
        <el-table-column prop="username" label="用户名" />
        <el-table-column prop="email" label="邮箱" />
        <el-table-column label="操作">
          <template #default="{ row }">
            <el-button size="mini" @click="deleteUser(row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-tab-pane>
    <el-tab-pane label="商品审核" name="products">
      <el-table :data="pending">
        <el-table-column prop="name" label="商品" />
        <el-table-column prop="supplier.name" label="供应商" />
        <el-table-column label="操作">
          <template #default="{ row }">
            <el-button size="mini" @click="approve(row.id, true)">通过</el-button>
            <el-button size="mini" @click="approve(row.id, false)">拒绝</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-tab-pane>
    <el-tab-pane label="订单管理" name="orders">
      <el-table :data="orders">
        <el-table-column prop="id" label="订单号" />
        <el-table-column prop="status" label="状态" />
        <el-table-column label="操作">
          <template #default="{ row }">
            <el-button size="mini" @click="changeStatus(row.id, 'SHIPPED')">发货</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-tab-pane>
  </el-tabs>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import api from '@/services/api';

const tab = ref('users');
const users = ref([]);
const pending = ref([]);
const orders = ref([]);

async function load() {
  if (tab.value === 'users') users.value = (await api.get('/admin/users')).data;
  if (tab.value === 'products') pending.value = (await api.get('/admin/products/pending')).data;
  if (tab.value === 'orders') orders.value = (await api.get('/admin/orders')).data;
}

onMounted(load);
watch(tab, load);

async function deleteUser(id) {
  await api.delete(`/admin/users/${id}`);
  load();
}
async function approve(id, ok) {
  await api.post(`/admin/products/${id}/approve`, { approve: ok });
  load();
}
async function changeStatus(id, status) {
  await api.patch(`/admin/orders/${id}`, { status });
  load();
}
</script>
