import { rest } from 'msw';

export const handlers = [
	// user

	// 로그인
	rest.post('http://localhost:5000/user/login', async (req, res, ctx) => {
		// 카카오에서 받은 인가코드 넘겨줌
		const { accessToken } = await req.json();
		if (typeof accessToken === 'string') {
			return res(
				ctx.json({
					result: 'success',
					token: 'jkshfjkldASDJKHASFJKasd123asFJKAHSd',
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
	}),

	//회원가입
	rest.post('http://localhost:5000/user/regist', async (req, res, ctx) => {
		return res.json(
			ctx.json({
				result: 'success',
			}),
		);
	}),

	//정보수정
	rest.put('http://localhost:5000/user/modify/info', async (req, res, ctx) => {
		return res.json(
			ctx.json({
				result: 'success',
			}),
		);
	}),

	//동네수정
	rest.put('http://localhost:5000/user/modify/region', async (req, res, ctx) => {
		return res.json(
			ctx.json({
				result: 'success',
			}),
		);
	}),

	// 유저 정보 받아오기
	rest.get('http://localhost:5000/user/:userId', async (req, res, ctx) => {
		return res(
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
		);
	}),

	// 특정 유저 follow/취소
	rest.post('http://localhost:5000/user/follow', async (req, res, ctx) => {
		const { userId } = await req.json();
		return res(ctx.json({ result: 'success' }));
	}),

	// 특정 유저가 작성한 article 리스트
	rest.get('http://localhost:5000/user/articleList', async (req, res, ctx) => {
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
	}),

	// article
	// article 리스트
	rest.get('http://localhost:5000/article/list', async (req, res, ctx) => {
		return res(
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
		);
	}),

	// article 상세
	rest.get('http://localhost:5000/article/:articleId', async (req, res, ctx) => {
		return res(
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
		);
	}),

	// article등록
	rest.post('http://localhost:5000/article/regist', async (req, res, ctx) => {
		return res(ctx.json({ result: 'success' }));
	}),
	// article수정
	rest.put('http://localhost:5000/article/:articleId', async (req, res, ctx) => {
		return res(ctx.json({ result: 'success' }));
	}),
	// article삭제
	rest.delete('http://localhost:5000/article/:articleId', async (req, res, ctx) => {
		return res(ctx.json({ result: 'success' }));
	}),

	// 찜한 피드
	rest.get('http://localhost:5000/article/likelist', async (req, res, ctx) => {
		return res(
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
		);
	}),

	// article 좋아요/취소
	rest.post('http://localhost:5000/article/like/:articleId', async (req, res, ctx) => {
		return res(ctx.json({ result: 'success' }));
	}),
	// article 대여 가능/불가능 수정
	rest.post('http://localhost:5000/article/available/:articleId', async (req, res, ctx) => {
		return res(ctx.json({ result: 'success' }));
	}),

	//chat
	// 채팅방리스트
	rest.get('http://localhost:5000/chat/list', async (req, res, ctx) => {
		return res(
			ctx.json([
				{
					chatRoomId: 'chatRoomId1',
					chatPartnerId: 'userId123',
					chatLastMsg: '감사합니다',
					chatLastTime: '2020-12-31 14:23:07',
					chatUnreadMsgCnt: 10,
				},
				{
					chatRoomId: 'chatRoomId2',
					chatPartnerId: 'userId123',
					chatLastMsg: 'ㅎㅇ',
					chatLastTime: '2020-12-31 14:23:07',
					chatUnreadMsgCnt: 10,
				},
			]),
		);
	}),
];
