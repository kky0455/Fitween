import React, { useEffect, useState } from 'react';
/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';
import API from '../../api';
import styled from 'styled-components';
import colors from '../../constants/colors';
import TopNavigation from '../../components/Common/TopNavigation/TopNavigation';
import Button from '../../components/Common/Button/Button';

const DummyPage = () => {
	const [input, setInput] = useState('');
	const [btnState, setBtnState] = useState(false);
	const check = () => {
		if (input === '윤주혜') {
			return true;
		}
		return false;
	};
	useEffect(() => {
		setBtnState(check());
	}, [input]);
	useEffect(() => {
		const fetch = async () => {
			const res = await API.get('/test');
			console.log(res);
		};
		fetch();
	}, []);
	return (
		<>
			<TopNavigation
				backClick
				onBackClick={() => {
					alert('클릭');
				}}
				leftContent={<span>Fitwell</span>}
				rightMenu={
					<>
						<button>asd</button>
						<button>asd</button>
						<button>asd</button>
					</>
				}
			/>
			<input type="text" value={input} onChange={e => setInput(e.target.value)} />
			<div style={{ height: '400px' }}>옷 공유 합니다 1234</div>
			<Button type="normal" label="다음 단계" style={{ marginBottom: 10 }} />
			<Button type="kakao" label="다음 단계" style={{ marginBottom: 10 }} />
			<Button type="active" label="다음 단계" style={{ marginBottom: 10 }} />
			<Button
				type={btnState ? 'active' : 'disabled'}
				label="다음 단계"
				style={{ marginBottom: 10 }}
				onClick={() => {
					alert('클릭');
				}}
			/>
		</>
	);
};

export default DummyPage;
