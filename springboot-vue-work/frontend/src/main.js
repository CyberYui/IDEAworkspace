import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import './plugins/axios'
import './plugins/element.js'
import * as echarts from 'echarts';

// 为了在 vue 文件中使用 echarts,这里需要设定一下 prototype 原型
Vue.prototype.$echarts = echarts;
Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app');
