import './assets/main.css'
import './assets/styles.scss'

import router from './router';
import { createApp } from 'vue';
import PrimeVue from 'primevue/config';
import Aura from '@primevue/themes/aura';
import Button from "primevue/button";
import App from './App.vue';

const app = createApp(App);

app.use(PrimeVue, {
    theme: {
        preset: Aura
    }
});
app.use(router);
app.component('Button', Button);

app.mount('#app');
