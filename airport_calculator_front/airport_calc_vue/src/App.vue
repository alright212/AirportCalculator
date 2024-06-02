<template>
  <div id="app">
    <DistanceCalculator />
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import DistanceCalculator from './components/DistanceCalculator.vue';

export default defineComponent({
  name: 'App',
  components: {
    DistanceCalculator,
  },
});
</script>

<style>
/* Add any global styles here */
</style>
