import { rest } from 'msw';
// 로그인
export const login = rest.post('http://localhost:5000/auth/login', async (req, res, ctx) => {
	// 카카오에서 받은 인가코드 넘겨줌
	const { accessToken } = await req.json();
	if (typeof accessToken === 'string') {
		return res(
			ctx.json({
				result: 'success',
				refreshToken: 'dfsa;difjsifjslkfjeifsjofj',
				accessToken: 'jkshfjkldASDJKHASFJKasd123asFJKAHSd',
				userInfo: {
					userId: 'id123',
					userNickname: '유저별명',
					userImg: '/user/fjkhsdjkfhkljasdf',
					userEmail: 'wlsgkq123@gmail.com',
					userAge: 23,
					userGender: 1,
					userName: '유저이름',
					userHeight: 175,
					userWeight: 70,
					userFootSize: 255,
					userRegion: '광주광역시 광산 장덕동',
					userFollowedCnt: 123,
					userFollowingCnt: 123,
					userArticleCnt: 10,
				},
			}),
		);
	} else {
		return res(ctx.json({ result: 'needToJoin' }));
	}
});

//회원가입
export const signup = rest.post('http://localhost:5000/auth/signup', async (req, res, ctx) =>
	res(
		ctx.json({
			refreshToken: 'dfsa;difjsifjslkfjeifsjofj',
			accessToken: 'jkshfjkldASDJKHASFJKasd123asFJKAHSd',
		}),
	),
);

//정보수정
export const updateInfo = rest.put(
	'http://localhost:5000/user/modify/info',
	async (req, res, ctx) =>
		res.json(
			ctx.json({
				result: 'success',
			}),
		),
);

//동네수정
export const updateRegion = rest.put(
	'http://localhost:5000/user/modify/region',
	async (req, res, ctx) =>
		res.json(
			ctx.json({
				result: 'success',
			}),
		),
);

// 유저 정보 받아오기
export const getUserInfo = rest.get('http://localhost:5000/user/:userId', async (req, res, ctx) =>
	res(
		ctx.json({
			userInfo: {
				userId: 'id123',
				userNickname: '유져별명',
				userImg: '/user/fjkhsdjkfhkljasdf',
				userEmail: 'wlsgkq123@gmail.com',
				userAge: 23,
				userGender: 1,
				userName: '유저이름',
				userHeight: 175,
				userWeight: 70,
				userFootSize: 255,
				userRegion: '광주광역시 광산 장덕동',
				userFollowedCnt: 123,
				userFollowingCnt: 123,
				userArticleCnt: 10,
			},
		}),
	),
);

// 특정 유저 follow/취소
export const followUser = rest.post('http://localhost:5000/user/follow', async (req, res, ctx) => {
	const { userId } = await req.json();
	return res(ctx.json({ result: 'success' }));
});

// 특정 유저가 작성한 article 리스트
export const getArticleList = rest.get(
	'http://localhost:5000/user/articleList',
	async (req, res, ctx) => {
		const { userId } = await req.json();
		return res(
			ctx.json([
				{
					articleId: 'id1',
					articleTitle: '제목입니다',
					articleImgList: ['/img/asdasf', '/img/asdasf'],
					articlePrice: 3000,
					articleContent: '네고 사절',
					articleLend: 1,
					articleLastUpdateTime: '2022-07-21',
					articleLikeCount: 144,
				},
				{
					articleId: 'id2',
					articleTitle: '2번쨰 제목입니다',
					articleImgList: ['/img/asdasf', '/img/asdasf'],
					articlePrice: 4000,
					articleContent: '네고 사절ㅁㄴㅇㅁㄴㅇㅁㄴㅇ',
					articleLend: 1,
					articleLastUpdateTime: '2022-07-21',
					articleLikeCount: 144,
				},
			]),
		);
	},
);
