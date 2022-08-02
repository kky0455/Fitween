import React from 'react';
/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';
import styled from 'styled-components';
import colors from '../../constants/colors';
import TopNavigation from '../../components/Common/TopNavigation/TopNavigation';
import Button from '../../components/Common/Button/Button';
import Input from '../../components/Common/Input/Input';
import { useParams } from 'react-router-dom';
import modify_img from '../../assets/modify_img.png';
import check_box_blank from '../../assets/check_box_blank.svg';
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
						`}
					/>
					{/* 대여 가격 */}
					<Input
						css={css`
							margin-bottom: 12px;
							border: 1px solid ${colors.black};
						`}
					/>
					{/* 내용 */}
					<Input
						css={css`
							min-height: 170px;
							margin-bottom: 12px;
							border: 1px solid ${colors.black};
						`}
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
