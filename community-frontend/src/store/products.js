import { defineStore } from 'pinia';
import api from '@/services/api';

export const useProductsStore = defineStore('products', {
  state: () => ({
    list: [],
    detail: null
  }),
  actions: {
    async fetchList(params = {}) {
      const res = await api.get('/products', { params });
      this.list = res.data;
    },
    async fetchDetail(id) {
      const res = await api.get(`/products/${id}`);
      this.detail = res.data;
    }
  }
});
