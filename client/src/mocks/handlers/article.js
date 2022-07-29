import { rest } from 'msw';

// article 리스트
export const getArticleList = rest.get(
	'http://localhost:5000/article/list',
	async (req, res, ctx) =>
		res(
			ctx.json([
				{
					articleId: 'id1',
					articleTitle: '제목입니다',
					articleImg: '/img/asdasf',
					articlePrice: 3000,
					articleLend: 1,
					articleLastUpdateTime: '2022-07-21',
					articleLikeCount: 144,
				},
				{
					articleId: 'id2',
					articleTitle: '2번쨰 제목입니다',
					articleImg: '/img/asdasf',
					articlePrice: 4000,
					articleLend: 1,
					articleLastUpdateTime: '2022-07-21',
					articleLikeCount: 144,
				},
			]),
		),
);

// article 상세
export const getArticle = rest.get(
	'http://localhost:5000/article/:articleId',
	async (req, res, ctx) =>
		res(
			ctx.json([
				{
					articleId: 'id1',
					articleTitle: '제목입니다',
					articleImgList: ['/img/asdasf', '/img/asdasf'],
					articleContent: '네고 사절',
					articlePrice: 3000,
					articleLend: 1,
					articleLastUpdateTime: '2022-07-21 14:23:07',
					articleLikeCount: 144,
				},
				{
					articleId: 'id2',
					articleTitle: '2번쨰 제목입니다',
					articleImgList: ['/img/asdasf', '/img/asdasf'],
					articleContent: '네고 환영',
					articlePrice: 4000,
					articleLend: 1,
					articleLastUpdateTime: '2022-07-21 14:23:07',
					articleLikeCount: 144,
				},
			]),
		),
);

// article등록
export const createAricle = rest.post(
	'http://localhost:5000/article/regist',
	async (req, res, ctx) => res(ctx.json({ result: 'success' })),
);

// article수정
export const updateArticle = rest.put(
	'http://localhost:5000/article/:articleId',
	async (req, res, ctx) => res(ctx.json({ result: 'success' })),
);

// article삭제
export const deleteArticle = rest.delete(
	'http://localhost:5000/article/:articleId',
	async (req, res, ctx) => res(ctx.json({ result: 'success' })),
);

// 찜한 피드
export const getLikeList = rest.get(
	'http://localhost:5000/article/likelist',
	async (req, res, ctx) =>
		res(
			ctx.json([
				{
					articleId: 'id1',
					articleTitle: '제목입니다',
					articleImg: '/img/asdasf',
					articlePrice: 3000,
					articleLend: 1,
					articleLastUpdateTime: '2022-07-21',
					articleLikeCount: 144,
				},
				{
					articleId: 'id2',
					articleTitle: '2번쨰 제목입니다',
					articleImg: '/img/asdasf',
					articlePrice: 4000,
					articleLend: 1,
					articleLastUpdateTime: '2022-07-21',
					articleLikeCount: 144,
				},
			]),
		),
);

// article 좋아요/취소
export const likeArticle = rest.post(
	'http://localhost:5000/article/like/:articleId',
	async (req, res, ctx) => res(ctx.json({ result: 'success' })),
);

// article 대여 가능/불가능 수정
export const updateAvailable = rest.post(
	'http://localhost:5000/article/available/:articleId',
	async (req, res, ctx) => res(ctx.json({ result: 'success' })),
);
