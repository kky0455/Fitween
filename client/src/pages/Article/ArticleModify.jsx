import React, { useState, useEffect } from 'react';
/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';
import { useNavigate } from 'react-router-dom';

import colors from '../../constants/colors';
import TopNavigation from '../../components/Common/TopNavigation/TopNavigation';
import Button from '../../components/Common/Button/Button';
import Input from '../../components/Common/Input/Input';
import { useParams } from 'react-router-dom';
import modify_img from '../../assets/modify_img.png';
import { getArticleDetail } from '../../api/article';
import { modifyArticle } from '../../api/article';
import TextArea from '../../components/Common/TextArea/TextArea';
import { Checkbox } from '../../components/Common/CheckBox/Checkbox';

const ArticleModify = () => {
	const [title, setTitle] = useState();
	const [price, setPrice] = useState('');
	const [content, setContent] = useState('');
	const [isRent, setIsRent] = useState(false);
	const navigate = useNavigate();

	useEffect(() => {
		const fetch = async () => {
			const data = await getArticleDetail();
			setTitle(data.articleTitle);
			setPrice(data.articlePrice);
			setContent(data.articleContent);
			setIsRent(data.articleIsRent);
		};
		fetch();
	}, []);
	const onSubmitHandler = async () => {
		const body = {
			title: title,
			price: price,
			content: content,
			isRent: isRent,
		};
		const ret = await modifyArticle(body);
		if (ret.result === 'success') navigate(`/article/${ret.articleId}`);
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
						padding: 30px 25px;
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
						onChange={e => setContent(e.target.value)}
						maxByte={500}
						placeholder="내용"
					/>
					<div
						css={css`
							display: flex;
							align-items: center;
							justify-content: center;
						`}
					>
						<Checkbox
							checked={isRent}
							onChange={e => setIsRent(e.target.checked)}
							label={isRent ? '대여 가능' : '대여 불가'}
							id="checkrent"
						/>
					</div>
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
						label="수정하기"
						style={{ padding: 10 }}
					/>
				</div>
			</div>
		</>
	);
};

export default ArticleModify;
