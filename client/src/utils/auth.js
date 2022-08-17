import { JWT_EXPIRY_TIME } from '../constants/config';
import { getRefreshToken, removeRefreshToken, setRefreshToken } from '../storage/Cookie';
import API from '../api';
import * as authApi from '../api/auth';

export const onRefresh = async () => {
	const body = {
		refreshToken: getRefreshToken(),
	};

	try {
		const { refreshToken, accessToken } = await authApi.refresh(body);
		onLoginSuccess(refreshToken, accessToken);
	} catch (err) {
		console.log(err);
	}
};

export const onLoginSuccess = (refreshToken, accessToken) => {
	setRefreshToken(refreshToken);
	API.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`;

	setTimeout(onRefresh, JWT_EXPIRY_TIME - 60 * 1000);
};

export const onLogout = async () => {
	const body = {
		refreshToken: getRefreshToken(),
	};

	try {
		await authApi.logout(body);
		removeRefreshToken();
		API.defaults.headers.common['Authorization'] = '';
	} catch (err) {
		console.log(err);
	}
};

export const onDeleteUser = async () => {
	try {
		await authApi.deleteUser();
		removeRefreshToken();
		API.defaults.headers.common['Authorization'] = '';
	} catch (err) {
		throw err;
	}
};
