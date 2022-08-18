import { JWT_EXPIRY_TIME } from '../constants/config';
import { getRefreshToken, setRefreshToken } from '../storage/Cookie';
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
		throw err;
	}
};

export const onLoginSuccess = (refreshToken, accessToken) => {
	setRefreshToken(refreshToken);
	API.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`;

	setTimeout(onRefresh, JWT_EXPIRY_TIME - 60 * 1000);
};
