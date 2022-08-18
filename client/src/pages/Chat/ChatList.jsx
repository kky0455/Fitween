import React, { useEffect, useState } from 'react';
/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';
import SockJS from 'sockjs-client';
import { over } from 'stompjs';

import TopNavigation from '../../components/Common/TopNavigation/TopNavigation';
import BottomNavigation from '../../components/Common/BottomNavigation/BottomNavigation';
import colors from '../../constants/colors';
import logo from '../../assets/logo2.png';
import Main from '../../components/Common/Main/Main';
import RoomItem from '../../components/Chat/RoomItem';
import { useNavigate } from 'react-router-dom';
import * as ChatApi from '../../api/chat';
import { useUserState } from '../../contexts/User/UserContext';

const EditButton = () => {
	return (
		<button
			css={css`
				background-color: ${colors.background};
				color: ${colors.green100};
				border: none;
				font-size: 16px;
				font-family: 'Bold';
				padding: 20px 15px 0 10px;
			`}
		>
			Edit
		</button>
	);
};

let stompClient = null;
const ChatList = () => {
	const navigate = useNavigate();
	const [chatList, setChatList] = useState(null);

	const { loginedUserId } = useUserState();
	const fetchAndSetChatList = async () => {
		try {
			const data = await ChatApi.getChatRoomLists(loginedUserId);
			setChatList(data);
		} catch (err) {
			console.log(err);
		}
	};

	const onMessageReceived = () => {
		fetchAndSetChatList();
	};

	const onError = err => {
		console.log(err);
		throw err;
	};
	const onConnected = () => {
		console.log('연결완료');
		stompClient.debug = null;
		stompClient.subscribe(`/topic/chat/wait/${loginedUserId}`, onMessageReceived);
	};

	useEffect(() => {
		const fetch = async () => fetchAndSetChatList();
		fetch();
	}, []);
	// 소켓연결
	useEffect(() => {
		let Sock = new SockJS(process.env.REACT_APP_SOCKET_URI);
		stompClient = over(Sock);
		stompClient.connect({}, onConnected, onError);

		return () => {
			if (stompClient.connected) stompClient.disconnect();
		};
	}, []);
	return (
		<>
			<TopNavigation centerContent="채팅" />
			<Main>
				{chatList ? (
					chatList.map(chatRoom => (
						<RoomItem
							key={chatRoom.roomId}
							imgSrc={logo}
							partnerName={
								chatRoom.user1Id === loginedUserId ? chatRoom.user2Nickname : chatRoom.user1Nickname
							}
							message={chatRoom.lastChat}
							unreadCnt={chatRoom.lastSenderId === loginedUserId ? 0 : chatRoom.notReadCount}
							onClickHander={() =>
								navigate('/chat/room', {
									state: {
										roomId: chatRoom.roomId,
										receiverId:
											chatRoom.user1Id === loginedUserId ? chatRoom.user2Id : chatRoom.user1Id,
										nickname:
											chatRoom.user1Id === loginedUserId
												? chatRoom.user2Nickname
												: chatRoom.user1Nickname,
									},
								})
							}
						/>
					))
				) : (
					<div />
				)}
			</Main>
			<BottomNavigation />
		</>
	);
};

export default ChatList;
