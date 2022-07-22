import React from 'react';
import Button from '../../components/Common/Button/Button';
const callKakaoLoginHandler = () => {
	window.location.href = `https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${process.env.REACT_APP_KAKAO_REST_API_KEY}&redirect_uri=${process.env.REACT_APP_KAKAO_REDIRECT_URI}`;
};

const Index = () => {
	return <Button type="kakao" label="카카오톡으로 계속하기" onClick={callKakaoLoginHandler} />;
};

export default Index;
