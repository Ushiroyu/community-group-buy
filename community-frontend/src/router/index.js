import { createRouter, createWebHistory } from 'vue-router';
import { useAuthStore } from '@/store/auth';

const routes = [
  { path: '/', name: 'Home', component: () => import('@/views/Home.vue') },
  { path: '/login', name: 'Login', component: () => import('@/views/Login.vue') },
  { path: '/register', name: 'Register', component: () => import('@/views/Register.vue') },

  // 用户模块
  { path: '/profile', name: 'Profile', component: () => import('@/views/Profile.vue'), meta: { requiresAuth: true } },
  { path: '/addresses', name: 'AddressManagement', component: () => import('@/views/AddressManagement.vue'), meta: { requiresAuth: true } },
  { path: '/orders', name: 'MyOrders', component: () => import('@/views/MyOrders.vue'), meta: { requiresAuth: true } },
  { path: '/orders/:id/confirm', name: 'ConfirmReceipt', component: () => import('@/views/ConfirmReceipt.vue'), props: true, meta: { requiresAuth: true } },

  // 商品模块
  { path: '/products', name: 'ProductList', component: () => import('@/views/ProductList.vue') },
  { path: '/products/:id', name: 'ProductDetail', component: () => import('@/views/ProductDetail.vue'), props: true },

  // 购物模块
  { path: '/cart', name: 'Cart', component: () => import('@/views/Cart.vue') },
  { path: '/checkout', name: 'Checkout', component: () => import('@/views/Checkout.vue'), meta: { requiresAuth: true } },

  // 团长模块
  { path: '/apply-leader', name: 'ApplyLeader', component: () => import('@/views/ApplyLeader.vue'), meta: { requiresAuth: true, role: 'user' } },
  { path: '/leader', name: 'LeaderDashboard', component: () => import('@/views/LeaderDashboard.vue'), meta: { requiresAuth: true, role: 'leader' } },
  { path: '/community/manage', name: 'CommunityManagement', component: () => import('@/views/CommunityManagement.vue'), meta: { requiresAuth: true, role: 'leader' } },

  // 供应商模块
  { path: '/supplier/register', name: 'SupplierRegister', component: () => import('@/views/SupplierRegister.vue') },
  { path: '/supplier', name: 'SupplierDashboard', component: () => import('@/views/SupplierDashboard.vue'), meta: { requiresAuth: true, role: 'supplier' } },

  // 管理后台
  { path: '/admin', name: 'AdminDashboard', component: () => import('@/views/AdminDashboard.vue'), meta: { requiresAuth: true, role: 'admin' } },
  { path: '/analytics', name: 'Analytics', component: () => import('@/views/Analytics.vue'), meta: { requiresAuth: true, role: 'admin' } },

  // 通配
  { path: '/:pathMatch(.*)*', redirect: { name: 'Home' } }
];

const router = createRouter({
  history: createWebHistory(),
  routes,
  scrollBehavior: () => ({ top: 0 })
});

router.beforeEach((to, from, next) => {
  const auth = useAuthStore();
  const isLoggedIn = auth.isLoggedIn;
  const role = auth.userRole;
  if (to.meta.requiresAuth && !isLoggedIn) {
    return next({ name: 'Login', query: { redirect: to.fullPath } });
  }
  if (to.meta.role && to.meta.role !== role) {
    return next({ name: 'Home' });
  }
  next();
});

export default router;
