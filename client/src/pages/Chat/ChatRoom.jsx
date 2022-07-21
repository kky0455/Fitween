import React from 'react';
import { useParams } from 'react-router-dom';
const ChatRoom = () => {
	const { chatRoomId } = useParams();
	return <div>채팅방 : {chatRoomId}</div>;
};

export default ChatRoom;
