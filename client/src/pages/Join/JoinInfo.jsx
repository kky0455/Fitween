import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

import Button from '../../components/Common/Button/Button';
import Input from '../../components/Common/Input/Input';
import TopNavigation from '../../components/Common/TopNavigation/TopNavigation';

const JoinInfo = ({ info, onChangeHandler }) => {
	const [readyState, setReadyState] = useState(false);
	const navigate = useNavigate();
	// toDo : 별명 중복검사 필요
	const check = info => {
		return info.nickname &&
			info.nickname.trim().length > 0 &&
			info.height &&
			info.height.trim().length > 0 &&
			info.weight &&
			info.weight.trim().length > 0 &&
			info.footSize &&
			info.footSize.trim().length > 0
			? true
			: false;
	};
	useEffect(() => {
		if (check(info)) {
			setReadyState(true);
		} else {
			setReadyState(false);
		}
	}, [info]);
	return (
		<>
			<TopNavigation
				backClick
				onBackClick={() => {
					navigate(-1);
				}}
			/>
			<div
				className="wrapper"
				style={{
					padding: '30px',
					width: '100%',
					height: '100%',
					position: 'relative',
				}}
			>
				<h2 className="fs-24 fw-700" style={{ textAlign: 'center', paddingBottom: 30 }}>
					추가 정보 입력
				</h2>
				<Input
					placeholder="별명"
					errMsg="중복된 별명입니다"
					type="text"
					onChange={onChangeHandler}
					value={info.nickname}
					name="nickname"
				/>
				<Input
					placeholder="키"
					unit="cm"
					type="number"
					name="height"
					onChange={onChangeHandler}
					value={info.height}
				/>
				<Input
					placeholder="몸무게"
					unit="kg"
					type="number"
					name="weight"
					onChange={onChangeHandler}
					value={info.weight}
				/>
				<Input
					placeholder="발사이즈"
					unit="mm"
					type="number"
					name="footSize"
					onChange={onChangeHandler}
					value={info.footSize}
				/>
				<Button
					style={{
						position: 'absolute',
						bottom: 0,
						width: 'calc(100% - 60px)',
						marginBottom: 50,
					}}
					type={readyState ? 'active' : 'disabled'}
					label="다음 단계로"
					onClick={() => navigate('/join/town', { state: info })}
				/>
			</div>
		</>
	);
};

export default JoinInfo;
