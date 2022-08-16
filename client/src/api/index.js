import axios from 'axios';

const API = axios.create({
	baseURL: process.env.REACT_APP_API_URI,
	withCredentials: true,
	timeout: 1,
});

export default API;
