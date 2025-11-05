import { createRouter, createWebHistory } from 'vue-router';
import Home from '../views/Home.vue';
import PessoasView from '../views/PessoasView.vue';

export default createRouter({ history:createWebHistory(), routes:[{path:'/', component:Home},
                                {path:'/pessoas', component:PessoasView}] });
