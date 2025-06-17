<template>
  <el-card>
    <h3>申请成为团长</h3>
    <el-form :model="form" label-width="100px">
      <el-form-item label="选择社区">
        <el-select v-model="form.communityId" placeholder="请选择社区">
          <el-option v-for="c in communities" :key="c.id" :label="c.name" :value="c.id"/>
        </el-select>
      </el-form-item>
      <el-button type="primary" @click="apply">提交申请</el-button>
    </el-form>
  </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import api from '@/services/api';

const communities = ref([]);
const form = ref({ communityId: null });

onMounted(async () => {
  communities.value = (await api.get('/communities')).data;
});

async function apply() {
  await api.post('/leader/apply', form.value);
  ElMessage.success('申请已提交');
}
</script>
