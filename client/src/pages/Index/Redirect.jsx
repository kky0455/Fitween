import React, { useEffect } from 'react';
import { useNavigate, useSearchParams, useLocation } from 'react-router-dom';

import Loading from '../../components/Common/Loading/Loading';
import * as authApi from '../../api/auth';
import { setRefreshToken } from '../../storage/Cookie';
import API from '../../api';

const Redirect = () => {
	const [searchParams] = useSearchParams();
	const location = useLocation();
	const navigate = useNavigate();
	const kakaoToken = searchParams.get('code');
	const googleToken = new URLSearchParams(location.hash).get('id_token');

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
					token: kakaoToken,
				};
			} else if (googleToken) {
				body = {
					loginType: 'Google',
					token: googleToken,
				};
			}

			try {
				const res = await authApi.login(body);
				if (res.result === 'success') {
					setRefreshToken(res.refreshToken);
					const { accessToken } = res;
					API.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`;
					navigate('/main');
				} else if (res.result === 'needToJoin') {
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
