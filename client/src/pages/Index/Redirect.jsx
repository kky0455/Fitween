import React, { useEffect } from 'react';
import { useNavigate, useSearchParams, useLocation } from 'react-router-dom';
import Loading from '../../components/Common/Loading/Loading';
import * as authApi from '../../api/auth';

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
					// todo: 클라이언트 로그인 로직 구현 필요
					navigate('/main');
				} else if (res.result === 'needToJoin') {
					// todo :약관 동의 및 회원 가입 진행 페이지로 진입
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
