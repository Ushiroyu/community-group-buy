<template>
  <el-card>
    <el-tabs v-model="tab" type="border-card">
      <el-tab-pane label="商品管理" name="products">
        <el-button type="primary" @click="showForm = true">新增商品</el-button>
        <el-dialog title="新增/编辑商品" v-model="showForm">
          <el-form :model="form" label-width="100px">
            <el-form-item label="名称">
              <el-input v-model="form.name" />
            </el-form-item>
            <el-form-item label="描述">
              <el-input type="textarea" v-model="form.description" />
            </el-form-item>
            <el-form-item label="价格">
              <el-input-number v-model="form.price" :min="0" :step="0.01" />
            </el-form-item>
            <el-form-item label="库存">
              <el-input-number v-model="form.stock" :min="0" />
            </el-form-item>
            <el-form-item label="分类">
              <el-select v-model="form.categoryId" placeholder="选择分类">
                <el-option v-for="c in categories" :key="c.id" :label="c.name" :value="c.id" />
              </el-select>
            </el-form-item>
          </el-form>
          <span slot="footer">
            <el-button @click="resetForm">取消</el-button>
            <el-button type="primary" @click="saveProduct">保存</el-button>
          </span>
        </el-dialog>

        <el-table :data="products" style="width: 100%; margin-top: 20px;">
          <el-table-column prop="name" label="名称" />
          <el-table-column prop="price" label="价格" />
          <el-table-column prop="stock" label="库存" />
          <el-table-column prop="category.name" label="分类" />
          <el-table-column label="操作">
            <template #default="{ row }">
              <el-button size="mini" @click="editProduct(row)">编辑</el-button>
              <el-button size="mini" type="danger" @click="deleteProduct(row.id)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>

      <el-tab-pane label="订单处理" name="orders">
        <el-table :data="orders" style="width: 100%;">
          <el-table-column prop="id" label="订单号" />
          <el-table-column prop="user.username" label="下单用户" />
          <el-table-column prop="totalAmount" label="总价" />
          <el-table-column prop="status" label="状态" />
          <el-table-column label="操作">
            <template #default="{ row }">
              <el-button v-if="row.status === 'PAID'" size="mini" type="primary" @click="shipOrder(row.id)">发货</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
    </el-tabs>
  </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import api from '@/services/api';
import { useAuthStore } from '@/store/auth';

const auth = useAuthStore();
const supplierId = auth.user.supplierId;
const tab = ref('products');

// Products
const products = ref([]);
const categories = ref([]);
const showForm = ref(false);
const form = ref({ id: null, name: '', description: '', price: 0, stock: 0, categoryId: null });

// Orders
const orders = ref([]);

onMounted(async () => {
  await loadProducts();
  categories.value = (await api.get('/categories')).data;
  await loadOrders();
});

async function loadProducts() {
  products.value = (await api.get(`/suppliers/${supplierId}/products`)).data;
}
async function saveProduct() {
  if (form.value.id) {
    await api.put(`/suppliers/${supplierId}/products/${form.value.id}`, form.value);
  } else {
    await api.post(`/suppliers/${supplierId}/products`, form.value);
  }
  showForm.value = false;
  resetForm();
  await loadProducts();
}
function editProduct(row) {
  Object.assign(form.value, { ...row, categoryId: row.category.id });
  showForm.value = true;
}
async function deleteProduct(id) {
  await api.delete(`/suppliers/${supplierId}/products/${id}`);
  await loadProducts();
}
function resetForm() {
  form.value = { id: null, name: '', description: '', price: 0, stock: 0, categoryId: null };
}

async function loadOrders() {
  orders.value = (await api.get(`/orders?supplierId=${supplierId}`)).data;
}
async function shipOrder(orderId) {
  await api.patch(`/orders/${orderId}`, { status: 'SHIPPED' });
  await loadOrders();
  ElMessage.success('已发货');
}
</script>

<style scoped>
/* 可根据需求添加样式 */
</style>
