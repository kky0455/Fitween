import { JWT_EXPIRY_TIME } from '../constants/config';
import { setRefreshToken } from '../storage/Cookie';
import API from '../api';
import * as authApi from '../api/auth';

export const onRefresh = async () => {
	const refreshInLocalStorage = {
		refreshToken: localStorage.getItem('refreshToken'),
	};
	const { refreshToken, accessToken } = await authApi.refresh(
		JSON.stringify(refreshInLocalStorage),
	);

	onLogin(refreshToken, accessToken);
};

export const onLogin = (refreshToken, accessToken) => {
	setRefreshToken(refreshToken);
	localStorage.setItem('refreshToken', refreshToken);
	API.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`;

	setTimeout(onRefresh(), JWT_EXPIRY_TIME - 60 * 1000);
};
