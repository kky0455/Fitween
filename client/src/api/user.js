import API from './index';

// 로그인
export const login = async body => {
	const res = await API.post('/user/login', body);
	return res.data;
};

// 정보 수정
export const modifyInfo = async body => {
	const res = await API.put('/user/modify/info', body);
	return res.data;
};

// 동네 수정
export const modifyRegion = async body => {
	const res = await API.put('/user/modify/region', body);
	return res.data;
};

// 팔로우/취소
export const modifyFollow = async () => {
	const res = await API.post('/user/follow');
	return res.data;
};

// 회원 정보 가져오기
export const getUserInfo = async userId => {
	const res = await API.get(`/user/${userId}`);
	return res.data;
};

// 회원 피드 목록 가져오기
export const getUserArticleList = async () => {
	const res = await API.get('/user/articleList');
	return res.data;
};
