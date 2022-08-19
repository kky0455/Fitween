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

export const logout = async body => {
	try {
		const res = await API.post('/auth/logout', body);
		return res;
	} catch (err) {
		throw err;
	}
};

export const duplicationCheck = async nickname => {
	try {
		const res = await API.get('/auth/duplicationcheck', { params: { nickname } });
		return res.data;
	} catch (err) {
		throw err;
	}
};

export const refresh = async body => {
	try {
		const res = await API.post('/token/refresh', body);
		return res.data;
	} catch (err) {
		throw err;
	}
};

export const deleteUser = async () => {
	try {
		const res = await API.delete('/users');
		return res.data;
	} catch (err) {
		throw err;
	}
};
