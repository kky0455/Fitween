import { useState } from 'react';
import { Route, Routes as ReactRouterRoutes, Navigate } from 'react-router-dom';
import JoinIndex from './JoinIndex';
import JoinInfo from './JoinInfo';
import JoinTown from './JoinTown';
// todo : 회원가입 쪽 설계 다시 고민
const Join = () => {
	const [info, setInfo] = useState({
		gender: '여성',
		dateOfBirth: '',
		nickname: '',
		weight: '',
		height: '',
		footSize: '',
	});
	return (
		<ReactRouterRoutes>
			{/* 회원가입 안내페이지 */}
			<Route path="index" element={<JoinIndex />} />
			{/* 정보 입력 */}
			<Route
				path="info"
				element={
					<JoinInfo
						info={info}
						onChangeHandler={e =>
							setInfo(prevState => {
								return { ...prevState, [e.target.name]: e.target.value };
							})
						}
						setInfo={setInfo}
					/>
				}
			/>
			{/* 동네 입력 */}
			<Route path="town" element={<JoinTown info={info} />} />
			<Route path="*" element={<Navigate replace to="/" />} />
		</ReactRouterRoutes>
	);
};
export default Join;
