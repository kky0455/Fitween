import React from 'react';
/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';

import colors from '../../constants/colors';

const CategoryBtn = ({ label, isActive, onClickHandler, code }) => {
	return (
		<button
			css={css`
				padding: 7px 12px;
				border: ${isActive ? 'none' : `1px solid ${colors.green100}`};
				border-radius: 16px;
				background: ${isActive ? `${colors.green100}` : `${colors.background}`};
				color: ${isActive ? `${colors.background}` : `${colors.green100}`};
				font-family: 'normal';
				font-size: 12px;
			`}
			name={label}
			id={code}
			onClick={onClickHandler}
		>
			{label}
		</button>
	);
};

export default CategoryBtn;
