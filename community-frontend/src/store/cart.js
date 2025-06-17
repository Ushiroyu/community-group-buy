import { defineStore } from 'pinia';
import api from '@/services/api';
import { useAuthStore } from '@/store/auth';

export const useCartStore = defineStore('cart', {
  state: () => ({
    items: JSON.parse(localStorage.getItem('cart') || '[]')
  }),
  actions: {
    addToCart(product, qty = 1) {
      const exist = this.items.find(i => i.product.id === product.id);
      if (exist) {
        exist.quantity += qty;
      } else {
        this.items.push({ product, quantity: qty });
      }
      localStorage.setItem('cart', JSON.stringify(this.items));
    },
    removeFromCart(productId) {
      this.items = this.items.filter(i => i.product.id !== productId);
      localStorage.setItem('cart', JSON.stringify(this.items));
    },
    async checkout(addressId) {
      const auth = useAuthStore();
      const orderItems = this.items.map(i => ({
        productId: i.product.id,
        quantity: i.quantity,
        unitPrice: i.product.price
      }));
      const payload = {
        userId: auth.user.id,
        leaderId: null,
        supplierId: null,
        totalAmount: orderItems.reduce((sum, i) => sum + i.quantity * i.unitPrice, 0),
        items: orderItems,
        addressId
      };
      const res = await api.post('/orders', payload);
      this.items = [];
      localStorage.removeItem('cart');
      return res.data;
    }
  },
  getters: {
    totalCount: state => state.items.reduce((sum, i) => sum + i.quantity, 0),
    totalAmount: state => state.items.reduce((sum, i) => sum + i.quantity * i.product.price, 0)
  }
});
