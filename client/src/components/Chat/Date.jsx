import React from 'react';
/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';
import colors from '../../constants/colors';

const Date = ({ date }) => {
	return (
		<div
			className="date"
			css={css`
				display: flex;
				font-family: 'Regular';
				font-size: 12px;
				line-height: 16.8px;
				background-color: ${colors.grey100};
				background-color: ${colors.grey50};
				align-self: center;
				padding: 4px 12px;
				border-radius: 50px;
			`}
		>
			<span>{date}</span>
		</div>
	);
};

export default Date;
