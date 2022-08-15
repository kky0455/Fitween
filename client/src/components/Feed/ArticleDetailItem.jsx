import React from 'react';
/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';

import FeedProfile from '../../components/Feed/FeedProfile';

function timeForToday(value) {
	const today = new Date();
	const timeValue = new Date(value);

	const betweenTime = Math.floor((today.getTime() - timeValue.getTime()) / 1000 / 60);
	if (betweenTime < 1) return '방금 전';
	if (betweenTime < 60) {
		return `${betweenTime}분 전`;
	}

	const betweenTimeHour = Math.floor(betweenTime / 60);
	if (betweenTimeHour < 24) {
		return `${betweenTimeHour}시간 전`;
	}

	const betweenTimeDay = Math.floor(betweenTime / 60 / 24);
	if (betweenTimeDay < 365) {
		return `${betweenTimeDay}일 전`;
	}

	return `${Math.floor(betweenTimeDay / 365)}년 전`;
}

const ArticleDetailItem = ({
	articleImg,
	nickname,
	title,
	updateTime,
	content,
	profileImg,
	isRent,
	writerId,
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
			<FeedProfile writerId={writerId} imgSrc={profileImg} nickname={nickname} isRent={isRent} />
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
					{timeForToday(updateTime)}
				</div>
				<div className="fw-400 fs-16" style={{ lineHeight: '20.08px' }}>
					{content}
				</div>
			</div>
		</div>
	);
};

export default ArticleDetailItem;
