import React from 'react';
import { useNavigate } from 'react-router-dom';
import Button from '../../components/Common/Button/Button';
import { useUserState } from '../../context/User/UserContext';

const JoinIndex = () => {
	const naviagte = useNavigate();
	const { loginedUserId } = useUserState();

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
			<span>등록되지 않은 회원입니다.</span>
			<Button
				label="동의하고 회원가입하기"
				onClick={() => {
					naviagte('/join/info');
				}}
			/>
		</div>
	);
};

export default JoinIndex;
