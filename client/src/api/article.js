import API from './index';

export const getArticleList = async () => {
	const res = await API.get('/article/list');
	return res.data;
};

export const getArticleDetail = async articleId => {
	const res = await API.get(`/article/detail/${articleId}`);
	return res.data;
};

export const registArticle = async body => {
	const res = await API.post('/article/regist', body);
	return res.data;
};

export const modifyArticle = async (articleId, body) => {
	const res = await API.put(`/article/${articleId}`, body);
	return res.data;
};

export const deleteArticle = async articleId => {
	const res = await API.delete(`/article/${articleId}`);
	return res.data;
};

export const getArticleLikeList = async () => {
	const res = await API.get('/article/likelist');
	return res.data;
};

export const modifyArticleLike = async articleId => {
	const res = await API.post(`/article/like/${articleId}`);
	return res.data;
};

export const modifyArticleAvailable = async articleId => {
	const res = await API.post(`/article/available/${articleId}`);
	return res.data;
};
