// src/services/api.ts
import axios from 'axios';

const API_URL = 'http://localhost:8080/api/airports';



export const getDistance = async (from: string, to: string) => {
    const response = await axios.get(`${API_URL}/distance`, {
        params: {
            from: from.toUpperCase(),  // Convert to uppercase
            to: to.toUpperCase(),      // Convert to uppercase
        },
    });
    return response.data;
};

export const searchAirports = async (query: string) => {
    const response = await axios.get(`${API_URL}/search`, {
        params: {
            query: query.toUpperCase(),  // Convert to uppercase
        },
    });
    return response.data;
};
