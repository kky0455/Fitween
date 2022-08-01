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

	// todo: 유저정보 가져와야함
	const [userId, setUserId] = useState('testId');
	const fetchAndSetChatList = async () => {
		try {
			const data = await ChatApi.getChatRoomLists();
			setChatList(data);
		} catch (err) {
			console.log(err);
		}
	};

	const onMessageReceived = payload => {
		let payloadData = JSON.parse(payload.body);
		console.log(payloadData);
		fetchAndSetChatList();
	};

	const onError = err => {
		console.log(err);
		throw err;
	};
	const onConnected = () => {
		console.log('연결완료');
		stompClient.subscribe(`/topic/chat/wait/${userId}`, onMessageReceived);
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
			<TopNavigation centerContent="채팅" rightMenu={<EditButton />} />
			<Main>
				{chatList ? (
					chatList.map(chatRoom => (
						<RoomItem
							key={chatRoom.roomId}
							// todo : img 받아서 구현 필요
							imgSrc={logo}
							partnerName={chatRoom.partnerName}
							message={chatRoom.lastMessage}
							unreadCnt={chatRoom.unreadMsgCnt}
							onClickHander={() =>
								navigate('/chat/room', {
									state: { roomId: chatRoom.roomId, receiverId: chatRoom.partnerId },
								})
							}
						/>
					))
				) : (
					// todo : 로딩컴퍼넌트로 넘겨주자
					<div />
				)}
			</Main>
			<BottomNavigation />
		</>
	);
};

export default ChatList;
