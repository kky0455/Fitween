import API from './index';

export const findRoom = async receiverId => {
	try {
		const res = await API.get(`/chat/findRoom/${receiverId}`);
		return res.data;
	} catch (err) {
		throw err;
	}
};

export const getChagLogs = async roomId => {
	try {
		const res = await API.get(`/chat/log/${roomId}`);
		return res.data;
	} catch (err) {
		throw err;
	}
};

export const getChatRoomLists = async () => {
	try {
		const res = await API.get(`/chat/roomList/`);
		return res.data;
	} catch (err) {
		throw err;
	}
};
