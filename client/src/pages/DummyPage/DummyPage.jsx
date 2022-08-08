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
import Modal from '../../components/Common/Modal/Modal';
import TextArea from '../../components/Common/TextArea/TextArea';
import { Checkbox, FeedCheckbox } from '../../components/Common/CheckBox/Checkbox';

import { useIntersect } from '../../hook/useIntersect';
import Loading from '../../components/Common/Loading/Loading';

const fakeFetch = (delay = 1000) => new Promise(res => setTimeout(res, delay));
const ListItem = ({ number }) => (
	<div className="ListItem" style={{ height: '100px' }}>
		<span>{number}</span>
	</div>
);

const DummyPage = () => {
	const [input, setInput] = useState('');
	const [btnState, setBtnState] = useState(false);
	const [modalVisible, setModalVisible] = useState(false);
	const [textareaValue, setTextareaValue] = useState('');
	const [inputChecked, setInputChecked] = useState(false);

	const [state, setState] = useState({ itemCount: 0, isLoading: false });
	const onIntersect = async (entry, observer) => {
		if (entry.isIntersecting) {
			observer.unobserve(entry.target);
			//비동기 로직
			await fetchItems();
			observer.observe(entry.target);
		}
	};
	const [target, setTarget] = useIntersect(onIntersect, { threshold: 0.5 });
	const fetchItems = async () => {
		setState(prev => ({ ...prev, isLoading: true }));
		await fakeFetch();
		setState(prev => ({
			itemCount: prev.itemCount + 10,
			isLoading: false,
		}));
	};

	useEffect(() => {
		fetchItems();
	}, []);
	const openModal = () => {
		setModalVisible(true);
	};
	const closeModal = () => {
		setModalVisible(false);
	};
	const check = () => {
		if (input === '윤주혜') {
			return true;
		}
		return false;
	};

	useEffect(() => {
		console.log(textareaValue);
	}, [textareaValue]);
	useEffect(() => {
		setBtnState(check());
	}, [input]);
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
						<button
							onClick={() => {
								openModal();
							}}
						>
							asd
						</button>
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
					overflow: 'scroll',
				}}
			>
				{modalVisible && (
					<Modal visible={modalVisible} maskClosable onClose={closeModal} type="center">
						<div>asd</div>
						<div>asd</div>
						<div>asd</div>
						<div>asd</div>
						<div>asd</div>
						<div>asd</div>
						<div>asd</div>
						<div>asd</div>
						<div>asd</div>
						<div>asd</div>
						<div>asd</div>
						<div>asd</div>
						<div>asd</div>
					</Modal>
				)}
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
				<TextArea
					value={textareaValue}
					onChange={e => setTextareaValue(e.target.value)}
					placeholder="내용"
					maxByte={500}
				/>
				<Checkbox
					checked={inputChecked}
					onChange={e => setInputChecked(!inputChecked)}
					label={inputChecked ? '대여 가능' : '대여 불가'}
					id="input1"
				/>
				<FeedCheckbox
					checked={inputChecked}
					onChange={e => setInputChecked(!inputChecked)}
					label={inputChecked ? '대여 가능' : '대여 불가'}
					id="input1"
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
				<div style={{ height: '400px' }}>
					<p style={{ fontFamily: 'MulishMedium', fontSize: 30 }}>123</p>
					<p style={{ fontFamily: 'MulishBold', fontSize: 30 }}>123</p>
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
				{[...Array(state.itemCount)].map((_, i) => {
					return <ListItem key={i} number={i} />;
				})}
				<div ref={setTarget}>{state.isLoading && <Loading />}</div>
			</div>
			<BottomNavigation />
		</>
	);
};

export default DummyPage;
