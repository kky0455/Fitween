import React, { useEffect, useRef } from 'react';
import Button from '../../components/Common/Button/Button';
import Logo from '../../assets/logo.svg';
import { login } from '../../api/auth';
import { useNavigate } from 'react-router-dom';

const callKakaoLoginHandler = () => {
	window.location.href = `https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${process.env.REACT_APP_KAKAO_REST_API_KEY}&redirect_uri=${process.env.REACT_APP_CLIENT_URI}/kakao`;
};

const onGoogleSuccess = async googleUser => {
	console.log(googleUser.getAuthResponse().id_token);
	const token = googleUser.getAuthResponse().id_token;

	const body = {
		loginType: 'Google',
		token: token,
	};

	// 서버로 id_token, 구글토큰임을 알리고
	const res = await login(body);
	console.log(res);
	// 로그인 회원가입 정하기
};

const onGoogleFailure = err => {
	console.log('err', err);
};

const Index = () => {
	const googleButtonRef = useRef(null);
	const navigate = useNavigate();

	const aboutClickHandler = () => {
		navigate('/about');
	};

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
								googleButtonRef.current,
								{
									ux_mode: 'redirect',
									redirect_uri: `${process.env.REACT_APP_CLIENT_URI}/oauth/redirect`,
								},

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
				display: 'flex',
				flexDirection: 'column',
				justifyContent: 'center',
				alignItems: 'center',
				padding: '30px 30px 240px',
				width: '100%',
				height: '100%',
				position: 'relative',
				backgroundColor: '#63D8B1',
			}}
		>
			<div>
				<img
					src={Logo}
					alt=""
					style={{
						padding: '30px',
					}}
				/>
			</div>
			<h2 className="fs-17 fw-700 fc-w">너와 나의 공유 옷장</h2>
			<h1 className="fs-47 fw-900 fc-w">FITWEEN</h1>
			<Button
				type="outlined"
				label="About →"
				className="fc-w"
				onClick={aboutClickHandler}
				style={{
					display: 'inline-block',
					padding: '0 18px',
					margin: '10px 0',
					width: 'auto',
					fontFamily: 'Medium',
					border: 'none',
				}}
			/>
			<div
				style={{
					position: 'absolute',
					bottom: '100px',
					width: '80%',
				}}
			>
				<Button
					type="google"
					label="구글로 계속하기"
					ref={googleButtonRef}
					style={{
						borderRadius: '10px',
					}}
				/>
			</div>
		</div>
	);
};

export default Index;
