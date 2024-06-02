// src/services/api.ts
import axios from 'axios';
import type { Airport } from '@/types/Airport';

const API_URL = 'http://localhost:8080/api/airports';

export const getDistance = async (from: string, to: string) => {
    const response = await axios.get(`${API_URL}/distance`, {
        params: {
            from: from.toUpperCase(),
            to: to.toUpperCase(),
        },
    });
    return response.data;
};

export const searchAirports = async (query: string) => {
    const response = await axios.get(`${API_URL}/search`, {
        params: {
            query: query.toUpperCase(),
        },
    });
    return response.data;
};

export const autocompleteAirports = async (query: string): Promise<Airport[]> => {
    const response = await axios.get(`${API_URL}/autocomplete`, {
        params: {
            query: query.toUpperCase(),
        },
    });
    return response.data;
};
