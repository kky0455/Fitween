import React from 'react';
/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';
import { useNavigate } from 'react-router-dom';

import checkbox from '../../assets/checkbox.svg';
import checkbox2 from '../../assets/checkbox2.svg';

const FeedProfile = ({ userId, imgSrc, nickname, isRent }) => {
	const navigate = useNavigate();
	return (
		<div
			css={css`
				display: flex;
				flex-direction: row;
				justify-content: space-between;
				align-items: center;
				width: 100%;
				padding: 10px 20px 10px;
				box-sizing: border-box;
				border: 1.5px solid #f0f0f0;
				border-radius: 20px 20px 0px 0px;
			`}
		>
			{/* 작성자 정보 */}
			<div
				css={css`
					display: flex;
					align-items: center;
				`}
				onClick={() => navigate(`/profile/${userId}`)}
			>
				{/* todo: 프로필 img api 받아와야 함 */}
				<img src={imgSrc} />
				<span className="fw-700 fs-18" style={{ paddingLeft: '15px', lineHeight: '21.6px' }}>
					{nickname}
				</span>
			</div>
			{/* 대여 가능 여부 */}
			<div
				css={css`
					display: flex;
					align-items: center;
				`}
			>
				{isRent ? <img src={checkbox} /> : <img src={checkbox2} />}
				<span className="fw-700 fs-14" style={{ paddingLeft: '7px', lineHeight: '20.27px' }}>
					{isRent ? '대여불가' : '대여가능'}
				</span>
			</div>
		</div>
	);
};

export default FeedProfile;
