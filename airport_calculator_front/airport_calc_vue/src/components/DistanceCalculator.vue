<template>
  <div class="container">
    <div class="background">
      <div class="cloud cloud1">
        <div class="light"></div>
        <img
            src="https://images.vexels.com/media/users/3/145795/isolated/preview/05cd33059a006bf49006097af4ccba98-plane-in-flight-by-vexels.png"/>
      </div>
    </div>
    <div class="autocomplete">
      <h1>Airport Distance Calculator</h1>
      <form @submit.prevent="calculateDistance">
        <div class="input-container">
          <div class="input-field">
            <label for="from">From (IATA Code):</label>
            <input type="text" v-model="from" id="from" @input="handleInputChange('from', from)" required/>
            <div class="autocomplete-items" v-if="autocompleteFrom.length">
              <div v-for="airport in autocompleteFrom" :key="airport.iataCode" @click="selectAirport('from', airport)">
                {{ airport.name }} ({{ airport.iataCode }})
              </div>
            </div>
          </div>
          <div class="input-field">
            <label for="to">To (IATA Code):</label>
            <input type="text" v-model="to" id="to" @input="handleInputChange('to', to)" required/>
            <div class="autocomplete-items" v-if="autocompleteTo.length">
              <div v-for="airport in autocompleteTo" :key="airport.iataCode" @click="selectAirport('to', airport)">
                {{ airport.name }} ({{ airport.iataCode }})
              </div>
            </div>
          </div>
          <button type="submit" class="button-3">Calculate Distance</button>
        </div>
      </form>
      <div v-if="errorMessage" class="error-message">
        {{ errorMessage }}
      </div>
      <div v-if="distance !== null">
        <h2> The distance between {{ from }} and {{ to }} is {{ distance }} km.</h2>
      </div>
    </div>
  </div>
</template>
<script lang="ts">
import {defineComponent, ref, watch} from 'vue';
import {getDistance, autocompleteAirports} from '@/services/api';
import type {Airport} from '@/types/Airport';
//airport_calc_vue/src/assets/page.css
import '@/assets/page.css';

export default defineComponent({
  name: 'DistanceCalculator',
  setup() {
    const from = ref('');
    const to = ref('');
    const distance = ref<number | null>(null);
    const fromCoordinates = ref<number[]>([]);
    const toCoordinates = ref<number[]>([]);
    const errorMessage = ref<string | null>(null);
    const autocompleteFrom = ref<Airport[]>([]);
    const autocompleteTo = ref<Airport[]>([]);

    const fetchAutocomplete = async (field: string, query: string) => {
      if (query.length < 2) return; // Minimum length for autocomplete query
      try {
        const response = await autocompleteAirports(query);
        if (field === 'from') {
          autocompleteFrom.value = response;
        } else {
          autocompleteTo.value = response;
        }
      } catch (error) {
        console.error('Error fetching autocomplete results:', error);
      }
    };

    const handleInputChange = (field: string, query: string) => {
      fetchAutocomplete(field, query);
      distance.value = null; // Clear the distance when input changes
    };

    const selectAirport = (field: string, airport: Airport) => {
      if (field === 'from') {
        from.value = airport.iataCode;
        autocompleteFrom.value = [];
      } else {
        to.value = airport.iataCode;
        autocompleteTo.value = [];
      }
    };

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

    // Watchers to clear autocomplete lists when inputs are cleared
    watch(from, (newValue) => {
      if (newValue === '') {
        autocompleteFrom.value = [];
      }
    });

    watch(to, (newValue) => {
      if (newValue === '') {
        autocompleteTo.value = [];
      }
    });

    return {
      from,
      to,
      distance,
      fromCoordinates,
      toCoordinates,
      errorMessage,
      calculateDistance,
      autocompleteFrom,
      autocompleteTo,
      handleInputChange,
      selectAirport,
    };
  },
});
</script>

