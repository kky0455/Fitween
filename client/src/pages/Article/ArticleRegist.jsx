import React, { useRef, useState } from 'react';
/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';
import { useNavigate } from 'react-router-dom';

import colors from '../../constants/colors';
import TopNavigation from '../../components/Common/TopNavigation/TopNavigation';
import Button from '../../components/Common/Button/Button';
import Input from '../../components/Common/Input/Input';
import { registArticle } from '../../api/article';
import TextArea from '../../components/Common/TextArea/TextArea';
import Carousel from '../../components/Common/Carousel/Carousel';

const ArticleRegist = () => {
	const [title, setTitle] = useState('');
	const [price, setPrice] = useState('');
	const [content, setContent] = useState('');
	const [imageFiles, setImageFiles] = useState(null);
	const [imageSrcs, setImageSrcs] = useState([]);

	const fileRef = useRef(null);
	const navigate = useNavigate();
	const encodeFileToBase64 = fileBlob => {
		const reader = new FileReader();
		reader.readAsDataURL(fileBlob);
		return new Promise(resolve => {
			reader.onload = () => {
				setImageSrcs(prev => [...prev, reader.result]);
				resolve();
			};
		});
	};
	const onSubmitHandler = async () => {
		const formData = new FormData();
		formData.append('title', title);
		formData.append('price', price);
		formData.append('content', content);
		formData.append('photo', imageFiles);
		const ret = await registArticle(formData);
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
				<form name="imageForm">
					<Carousel
						style={{ marginBottom: '30px' }}
						contentHeight="300px"
						imgSrcList={imageSrcs}
						onClick={e => {
							fileRef.current.click();
						}}
					/>
					<input
						ref={fileRef}
						name="file"
						type="file"
						multiple
						accept="image/*"
						style={{ display: 'none' }}
						onChange={e => {
							if (e.target.files.length <= 5) {
								if (e.target.files.length > 0) {
									setImageSrcs([]);
									Array.from(e.target.files).forEach(file => encodeFileToBase64(file));
									setImageFiles(e.target.files);
								}
							} else {
								alert('최대 5개만 업로드할 수 있습니다.');
							}
						}}
					/>
				</form>
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
						onChange={e => setContent(e.target.value)}
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
