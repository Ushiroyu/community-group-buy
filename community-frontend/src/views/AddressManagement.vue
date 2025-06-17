<template>
  <el-card>
    <h3>收货地址管理</h3>
    <el-button type="primary" @click="showAdd=true">新增地址</el-button>
    <el-dialog title="新增地址" v-model="showAdd">
      <el-form :model="form" label-width="80px">
        <!-- 表单项同 Register.vue address fields -->
      </el-form>
      <span slot="footer">
        <el-button @click="showAdd=false">取消</el-button>
        <el-button type="primary" @click="add">保存</el-button>
      </span>
    </el-dialog>
    <el-table :data="addresses" style="margin-top:20px;">
      <el-table-column prop="receiverName" label="收件人" />
      <el-table-column prop="phone" label="电话" />
      <el-table-column label="地址">
        <template #default="{ row }">{{ `${row.province} ${row.city} ${row.detail}` }}</template>
      </el-table-column>
      <el-table-column label="操作">
        <template #default="{ row }">
          <el-button size="mini" @click="del(row.id)">删除</el-button>
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
const addresses = ref([]);
const showAdd = ref(false);
const form = ref({ receiverName:'', phone:'', province:'', city:'', detail:'' });

async function load() {
  addresses.value = (await api.get(`/users/${auth.user.id}/addresses`)).data;
}
onMounted(load);

async function add() {
  await api.post(`/users/${auth.user.id}/addresses`, form.value);
  showAdd.value = false;
  load();
}

async function del(id) {
  await api.delete(`/users/${auth.user.id}/addresses/${id}`);
  load();
}
</script>
