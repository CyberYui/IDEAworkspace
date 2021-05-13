import Vue from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
// 在本文件导入插件 js 代码
import './plugins/axios';
import './plugins/element.js';

// 导入 markdown 编辑器
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'

import * as echarts from 'echarts';

// 为了在 vue 文件中使用 echarts,这里需要设定一下 prototype 原型
Vue.prototype.$echarts = echarts;
// Vue.use(Vue2Editor);
Vue.config.productionTip = false;
Vue.use(mavonEditor)

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app');
