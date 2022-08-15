import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import _ from 'lodash';

import Button from '../../components/Common/Button/Button';
import Input from '../../components/Common/Input/Input';
import TopNavigation from '../../components/Common/TopNavigation/TopNavigation';
import {
	validateFootSize,
	validateHeight,
	validateNickName,
	validateWeight,
	validateAllInput,
	validateDateOfBirth,
} from '../../utils/validate';
import nicknameImg from '../../assets/nickname.svg';
import { Radiobox } from '../../components/Common/CheckBox/Checkbox';

const JoinInfo = ({ info, onChangeHandler, setInfo }) => {
	const [readyState, setReadyState] = useState(false);
	const [nickNameErrMsg, setNickNameErrMsg] = useState('');
	const [validateNickname, setValidateNickname] = useState(true);
	const navigate = useNavigate();

	const debounce = _.debounce(e => {
		setInfo(prevState => {
			return { ...prevState, [e.target.name]: e.target.value };
		});
	}, 500);

	useEffect(() => {
		const checkAll = async info => {
			const { state, errMessage } = await validateNickName(info.nickname);

			setValidateNickname(state);

			if (!state) {
				setNickNameErrMsg(errMessage);
			} else {
				setNickNameErrMsg('');
			}

			if (await validateAllInput(info)) {
				setReadyState(true);
			} else {
				setReadyState(false);
			}
		};
		checkAll(info);
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
				<div
					style={{
						display: 'flex',
						justifyContent: 'center',
						alignItems: 'center',
						width: '100%',
						paddingBottom: '30px',
					}}
				>
					<Radiobox
						id="woman"
						type="radio"
						onClick={onChangeHandler}
						value="여성"
						name="gender"
						checked={info.gender === '여성'}
					/>
					여성
					<Radiobox
						id="man"
						type="radio"
						onClick={onChangeHandler}
						value="남성"
						name="gender"
						style={{ marginLeft: '20px' }}
						checked={info.gender === '남성'}
					/>
					남성
				</div>
				<Input
					placeholder="생년월일"
					error={!(info.dateOfBirth.length === 0 || validateDateOfBirth(info.dateOfBirth))}
					errMsg="생년월일을 입력해주세요."
					type="date"
					onChange={onChangeHandler}
					name="dateOfBirth"
					style={{
						padding: '20px',
						fontSize: '14px',
					}}
				/>
				<Input
					placeholder="별명"
					error={!(info.nickname.length === 0 || validateNickname)}
					errMsg={nickNameErrMsg}
					image={nicknameImg}
					type="text"
					onChange={debounce}
					name="nickname"
					style={{
						padding: '20px',
						fontSize: '14px',
					}}
				/>
				<Input
					placeholder="키"
					error={!(info.height.length === 0 || validateHeight(info.height))}
					errMsg="키는 90cm 이상 250cm 이하로 입력해주세요."
					unit="cm"
					type="number"
					name="height"
					onChange={onChangeHandler}
					style={{
						padding: '20px',
						fontSize: '14px',
					}}
				/>
				<Input
					placeholder="몸무게"
					error={!(info.weight.length === 0 || validateWeight(info.weight))}
					errMsg="몸무게는 30kg 이상 200kg 이하로 입력해주세요."
					unit="kg"
					type="number"
					name="weight"
					onChange={onChangeHandler}
					style={{
						padding: '20px',
						fontSize: '14px',
					}}
				/>
				<Input
					placeholder="발사이즈"
					error={!(info.footSize.length === 0 || validateFootSize(info.footSize))}
					errMsg="발사이즈는 0mm 이상 350mm 이하로 입력해주세요."
					unit="mm"
					type="number"
					name="footSize"
					onChange={onChangeHandler}
					style={{
						padding: '20px',
						fontSize: '14px',
					}}
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
