import React from 'react';
/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';

import FollowCnt from '../../components/Profile/FollowCnt';
import ClosetCnt from '../../components/Profile/ClosetCnt';

const ProfileTop = ({ imgSrc, followerCount, followingCount, clothesCount }) => {
	return (
		<div
			css={css`
				display: flex;
				flex-direction: row;
				justify-content: center;
				align-items: center;
			`}
		>
			<img
				src={imgSrc}
				alt=""
				css={css`
					display: flex;
					justify-content: center;
					align-items: center;
					width: 100px;
					height: 100px;
				`}
			/>
			{/* 프로필 사진 옆 텍스트*/}
			<div
				css={css`
					display: flex;
					flex-direction: column;
					justify-content: center;
					align-items: center;
					padding-left: 20px;
				`}
			>
				{/* 팔로워, 팔로잉 */}
				<FollowCnt followerCnt={followerCount} followingCnt={followingCount} />
				{/* 옷 수 */}
				<ClosetCnt clothesCnt={clothesCount} />
			</div>
		</div>
	);
};

export default ProfileTop;
