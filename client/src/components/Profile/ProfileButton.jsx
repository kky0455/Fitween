import React, { useState } from 'react';
/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';

import colors from '../../constants/colors';
import Button from '../../components/Common/Button/Button';
import { useNavigate } from 'react-router-dom';
import { doFollow, cancelFollow } from '../../api/user';

const ProfileButton = ({ userId, isFollowed, setProfileInfo }) => {
	const navigate = useNavigate();
	const [followed, setFollowed] = useState(isFollowed);

	const doFollowClickHandler = async () => {
		const ret = await doFollow(userId);
		if (ret === '팔로우 성공') {
			setFollowed(!followed);
			setProfileInfo(profileInfo => {
				return { ...profileInfo, userFollowerCount: profileInfo.userFollowerCount + 1 };
			});
		}
	};

	const cancelFollowClickHandler = async () => {
		const ret = await cancelFollow(userId);
		if (ret === '팔로우 취소 성공') {
			setFollowed(!followed);
			setProfileInfo(profileInfo => {
				return { ...profileInfo, userFollowerCount: profileInfo.userFollowerCount - 1 };
			});
		}
	};

	return (
		<div
			css={css`
				display: flex;
				flex-direction: row;
				justify-content: center;
				align-items: center;
				padding: 14px;
			`}
		>
			{followed ? (
				<Button
					css={css`
						display: flex;
						justify-content: center;
						align-items: center;
						padding: 10px;
						margin: 10px;
						border: 2px solid ${colors.green100};
						border-radius: 14px;
						box-shadow: none;
					`}
					type="outlined"
					label="UNFOLLOW"
					onClick={cancelFollowClickHandler}
				/>
			) : (
				<Button
					css={css`
						display: flex;
						justify-content: center;
						align-items: center;
						padding: 10px;
						margin: 10px;
						border: 2px solid ${colors.green100};
						border-radius: 14px;
						box-shadow: none;
					`}
					type="active"
					label="FOLLOW"
					onClick={doFollowClickHandler}
				/>
			)}

			<Button
				css={css`
					display: flex;
					justify-content: center;
					align-items: center;
					padding: 10px;
					margin: 10px;
					border: 2px solid ${colors.green100};
					border-radius: 14px;
					box-shadow: none;
				`}
				type="outlined"
				label="CHAT"
				onClick={() =>
					navigate('/chat/room', {
						state: { roomId: null, receiverId: userId },
					})
				}
			/>
		</div>
	);
};

export default ProfileButton;
