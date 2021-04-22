import Vue from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
// 在本文件导入插件 js 代码
import './plugins/axios';
import './plugins/element.js';
// import Vue2Editor from 'vue2-editor';

import * as echarts from 'echarts';

// 为了在 vue 文件中使用 echarts,这里需要设定一下 prototype 原型
Vue.prototype.$echarts = echarts;
// Vue.use(Vue2Editor);
Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app');
