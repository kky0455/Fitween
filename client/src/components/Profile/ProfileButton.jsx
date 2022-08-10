import React, { useState, useEffect } from 'react';
/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';

import colors from '../../constants/colors';
import Button from '../../components/Common/Button/Button';
import { useNavigate } from 'react-router-dom';
import { getUserInfo, doFollow, cancelFollow } from '../../api/user';

const ProfileButton = ({ userId, isFollowed, setProfileInfo }) => {
	const navigate = useNavigate();
	const [userid, setUserid] = useState('');
	const [followed, setFollowed] = useState(isFollowed);
	useEffect(() => {
		const fetch = async () => {
			const data = await getUserInfo();
			setUserid(data.userInfo.userId);
		};
		fetch();
	}, []);

	const doFollowClickHandler = async () => {
		const ret = await doFollow();
		if (ret.result === 'success') {
			setFollowed(!followed);
			setProfileInfo(profileInfo => {
				return { ...profileInfo, userFollowedCnt: profileInfo.userFollowedCnt + 1 };
			});
		}
	};

	const cancelFollowClickHandler = async () => {
		const ret = await cancelFollow();
		if (ret.result === 'success') {
			setFollowed(!followed);
			setProfileInfo(profileInfo => {
				return { ...profileInfo, userFollowedCnt: profileInfo.userFollowedCnt - 1 };
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
					type="active"
					label="FOLLOW"
					onClick={doFollowClickHandler}
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
					type="outlined"
					label="UNFOLLOW"
					onClick={cancelFollowClickHandler}
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
