<template>
  <el-card>
    <h3>销售数据分析</h3>
    <!-- 可以用 ECharts 或者 Element Plus 的图表库绘制 -->
    <div id="chart" style="height:400px;"></div>
  </el-card>
</template>

<script setup>
import { onMounted } from 'vue';
import api from '@/services/api';

onMounted(async () => {
  const data = (await api.get('/analytics/sales')).data;
  // 假设 data = [{ date: '2025-06-01', total: 100 }, …]
  const option = {
    xAxis: { type: 'category', data: data.map(d => d.date) },
    yAxis: { type: 'value' },
    series: [{ data: data.map(d => d.total), type: 'line' }]
  };
  const chart = echarts.init(document.getElementById('chart'));
  chart.setOption(option);
});
</script>
