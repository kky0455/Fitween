import React from 'react';
/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';

import FeedProfile from '../../components/Feed/FeedProfile';

const ArticleDetailItem = ({
	articleImg,
	userId,
	title,
	createTime,
	content,
	profileImg,
	isRent,
}) => {
	return (
		<div>
			{/* 상세이미지 */}
			<div
				css={css`
					display: flex;
					justify-content: center;
					width: 100%;
					height: 330px;
				`}
			>
				<img src={articleImg} alt="" />
			</div>
			{/* 작성자 정보, 대여 가능 여부 */}
			<FeedProfile imgSrc={profileImg} userId={userId} isRent={isRent} />
			{/* 게시글 제목, 내용 */}
			<div
				css={css`
					display: flex;
					flex-direction: column;
					gap: 15px;
					padding: 20px 30px;
				`}
			>
				<h1 className="fw-700 fs-24" style={{ lineHeight: '30.12px' }}>
					{title}
				</h1>
				<div className="fw-500 fs-14" style={{ lineHeight: '17.57px', color: '#8A8A8A' }}>
					{createTime}
				</div>
				<div className="fw-400 fs-16" style={{ lineHeight: '20.08px' }}>
					{content}
				</div>
			</div>
		</div>
	);
};

export default ArticleDetailItem;
