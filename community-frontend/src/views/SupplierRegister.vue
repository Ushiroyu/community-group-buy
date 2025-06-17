<template>
  <el-card>
    <h3>供应商入驻</h3>
    <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
      <el-form-item label="公司名称" prop="name">
        <el-input v-model="form.name" placeholder="请输入公司或店铺名称" />
      </el-form-item>
      <el-form-item label="联系人邮箱" prop="email">
        <el-input v-model="form.email" placeholder="请输入联系人邮箱" />
      </el-form-item>
      <el-form-item label="联系电话" prop="contactPhone">
        <el-input v-model="form.contactPhone" placeholder="请输入联系电话" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">提交申请</el-button>
        <el-button @click="onReset">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script setup>
import { reactive, ref } from 'vue';
import { useRouter } from 'vue-router';
import api from '@/services/api';
import { ElMessage } from 'element-plus';

const router = useRouter();
const formRef = ref(null);
const form = reactive({ name: '', email: '', contactPhone: '' });
const rules = {
  name: [{ required: true, message: '请输入公司名称', trigger: 'blur' }],
  email: [{ required: true, type: 'email', message: '请输入有效邮箱', trigger: 'blur' }],
  contactPhone: [{ required: true, message: '请输入联系电话', trigger: 'blur' }]
};

async function onSubmit() {
  try {
    await formRef.value.validate();
    await api.post('/suppliers/register', form);
    ElMessage.success('申请已提交，待后台审核');
    router.push({ name: 'Home' });
  } catch (err) {
    ElMessage.error(err.response?.data?.message || '提交失败');
  }
}

function onReset() {
  formRef.value.resetFields();
}
</script>

<style scoped>
el-card {
  max-width: 600px;
  margin: 30px auto;
}
</style>
