import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'

// 设定使用 vueRouter
Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
    // 配置路径
    // 路径的 name 需要和 .vue 文件的文件名对应即可
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  },
  {
    path: '/chart',
    name: 'Chart',
    component: () => import('../views/Chart.vue')
  },
  {
    path: '/table',
    name: 'Table',
    component: () => import('../views/Table.vue')
  },
  {
    path:'/edit',
    name:'Edit',
    component: () => import('../views/Edit.vue')
  },
  {
    path:'/add',
    name:'Add',
    component: () => import('../views/Add.vue')
  },
  // 下面为测试 markdown 编辑器用的测试路径
  {
    path:'/edittest',
    name:'Edittest',
    component: ()=> import( '../views/Edittest.vue')
  },
  {
    path:'/videotest',
    name:'Videotest',
    component: ()=>import('../views/Videotest.vue')
  },
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
});

export default router
