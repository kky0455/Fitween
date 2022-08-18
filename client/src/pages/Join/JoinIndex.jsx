import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';

import Button from '../../components/Common/Button/Button';
import { Checkbox } from '../../components/Common/CheckBox/Checkbox';
import TopNavigation from '../../components/Common/TopNavigation/TopNavigation';
import colors from '../../constants/colors';

const AgreeContent = styled.div`
	/* display: flex; */
	margin-bottom: 30px;
	font-family: 'Regular';
	font-size: 15px;
	line-height: 20px;

	label {
		font-family: 'Medium';
	}
	p {
		margin-top: 16px;
		border: 1px solid #8a8a8a;
		padding: 10px;
	}
`;

const CONDITIONS = [
	{
		id: '0',
		checked: false,
		title: '위치 정보 수집 및 이용 동의',
		text: '위치 정보 기반 서비스 제공을 위해 위치정보를 수집 및 이용하는 것에 동의합니다.',
	},
	{
		id: '1',
		checked: false,
		title: '서비스 이용 동의',
		text: '피트윈의 과실로 인하여 사용자가 손해를 입게 될 경우 피트윈은 법령에 따라 사용자의 손해를 배상하겠습니다. 다만, 피트윈은 피트윈 서비스에 접속 또는 이용과정에서 발생하는 개인적인손해, 제3자가 피트윈 서비스를 이용하는 과정에서 사용자에게 발생시킨 손해에 대하여 책임을 부담하지 않습니다.',
	},
];

const JoinIndex = () => {
	const navigate = useNavigate();
	const [conditions, setConditions] = useState(CONDITIONS);

	const onCheckHandler = e => {
		const newConditions = conditions.map(condition => {
			if (condition.id === e.target.id) {
				return { ...condition, checked: !condition.checked };
			}
			return condition;
		});
		setConditions(newConditions);
	};

	const onSubmitHandler = () => {
		if (conditions.some(condition => condition.checked === false)) {
			alert('약관에 모두 동의 해주세요');
			return;
		}
		navigate('/join/info');
	};

	return (
		<>
			<TopNavigation
				backClick
				centerContent="약관 동의"
				onBackClick={() => {
					navigate('/');
				}}
			/>
			<div
				className="wrapper"
				style={{
					padding: '30px',
					width: '100%',
					height: '100%',
					position: 'relative',
					overflow: 'scroll',
					height: 'auto',
				}}
			>
				{conditions.map(condition => (
					<AgreeContent key={condition.id}>
						<Checkbox
							checked={condition.checked}
							onChange={onCheckHandler}
							id={condition.id}
							label={condition.title}
						/>
						<p>{condition.text}</p>
					</AgreeContent>
				))}

				<Button
					label="동의하고 회원가입하기"
					onClick={onSubmitHandler}
					// style={{ position: 'absolute', bottom: 0, width: 'calc(100% - 60px)' }}
				/>
			</div>
		</>
	);
};

export default JoinIndex;
