import React from 'react';
/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';

const FollowCnt = ({ followerCnt, followingCnt }) => {
	return (
		<div
			css={css`
				display: flex;
				flex-direction: row;
				justify-content: center;
				padding: 10px 10px 0px 10px;
			`}
		>
			{/* 팔로워 */}
			<div
				css={css`
					display: flex;
					flex-direction: column;
					align-items: center;
					padding: 10px;
				`}
			>
				<span className="fw-500 fs-24" style={{ lineHeight: '33.6px' }}>
					{followerCnt}
				</span>
				<span className="fw-500 fs-16" style={{ lineHeight: '19.2px' }}>
					Followers
				</span>
			</div>
			{/* 팔로잉 */}
			<div
				css={css`
					display: flex;
					flex-direction: column;
					align-items: center;
					padding: 10px;
				`}
			>
				<sapn className="fw-500 fs-24" style={{ lineHeight: '33.6px' }}>
					{followingCnt}
				</sapn>
				<span className="fw-500 fs-16" style={{ lineHeight: '19.2px' }}>
					Following
				</span>
			</div>
		</div>
	);
};

export default FollowCnt;
