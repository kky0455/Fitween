import API from './index';

// 로그인
export const login = async body => {
	const res = await API.post('/auth/login', body);
	return res.data;
};
