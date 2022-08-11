import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

import Button from '../../components/Common/Button/Button';
import Input from '../../components/Common/Input/Input';
import TopNavigation from '../../components/Common/TopNavigation/TopNavigation';
import {
	validateFootSize,
	validateHeight,
	validateNickName,
	validateWeight,
	validateAllInput,
} from '../../utils/validate';

const JoinInfo = ({ info, onChangeHandler }) => {
	const [readyState, setReadyState] = useState(false);
	const [nickNameErrMsg, setNickNameErrMsg] = useState('');
	const navigate = useNavigate();

	useEffect(() => {
		const { state, type } = validateNickName(info.nickname);

		if (validateAllInput(info)) {
			setReadyState(true);
		} else {
			setReadyState(false);
		}

		if (!state) {
			if (type === 'notLengthErr')
				setNickNameErrMsg('별명은 특수문자, 이모지, 숫자를 입력받을 수 없습니다.');
			else if (type === 'lengthErr') setNickNameErrMsg('별명은 2자 이상 8자 이하로 입력해주세요.');
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
					error={info.nickname.length === 0 || validateNickName(info.nickname).state ? false : true}
					errMsg={nickNameErrMsg}
					type="text"
					onChange={onChangeHandler}
					value={info.nickname}
					name="nickname"
				/>
				<Input
					placeholder="키"
					error={info.height.length === 0 || validateHeight(info.height) ? false : true}
					errMsg="키는 90cm 이상 250cm 이하로 입력해주세요."
					unit="cm"
					type="number"
					name="height"
					onChange={onChangeHandler}
					value={info.height}
				/>
				<Input
					placeholder="몸무게"
					error={info.weight.length === 0 || validateWeight(info.weight) ? false : true}
					errMsg="몸무게는 30kg 이상 200kg 이하로 입력해주세요."
					unit="kg"
					type="number"
					name="weight"
					onChange={onChangeHandler}
					value={info.weight}
				/>
				<Input
					placeholder="발사이즈"
					error={info.footSize.length === 0 || validateFootSize(info.footSize) ? false : true}
					errMsg="발사이즈는 0mm 이상 350mm 이하로 입력해주세요."
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
					onClick={() => navigate('/join/town')}
				/>
			</div>
		</>
	);
};

export default JoinInfo;
