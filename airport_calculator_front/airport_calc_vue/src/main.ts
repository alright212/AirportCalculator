import { createApp } from 'vue';
import { createPinia } from 'pinia';
import App from './App.vue';
import router from './router'; // Ensure this import matches the actual file location

const app = createApp(App);

app.use(createPinia());
app.use(router);

app.mount('#app');
