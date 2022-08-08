import API from './index';
// article 리스트
export const getArticleList = async () => {
	const res = await API.get('/article/list');
	return res.data;
};

// article 상세
export const getArticleDetail = async articleId => {
	const res = await API.get(`/article/detail/${articleId}`);
	return res.data;
};

// article등록
export const registArticle = async body => {
	const res = await API.post('/article/regist', body);
	return res.data;
};

// article수정
export const modifyArticle = async (articleId, body) => {
	const res = await API.put(`/article/${articleId}`, body);
	return res.data;
};

// article삭제
export const deleteArticle = async articleId => {
	const res = await API.delete(`/article/${articleId}`);
	return res.data;
};

// 찜한 피드
export const getArticleLikeList = async () => {
	const res = await API.get('/article/likelist');
	return res.data;
};

// article 좋아요/취소
export const modifyArticleLike = async articleId => {
	const res = await API.post(`/article/like/${articleId}`);
	return res.data;
};

// article 대여 가능/불가능 수정
export const modifyArticleAvailable = async articleId => {
	const res = await API.post(`/article/available/${articleId}`);
	return res.data;
};
