import React from 'react';
/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';

import colors from '../../constants/colors';
import TopNavigation from '../../components/Common/TopNavigation/TopNavigation';
import Button from '../../components/Common/Button/Button';
import Input from '../../components/Common/Input/Input';
import { useParams } from 'react-router-dom';
import modify_img from '../../assets/modify_img.png';
const ArticleRegist = () => {
	return (
		<>
			<TopNavigation
				backClick
				onBackClick={() => {
					alert('클릭');
				}}
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
						padding: 30px 25px 0px 25px;
					`}
				>
					{/* 제목 */}
					<Input
						css={css`
							margin-bottom: 12px;
							border: 1px solid ${colors.black};
							text-align: center;
						`}
						type="text"
						placeholder="제목"
					/>
					{/* 대여 가격 */}
					<Input
						css={css`
							margin-bottom: 12px;
							border: 1px solid ${colors.black};
							text-align: center;
						`}
						type="number"
						placeholder="1일 대여 가격"
					/>
					{/* 내용 */}
					<Input
						css={css`
							min-height: 170px;
							margin-bottom: 12px;
							border: 1px solid ${colors.black};
							text-align: center;
						`}
						type="text"
						placeholder="내용"
					/>
				</div>
				<div
					css={css`
						display: flex;
						padding: 0px 25px 25px 25px;
					`}
				>
					<Button type="active" label="등록" style={{ padding: 10 }} />
				</div>
			</div>
		</>
	);
};

export default ArticleRegist;
