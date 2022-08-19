import React, { useState, useEffect, useRef } from 'react';
import { useNavigate } from 'react-router-dom';

import * as userApi from '../../api/user';
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

const ProfileInfoModify = () => {
	const [readyState, setReadyState] = useState(false);
	const [nickNameErrMsg, setNickNameErrMsg] = useState('');
	const [validateNickname, setValidateNickname] = useState(true);
	const mounted = useRef(false);
	const navigate = useNavigate();
	const [info, setInfo] = useState({
		gender: '',
		dateOfBirth: '',
		nickname: '',
		weight: '',
		height: '',
		footSize: '',
		region: '',
	});

	const onChangeHandler = e => {
		setInfo(prevState => {
			return { ...prevState, [e.target.name]: e.target.value };
		});
	};

	useEffect(() => {
		const getInfo = async () => {
			try {
				const res = await userApi.getuserDetailInfo();
				setInfo(res);
			} catch (err) {
				throw err;
			}
		};
		getInfo();
	}, []);

	useEffect(() => {
		if (mounted.current) {
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
		} else {
			mounted.current = true;
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
					value={info.dateOfBirth}
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
					onChange={onChangeHandler}
					value={info.nickname}
					name="nickname"
					style={{
						padding: '20px',
						fontSize: '14px',
					}}
				/>
				<Input
					placeholder="키"
					error={!(info.height === null || validateHeight(info.height))}
					errMsg="키는 90cm 이상 250cm 이하로 입력해주세요."
					unit="cm"
					type="number"
					value={info.height}
					name="height"
					onChange={onChangeHandler}
					style={{
						padding: '20px',
						fontSize: '14px',
					}}
				/>
				<Input
					placeholder="몸무게"
					error={!(info.weight === null || validateWeight(info.weight))}
					errMsg="몸무게는 30kg 이상 200kg 이하로 입력해주세요."
					unit="kg"
					type="number"
					value={info.weight}
					name="weight"
					onChange={onChangeHandler}
					style={{
						padding: '20px',
						fontSize: '14px',
					}}
				/>
				<Input
					placeholder="발사이즈"
					error={!(info.footSize === null || validateFootSize(info.footSize))}
					errMsg="발사이즈는 0mm 이상 350mm 이하로 입력해주세요."
					unit="mm"
					type="number"
					value={info.footSize}
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
					onClick={() => navigate('/profile/modify/town', { state: info })}
				/>
			</div>
		</>
	);
};

export default ProfileInfoModify;
