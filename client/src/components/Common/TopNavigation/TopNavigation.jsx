import React from 'react';
/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';
import { ReactComponent as ArrowBackIcon } from '../../../assets/arrow_back.svg';
import colors from '../../../constants/colors';
const style = css`
	position: fixed;
	/* left: calc (100vw - 1200px); */
	top: 0;
	width: 100%;
	height: 85px;
	max-width: 1200px;
	padding: 20px;
	box-shadow: 0 0.1px ${colors.text};
	z-index: 100;
	display: flex;
	align-items: center;
	background-color: ${colors.background};
`;

const TopNavigation = ({ backClick, onBackClick }) => {
	return (
		<div css={style}>
			{backClick && <ArrowBackIcon onClick={onBackClick} />}
			<span>asd</span>
		</div>
	);
};

export default TopNavigation;
