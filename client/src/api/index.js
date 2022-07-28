import axios from 'axios';

const API = axios.create({
	baseURL: process.env.REACT_APP_MOCK_API_URI,
	withCredentials: true,
});

export default API;
