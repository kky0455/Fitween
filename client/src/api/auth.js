import API from './index';

export const login = async body => {
	try {
		const res = await API.get('/auth/login', { params: body });
		return res.data;
	} catch (err) {
		throw err;
	}
};

export const signup = async body => {
	try {
		const res = await API.post('/auth/signup', body);
		return res.data;
	} catch (err) {
		throw err;
	}
};
