import React from 'react';
/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';

const Main = ({ padding, gap, children }) => {
	return (
		<div
			css={css`
				padding: ${padding ? padding : '16px 14px'};
				overflow-y: scroll;
				width: 100%;
				height: 100%;
				display: flex;
				flex-direction: column;
				gap: ${gap ? gap : '14px'};
			`}
		>
			{children}
		</div>
	);
};

export default Main;
