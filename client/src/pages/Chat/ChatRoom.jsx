import React, { useEffect, useRef, useState } from 'react';
/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';
import { useLocation, useNavigate } from 'react-router-dom';
import { over } from 'stompjs';
import SockJS from 'sockjs-client';
import { v4 as uuid } from 'uuid';

import TopNavigation from '../../components/Common/TopNavigation/TopNavigation';
import { ReactComponent as SendIcon } from '../../assets/send.svg';
import colors from '../../constants/colors';
import commonStyle from '../../constants/commonStyle';
import { useGlobalContext } from '../../contexts/GlobalContext';
import Main from '../../components/Common/Main/Main';
import Message from '../../components/Chat/Message';
import Date from '../../components/Chat/Date';
import * as ChatApi from '../../api/chat';
import { useUserState } from '../../contexts/User/UserContext';
import { getDay } from '../../utils/getDate';

let stompClient = null;
const ChatRoom = () => {
	const inputRef = useRef(null);
	const [message, setMessage] = useState('');
	const { state } = useLocation();
	const [roomId, setRoomId] = useState(state.roomId);
	const [receiverId, setReceiverId] = useState(state.receiverId);
	const { loginedUserId } = useUserState();
	const [chats, setChats] = useState([]);
	const navigate = useNavigate();
	const scrollRef = useRef(null);
	const sendChatHandler = async () => {
		if (message === '') return;
		const chatMessage = {
			senderId: loginedUserId,
			receiverId: receiverId,
			roomId: roomId,
			message,
		};
		if (stompClient) {
			if (roomId === -1) {
				const ret = await ChatApi.makeRoom(receiverId, loginedUserId);
				console.log(ret);
				setRoomId(ret);
				chatMessage.roomId = ret;
			}
			stompClient.send(`/app/chat/message`, {}, JSON.stringify(chatMessage));
		}
		setMessage('');
		inputRef.current.focus();
	};
	const scrollToBottom = () => {
		scrollRef.current.scrollIntoView({ behavior: 'smooth', block: 'end', inline: 'nearest' });
	};
	const onMessageReceived = async payload => {
		let payloadData = JSON.parse(payload.body);
		await ChatApi.doRead(roomId, loginedUserId, receiverId);
		setChats(prev => [...prev, payloadData]);
	};

	const onError = err => {
		console.log(err);
		throw err;
	};
	const onConnected = () => {
		if (stompClient.connected) {
			stompClient.subscribe(`/topic/chat/room/${roomId}`, onMessageReceived);
		}
	};

	const { setHasBottom } = useGlobalContext();
	useEffect(() => {
		setHasBottom(true);
		return () => {
			setHasBottom(false);
		};
	}, []);
	useEffect(() => {
		scrollToBottom();
	}, [chats]);

	// 소켓연결
	useEffect(() => {
		let Sock = new SockJS(process.env.REACT_APP_SOCKET_URI);
		stompClient = over(Sock);
		stompClient.debug = null;
		stompClient.connect({}, onConnected, onError);
		const doRead = async () => {
			const ret = await ChatApi.doRead(roomId, loginedUserId, receiverId);
		};
		doRead();
		const fetchChatLogs = async () => {
			try {
				const data = await ChatApi.getChagLogs(roomId);
				setChats(data);
			} catch (err) {
				console.log(err);
			}
		};
		const findRoom = async () => {
			try {
				const roomId = await ChatApi.findRoom(receiverId, loginedUserId);
				setRoomId(roomId);
			} catch (err) {
				console.log(err);
			}
		};
		if (roomId && roomId !== -1) {
			fetchChatLogs();
		} else {
			findRoom();
		}
		return () => {
			if (stompClient.connected) stompClient.disconnect();
		};
	}, [roomId]);
	let lastDate = '';
	return (
		<>
			<TopNavigation backClick onBackClick={() => navigate(-1)} centerContent={state.nickname} />
			<Main>
				{chats.map(chat => {
					const DATETIME = chat.senddatetime.replace('T', ' ').split(' ')[0];
					const DATE = chat.senddatetime.replace('T', ' ').split(' ')[1].slice(0, 5);
					if (DATETIME === lastDate)
						return (
							<Message
								key={uuid()}
								message={chat.message}
								isMine={chat.senderId === loginedUserId}
								sendTime={DATE}
								isRead={chat.isRead}
							/>
						);
					else {
						lastDate = DATETIME;
						return (
							<>
								<Date date={DATETIME + ' ' + getDay(chat.senddatetime)} key={uuid()} />
								<Message
									key={uuid()}
									message={chat.message}
									isMine={chat.senderId === loginedUserId}
									sendTime={DATE}
									isRead={chat.isRead}
								/>
							</>
						);
					}
				})}
				<div ref={scrollRef} />
			</Main>
			<div
				className="input-wrapper"
				css={css`
					position: fixed;
					bottom: 0;
					width: 100%;
					max-width: 1200px;
					height: ${commonStyle.bottomHeaderHeight};
					box-shadow: 0 -0.2px ${colors.text};
					background-color: ${colors.white};
					display: flex;
					justify-content: center;
					align-items: center;
				`}
			>
				<input
					ref={inputRef}
					value={message}
					onChange={e => setMessage(e.target.value)}
					onKeyDown={e => {
						if (e.keyCode === 13) sendChatHandler();
					}}
					type="text"
					placeholder="메시지를 입력하세요"
					css={css`
						width: 90%;
						padding: 10px 45px 10px 10px;
						border: 2px solid ${colors.text};
						font-size: 16px;
						font-family: 'Regular';
						border-radius: 31px;
						&:active,
						&:focus {
							outline: none;
						}
					`}
				/>
				<div
					css={css`
						position: absolute;
						right: 5%;
						padding: 10px;
						display: flex;
						justify-content: center;
						align-items: center;
					`}
				>
					<SendIcon onClick={sendChatHandler} width="28" height="28" fill={colors.text} />
				</div>
			</div>
		</>
	);
};

export default ChatRoom;
