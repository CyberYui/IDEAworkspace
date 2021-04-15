import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Pie from '../views/Pie.vue'
import Table from '../views/Table.vue'
import Edit from '../views/Edit.vue'
import Add from '../views/Add.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  },
  {
    path:'/pie',
    name:'Pie',
    component: () => import('../views/Pie.vue')
  },
  {
    path:'/table',
    name:'Table',
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
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
