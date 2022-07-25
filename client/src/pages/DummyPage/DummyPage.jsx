import React, { useEffect, useState } from 'react';
/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';
import API from '../../api';
import styled from 'styled-components';
import colors from '../../constants/colors';
import common from '../../constants/commonStyle';
import TopNavigation from '../../components/Common/TopNavigation/TopNavigation';
import Button from '../../components/Common/Button/Button';
import Input from '../../components/Common/Input/Input';
import BottomNavigation from '../../components/Common/BottomNavigation/BottomNavigation';
import * as articleApi from '../../api/article';

const DummyPage = () => {
	const [input, setInput] = useState('');
	const [btnState, setBtnState] = useState(false);
	const check = value => {
		return value === '윤주혜' ? true : false;
	};
	useEffect(() => {
		const fetch = async () => {
			const res = await articleApi.modifyArticle('12314', { title: 'titiel' });
			console.log(res);
		};
		fetch();
	}, []);
	useEffect(() => {
		setBtnState(check(input));
	}, [input]);
	useEffect(() => {
		const fetch = async () => {
			try {
				const res = await API.post('/user/login', { accessToken: 'true' });
				console.log(res);
			} catch (err) {
				console.log(err);
				console.log(err.response.data);
			}
		};
		fetch();
	}, []);
	useEffect(() => {
		const fetch = async () => {
			try {
				const res = await API.post('/user/login', { accessToken: 'nottrue' });
			} catch (err) {
				console.log(err);
				console.log(err.response.data);
			}
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
				leftContent={<span>Fitween</span>}
				rightMenu={
					<>
						<button>asd</button>
						<button>asd</button>
						<button>asd</button>
					</>
				}
			/>
			<div
				className="wrapper"
				style={{
					padding: '30px',
					width: '100%',
					height: '100%',
				}}
			>
				<Input
					type="text"
					value={input}
					onChange={e => setInput(e.target.value)}
					placeholder="별명"
					error
					errMsg="중복된 별명입니다."
				/>
				<Input
					type="text"
					value={input}
					onChange={e => setInput(e.target.value)}
					placeholder="별명"
					unit="cm"
				/>
				<div style={{ height: '400px' }}>
					<p style={{ fontFamily: 'Regular', fontSize: 30 }}>Test 테스트</p>
					<p style={{ fontFamily: 'Medium', fontSize: 30 }}>Test 테스트</p>
					<p style={{ fontFamily: 'Bold', fontSize: 30 }}>Test 테스트</p>
					<p style={{ fontFamily: 'Black', fontSize: 30 }}>Test 테스트</p>
				</div>
				<div style={{ height: '400px' }}>
					<p style={{ fontFamily: 'MulishMedium', fontSize: 30 }}>123</p>
					<p style={{ fontFamily: 'MulishBold', fontSize: 30 }}>123</p>
				</div>

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
			</div>
			<BottomNavigation />
		</>
	);
};

export default DummyPage;
