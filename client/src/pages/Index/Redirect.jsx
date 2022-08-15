import React, { useEffect } from 'react';
import { useNavigate, useSearchParams, useLocation } from 'react-router-dom';

import Loading from '../../components/Common/Loading/Loading';
import * as authApi from '../../api/auth';
import { useUserDispatch } from '../../contexts/User/UserContext';
import { setLogin, setSignUp } from '../../contexts/User/UserTypes';
import { onLogin } from '../../utils/auth';

const Redirect = () => {
	const [searchParams] = useSearchParams();
	const location = useLocation();
	const navigate = useNavigate();
	const kakaoToken = searchParams.get('code');
	const googleToken = new URLSearchParams(location.hash).get('id_token');
	const dispatch = useUserDispatch();

	useEffect(() => {
		const login = async () => {
			if (!kakaoToken && !googleToken) {
				alert('로그인에 실패했습니다');
				navigate('/');
			}
			let body = {};
			if (kakaoToken) {
				body = {
					loginType: 'Kakao',
					authCode: kakaoToken,
				};
			} else if (googleToken) {
				body = {
					loginType: 'Google',
					authCode: googleToken,
				};
			}

			try {
				const res = await authApi.login(body);
				if (res.responseType === 'signIn') {
					const { refreshToken, accessToken, userId } = res;

					onLogin(refreshToken, accessToken);
					dispatch(setLogin(userId));
					navigate('/main');
				} else if (res.responseType === 'signUp') {
					const { userId } = res;

					dispatch(setSignUp(userId));
					navigate('/join/index');
				}
			} catch (err) {
				console.log(err);
			}
		};
		login();
	}, []);

	return (
		<>
			<Loading />
			<span>로딩중</span>
		</>
	);
};
export default Redirect;
