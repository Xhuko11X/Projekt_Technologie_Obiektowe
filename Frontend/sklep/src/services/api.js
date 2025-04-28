import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080', // Twój backend Spring Boot
});

export default api;
