import React, { useState } from 'react';
/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';
import { useNavigate } from 'react-router-dom';

import colors from '../../constants/colors';
import TopNavigation from '../../components/Common/TopNavigation/TopNavigation';
import Button from '../../components/Common/Button/Button';
import Input from '../../components/Common/Input/Input';
import modify_img from '../../assets/modify_img.png';
import { registArticle } from '../../api/article';
import TextArea from '../../components/Common/TextArea/TextArea';
import getByte from '../../utils/getByte';
import { MAX_BYTE } from '../../constants/config';

const ArticleRegist = () => {
	const [title, setTitle] = useState('');
	const [price, setPrice] = useState('');
	const [content, setContent] = useState('');
	const navigate = useNavigate();
	const onSubmitHandler = async () => {
		const body = {
			title: title,
			price: price,
			content: content,
		};
		const ret = await registArticle(body);
		if (ret.result === 'success') navigate(`/article/${ret.articleIdx}`);
	};
	const onContentChangeHandler = e => {
		e.preventDefault();
		if (getByte(e.target.value) <= MAX_BYTE) setContent(e.target.value);
	};
	return (
		<>
			<TopNavigation
				backClick
				onBackClick={() => navigate(-1)}
				leftContent={<span>FITWEEN</span>}
			/>
			<div
				className="wrapper"
				style={{
					width: '100%',
					height: '100%',
					overflow: 'scroll',
				}}
			>
				{/* 사진 등록 */}
				<img
					src={modify_img}
					alt=""
					css={css`
						display: flex;
						justify-content: center;
						align-items: center;
						width: 100%;
					`}
				/>
				<div
					css={css`
						display: flex;
						flex-direction: column;
						padding: 30px 25px 12px 25px;
					`}
				>
					{/* 제목 */}
					<Input
						css={css`
							margin-bottom: 12px;
							border: 1px solid ${colors.black};
							text-align: center;
							box-shadow: none;
						`}
						type="text"
						value={title}
						onChange={e => setTitle(e.target.value)}
						placeholder="제목"
					/>
					{/* 대여 가격 */}
					<Input
						css={css`
							margin-bottom: 12px;
							border: 1px solid ${colors.black};
							text-align: center;
							box-shadow: none;
						`}
						type="number"
						value={price}
						onChange={e => setPrice(e.target.value)}
						placeholder="1일 대여 가격"
					/>
					{/* 내용 */}
					<TextArea
						css={css`
							text-align: center;
							border: 1px solid ${colors.black};
							box-shadow: none;
						`}
						type="text"
						value={content}
						onChange={onContentChangeHandler}
						maxByte={500}
						placeholder="내용"
					/>
				</div>
				<div
					css={css`
						display: flex;
						padding: 0px 25px 25px 25px;
					`}
				>
					<Button
						onClick={onSubmitHandler}
						type="active"
						label="등록하기"
						style={{ padding: 10 }}
					/>
				</div>
			</div>
		</>
	);
};

export default ArticleRegist;
