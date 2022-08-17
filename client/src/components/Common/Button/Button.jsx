import React, { forwardRef } from 'react';
/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';
import colors from '../../../constants/colors';

// type: disabled, active, kakao, normal
const btnType = {
	disabled: 'disabled',
	active: 'active',
	kakao: 'kakao',
	google: 'google',
	outlined: 'outlined',
	normal: 'normal',
};

const getBtnStyle = type => {
	const style = css`
		color: ${colors.white};
		border-radius: 100px;
	`;
	switch (type) {
		case btnType.disabled:
			return css`
				background-color: ${colors.disabled};
				${style}
			`;
		case btnType.active:
			return css`
				background-color: ${colors.green200};
				${style}
			`;
		case btnType.kakao:
			return css`
				background-color: ${colors.kakao};
				color: ${colors.black};
				border-radius: 10px;
			`;
		case btnType.outlined:
			return css`
				color: ${colors.green100};
				background-color: ${colors.background};
				border: 1px solid ${colors.green100};
			`;
		case btnType.google:
			return css`
				color: ${colors.google};
				background-color: ${colors.background};
				border: 1px solid ${colors.white};
			`;
		default:
			return css`
				background-color: ${colors.black};
				${style}
			`;
	}
};

const Button = ({ type, label, onClick, ...rest }, ref) => {
	return (
		<button
			ref={ref}
			css={css`
				width: 100%;
				padding: 18px 16px;
				border: none;
				border-radius: 100px;
				box-shadow: 4px 8px 24px rgba(16, 16, 16, 0.25);
				font-family: 'Bold';
				font-size: 16px;
				${getBtnStyle(type)}
			`}
			onClick={type !== btnType.disabled ? onClick : null}
			{...rest}
		>
			{label}
		</button>
	);
};

export default forwardRef(Button);
