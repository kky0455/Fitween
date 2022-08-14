import React, { useEffect, useState } from 'react';
/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';
import { NavLink } from 'react-router-dom';

import { ReactComponent as ChatIcon } from '../../../assets/chat.svg';
import { ReactComponent as HeartIcon } from '../../../assets/heart.svg';
import { ReactComponent as HomeIcon } from '../../../assets/home.svg';
import { ReactComponent as PersonIcon } from '../../../assets/person.svg';
import styled from 'styled-components';
import common from '../../../constants/commonStyle';
import colors from '../../../constants/colors';
import { useGlobalContext } from '../../../contexts/GlobalContext';
import { useUserState } from '../../../context/User/UserContext';

const Link = styled(NavLink)`
	width: 25%;
`;
const IconWrapper = ({ onClick, active, icon, label }) => {
	return (
		<div
			css={css`
				display: flex;
				height: 100%;
				flex-direction: column;
				justify-content: center;
				align-items: center;
				gap: 5px;
				color: ${active ? colors.green200 : colors.text};
				fill: ${active ? colors.green200 : colors.text};
			`}
			onClick={onClick}
		>
			{icon}
			<span>{label}</span>
		</div>
	);
};
const BottomNavigation = () => {
	const { setHasBottom } = useGlobalContext();
	const { loginedUserId } = useUserState();
	useEffect(() => {
		setHasBottom(true);
		return () => {
			setHasBottom(false);
		};
	}, []);

	return (
		<div
			css={css`
				width: 100%;
				max-width: 1200px;
				background-color: ${colors.background};
				display: flex;
				justify-content: space-between;
				position: fixed;
				bottom: 0;
				z-index: 100;
				height: ${common.bottomHeaderHeight};
				box-shadow: 0 0.2px ${colors.text};
				font-family: 'Medium';
				font-size: 12px;
			`}
		>
			<Link to="/main">
				{({ isActive }) => (
					<IconWrapper active={isActive} icon={<HomeIcon width={30} height={30} />} label="홈" />
				)}
			</Link>
			<Link to="/like">
				{({ isActive }) => (
					<IconWrapper active={isActive} icon={<HeartIcon width={30} height={30} />} label="찜" />
				)}
			</Link>
			<Link to="/chat">
				{({ isActive }) => (
					<IconWrapper active={isActive} icon={<ChatIcon width={30} height={30} />} label="채팅" />
				)}
			</Link>
			<Link to={`/profile/${loginedUserId}`}>
				{({ isActive }) => (
					<IconWrapper active={isActive} icon={<PersonIcon width={30} height={30} />} label="나" />
				)}
			</Link>
		</div>
	);
};

export default BottomNavigation;
