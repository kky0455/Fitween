import { rest } from 'msw';

// article 리스트
export const getArticleList = rest.get(
	'http://localhost:5000/article/list',
	async (req, res, ctx) =>
		res(
			ctx.json([
				{
					articleIdx: 'id1',
					title: '제목입니다',
					articleImg: '/img/asdasf',
					price: 3000,
					lendStatus: 1,
					lastUpdateTime: '2022-07-21',
					likesCount: 144,
					likesState: false,
				},
				{
					articleIdx: 'id2',
					title: '2번쨰 제목입니다',
					articleImg: '/img/asdasf',
					price: 4000,
					lendStatus: 1,
					lastUpdateTime: '2022-07-21',
					likesCount: 144,
					likesState: false,
				},
			]),
		),
);

// article 상세
export const getArticle = rest.get(
	'http://localhost:5000/article/detail/:articleId',
	async (req, res, ctx) =>
		res(
			ctx.json({
				user: { userID: 'USERID1' },
				articleIdx: 'id1',
				title: '제목입니다',
				articleImgList: ['/img/asdasf', '/img/asdasf'],
				content: '네고 사절',
				price: 3000,
				lendStatus: 1,
				lastUpdateTime: '2022-08-08 14:23:07',
				likesCount: 144,
				likesState: true,
			}),
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
					articleIdx: 'id1',
					title: '제목입니다',
					articleImg: '/img/asdasf',
					price: 3000,
					lendStatus: 1,
					lastUpdateTime: '2022-07-21',
					likesCount: 144,
				},
				{
					articleIdx: 'id2',
					title: '2번쨰 제목입니다',
					articleImg: '/img/asdasf',
					price: 4000,
					lendStatus: 1,
					lastUpdateTime: '2022-07-21',
					likesCount: 144,
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
