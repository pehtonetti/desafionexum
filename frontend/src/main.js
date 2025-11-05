import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';

// importa o serviço de pessoas
import pessoaService from '@/services/pessoaService';

const app = createApp(App);

// 🔧 registra o serviço globalmente
app.config.globalProperties.$pessoaService = pessoaService;

// 🔧 adiciona o roteador
app.use(router);

// 🔧 monta o app
app.mount('#app');

console.log('✅ Serviço de pessoa registrado globalmente.');
