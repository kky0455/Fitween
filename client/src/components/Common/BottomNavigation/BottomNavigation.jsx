import React from 'react';
/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';
import { ReactComponent as ChatIcon } from '../../../assets/chat.svg';
import { ReactComponent as HeartIcon } from '../../../assets/heart.svg';
import { ReactComponent as HomeIcon } from '../../../assets/home.svg';
import { ReactComponent as PersonIcon } from '../../../assets/person.svg';
import styled from 'styled-components';
import common from '../../../constants/commonStyle';
import colors from '../../../constants/colors';
const IconWrapper = styled.div`
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
	width: 25%;
	gap: 5px;
	font-size: 15px;
	color: ${colors.text};
`;
const BottomNavigation = () => {
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
			`}
		>
			<IconWrapper>
				<HomeIcon width={30} height={30} fill={colors.green200} />
				<span>홈</span>
			</IconWrapper>
			<IconWrapper>
				<HeartIcon width={30} height={30} fill={colors.text} />
				<span>찜</span>
			</IconWrapper>
			<IconWrapper>
				<ChatIcon width={30} height={30} fill={colors.text} />
				<span>채팅</span>
			</IconWrapper>
			<IconWrapper>
				<PersonIcon width={30} height={30} fill={colors.text} />
				<span>나</span>
			</IconWrapper>
		</div>
	);
};

export default BottomNavigation;
