<template>
  <div>
    <h1>Airport Distance Calculator</h1>
    <form @submit.prevent="calculateDistance">
      <div>
        <label for="from">From (IATA Code or Name):</label>
        <input type="text" v-model="from" id="from" required />
      </div>
      <div>
        <label for="to">To (IATA Code or Name):</label>
        <input type="text" v-model="to" id="to" required />
      </div>
      <button type="submit">Calculate Distance</button>
    </form>
    <div v-if="errorMessage" class="error-message">
      {{ errorMessage }}
    </div>
    <div v-if="distance !== null">
      <h2>Distance: {{ distance }} km</h2>
      <p>From: {{ fromCoordinates }}</p>
      <p>To: {{ toCoordinates }}</p>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
import { getDistance } from '@/services/api';

export default defineComponent({
  name: 'DistanceCalculator',
  setup() {
    const from = ref('');
    const to = ref('');
    const distance = ref<number | null>(null);
    const fromCoordinates = ref<number[]>([]);
    const toCoordinates = ref<number[]>([]);
    const errorMessage = ref<string | null>(null);

    const calculateDistance = async () => {
      if (from.value.toUpperCase() === 'N/A' || to.value.toUpperCase() === 'N/A') {
        errorMessage.value = "Please use the airport's name instead of 'N/A'";
        return;
      }

      try {
        const response = await getDistance(from.value, to.value);
        distance.value = response.distance;
        fromCoordinates.value = response.from;
        toCoordinates.value = response.to;
        errorMessage.value = null; // Clear any previous error message
      } catch (error) {
        if (error.response && error.response.data && error.response.data.error) {
          errorMessage.value = error.response.data.error;
        } else {
          errorMessage.value = 'An unexpected error occurred. Please try again.';
        }
      }
    };

    return {
      from,
      to,
      distance,
      fromCoordinates,
      toCoordinates,
      errorMessage,
      calculateDistance,
    };
  },
});
</script>

<style scoped>
.error-message {
  color: red;
  margin-top: 10px;
}
</style>
