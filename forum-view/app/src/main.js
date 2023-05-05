import Vue from 'vue';
import App from './App.vue';
import router from './router';
import ElementUI from 'element-ui';
import store from './store/index.js';
import 'element-ui/lib/theme-chalk/index.css';

Vue.use(ElementUI)
Vue.config.productionTip = false







new Vue({
  router,
  store,
  render: h => h(App),
  components: {

  }
}).$mount('#app')
