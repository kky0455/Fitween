import React, { useEffect, useState } from 'react';
/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';
import { ReactComponent as ChatIcon } from '../../../assets/chat.svg';
import { ReactComponent as HeartIcon } from '../../../assets/heart.svg';
import { ReactComponent as HomeIcon } from '../../../assets/home.svg';
import { ReactComponent as PersonIcon } from '../../../assets/person.svg';
import styled from 'styled-components';
import common from '../../../constants/commonStyle';
import colors from '../../../constants/colors';
import { useGlobalContext } from '../../../contexts/GlobalContext';

const IconWrapper = ({ onClick, active, icon, label }) => {
	return (
		<div
			css={css`
				display: flex;
				flex-direction: column;
				justify-content: center;
				align-items: center;
				width: 25%;
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
	const [path, setPath] = useState('/home');
	const { setHasBottom } = useGlobalContext();
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
			<IconWrapper
				onClick={e => setPath('home')}
				active={path.includes('home')}
				icon={<HomeIcon width={30} height={30} />}
				label="홈"
			/>
			<IconWrapper
				onClick={e => setPath('like')}
				active={path.includes('like')}
				icon={<HeartIcon width={30} height={30} />}
				label="찜"
			/>
			<IconWrapper
				onClick={e => setPath('chat')}
				active={path.includes('chat')}
				icon={<ChatIcon width={30} height={30} />}
				label="채팅"
			/>
			<IconWrapper
				onClick={e => setPath('myprofile')}
				active={path.includes('myprofile')}
				icon={<PersonIcon width={30} height={30} />}
				label="나"
			/>
		</div>
	);
};

export default BottomNavigation;
