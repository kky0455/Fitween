import API from './index';

// 로그인
export const login = async body => {
	try {
		const res = await API.post('/auth/login', body);
		return res.data;
	} catch (err) {
		throw err;
	}
};

export const signup = async () => {
	try {
		const res = await API.get('/auth/signup');
		return res.data;
	} catch (err) {
		throw err;
	}
};
