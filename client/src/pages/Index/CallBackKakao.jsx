import React, { useEffect } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import Loading from '../../components/Common/Loading/Loading';
import * as userApi from '../../api/user';

const CallBackKakao = () => {
	const [searchParams] = useSearchParams();
	const navigate = useNavigate();
	const code = searchParams.get('code');
	useEffect(() => {
		const login = async () => {
			if (code === null || code === '') {
				alert('카카오에서 코드를 받는데 실패했습니다');
				navigate('/');
				return;
			}
			const body = {
				accessToken: code,
			};
			try {
				const res = await userApi.login(body);
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
		// login();
	}, []);
	return (
		<>
			<Loading />
			<span>카카오 로딩중</span>
		</>
	);
};
export default CallBackKakao;
