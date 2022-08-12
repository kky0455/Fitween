import API from './index';

// 정보 수정
export const modifyInfo = async body => {
	const res = await API.put('/users/modify/info', body);
	return res.data;
};

// 동네 수정
export const modifyRegion = async body => {
	const res = await API.put('/users/modify/region', body);
	return res.data;
};

// 팔로우
export const doFollow = async userId => {
	const res = await API.post(`/follow/${userId}`);
	return res.data;
};

// 팔로우 취소
export const cancelFollow = async userId => {
	const res = await API.delete(`/follow/${userId}`);
	return res.data;
};

// 회원 정보 가져오기
export const getUserInfo = async loginedUserId => {
	const res = await API.get(`/user/${loginedUserId}`);
	return res.data;
};

// 회원 피드 목록 가져오기
export const getUserArticleList = async () => {
	const res = await API.get('/users/articleList');
	return res.data;
};
