import React from 'react';
import Button from '../../components/Common/Button/Button';
import Logo from '../../assets/FitweenLogoBg.png';

const callKakaoLoginHandler = () => {
	window.location.href = `https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${process.env.REACT_APP_KAKAO_REST_API_KEY}&redirect_uri=${process.env.REACT_APP_KAKAO_REDIRECT_URI}`;
};

const Index = () => {
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
			<Button
				style={{
					position: 'absolute',
					bottom: 0,
					width: 'calc(100% - 60px)',
					marginBottom: 50,
				}}
				type="kakao"
				label="카카오톡으로 계속하기"
				onClick={callKakaoLoginHandler}
			/>
		</div>
	);
};

export default Index;
