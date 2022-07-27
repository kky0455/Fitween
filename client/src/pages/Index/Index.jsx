import React, { useEffect, useRef } from 'react';
import Button from '../../components/Common/Button/Button';
import Logo from '../../assets/FitweenLogoBg.png';
import { login } from '../../api/auth';

const callKakaoLoginHandler = () => {
	window.location.href = `https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${process.env.REACT_APP_KAKAO_REST_API_KEY}&redirect_uri=${process.env.REACT_APP_KAKAO_REDIRECT_URI}`;
};
const onGoogleSuccess = async googleUser => {
	console.log(googleUser.getAuthResponse().id_token);
	const token = googleUser.getAuthResponse().id_token;

	const body = {
		loginType: 'Google',
		token: token,
	};

	// 서버로 id_toekn, 구글토큰임을 알리고
	const res = await login(body);
	console.log(res);
	// 로그인 회원가입 정하기
};
const onGoogleFailure = err => {
	alert('구글 로그인에 실패하였습니다');
	console.log('err', err);
};
const Index = () => {
	const googleButton = useRef(null);
	useEffect(() => {
		const loadScript = src =>
			new Promise((resolve, reject) => {
				if (document.querySelector(`script[src="${src}"]`)) return resolve();
				const script = document.createElement('script');
				script.src = src;
				script.onload = () => resolve();
				script.onerror = err => reject(err);
				document.body.appendChild(script);
			});
		const src = 'https://apis.google.com/js/platform.js?onload=init';
		loadScript(src)
			.then(() => {
				/*global gapi*/
				gapi.load('client:auth2', () => {
					gapi.client
						.init({
							client_id: process.env.REACT_APP_GOOGLE_CLIENT_KEY,
							scope: 'profile email',
							plugin_name: 'chat',
						})
						.then(() => {
							const gauth = gapi.auth2.getAuthInstance();
							gauth.attachClickHandler(
								googleButton.current,
								{ ux_mode: 'redirect', redirect_uri: 'http://localhost:3000/oauth/redirect' },

								onGoogleSuccess,
								onGoogleFailure,
							);
						});
				});
			})
			.catch(console.error);
	}, []);
	return (
		<div
			className="wrapper"
			style={{
				padding: '30px',
				width: '100%',
				height: '100%',
				position: 'relative',
			}}
		>
			<div>
				<img src={Logo} alt="" />
			</div>
			<h2 className="fs-17 fw-700">너와 나의 공유 옷장</h2>
			<h1 className="fs-47 fw-900 fc-g100">FITWEEN</h1>
			<div style={{ marginTop: '100px' }}>
				<Button type="kakao" label="카카오톡으로 계속하기" onClick={callKakaoLoginHandler} />
				<Button type="google" label="구글로 계속하기" forwardedRef={googleButton} />
			</div>
		</div>
	);
};

export default Index;
