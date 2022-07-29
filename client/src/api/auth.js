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

export const signup = async body => {
	try {
		const res = await API.post('/auth/signup', body);
		return res.data;
	} catch (err) {
		throw err;
	}
};
