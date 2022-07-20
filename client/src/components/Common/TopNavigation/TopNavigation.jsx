import React from 'react';
/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';
import { ReactComponent as ArrowBackIcon } from '../../../assets/arrow_back.svg';
import colors from '../../../constants/colors';
import common from '../../../constants/commonStyle';
const style = css`
	position: fixed;
	/* left: calc (100vw - 1200px); */
	top: 0;
	width: 100%;
	height: ${common.topHeaderHeight};
	max-width: 1200px;
	padding: 20px;
	box-shadow: 0 0.2px ${colors.text};
	z-index: 100;
	display: flex;
	align-items: center;
	background-color: ${colors.background};
	font-size: 24px;
`;

const TopNavigation = ({ backClick, onBackClick, leftContent, centerContent, rightMenu }) => {
	return (
		<div css={style}>
			{backClick && (
				<ArrowBackIcon onClick={onBackClick} width="28" height="28" fill={colors.text} />
			)}
			{leftContent && <div style={{ marginLeft: 20 }}>{leftContent}</div>}
			{centerContent && (
				<div style={{ position: 'absolute', left: '50%', transform: 'translate(-50%,0)' }}>
					{centerContent}
				</div>
			)}
			{rightMenu && <div style={{ marginLeft: 'auto' }}>{rightMenu}</div>}
		</div>
	);
};

export default TopNavigation;
