import { rest } from 'msw';

// 채팅방리스트
export const getChatList = rest.get('http://localhost:5000/chat/list', async (req, res, ctx) =>
	res(
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
	),
);
