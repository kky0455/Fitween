import API from './index';

export const findRoom = async (receiverId, senderId) => {
	try {
		const res = await API.get(`/chat/findRoom?receiverId=${receiverId}&senderId=${senderId}`);
		return res.data;
	} catch (err) {
		throw err;
	}
};

export const makeRoom = async (receiverId, senderId) => {
	try {
		const res = await API.get(`/chat/makeRoom?receiverId=${receiverId}&senderId=${senderId}`);
		return res.data;
	} catch (err) {
		throw err;
	}
};

export const getChagLogs = async roomId => {
	try {
		const res = await API.get(`/chat/log?roomId=${roomId}`);
		return res.data;
	} catch (err) {
		throw err;
	}
};

export const getChatRoomLists = async userId => {
	try {
		const res = await API.get(`/chat/roomList?userId=${userId}`);
		return res.data;
	} catch (err) {
		throw err;
	}
};

export const doRead = async (roomId, userId, senderId) => {
	const body = {
		roomId,
		userId,
		senderId,
	};
	try {
		const res = await API.put(`/chat/doRead`, body);
	} catch (err) {
		throw err;
	}
};
