import React from 'react';
/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';

import colors from '../../../constants/colors';
import { ReactComponent as CheckedIcon } from '../../../assets/checkbox2.svg';
import { ReactComponent as UnCheckedIcon } from '../../../assets/unCheckedBox.svg';
import styled from 'styled-components';

const CheckBoxContainer = styled.div`
	display: flex;
	align-items: center;
	position: relative;
`;
const CheckBoxLabel = styled.label`
	display: inline-block;
	cursor: pointer;
	margin: 0 10px;
	width: 25px;
	height: 25px;
	border: ${({ checked }) => (checked ? 'none' : `2px solid ${colors.text}`)};
	border-radius: 7px;
	& > svg {
		width: 100%;
		height: 100%;
	}
`;

const CheckBoxInput = styled.input`
	border: 0;
	clip: rect(0 0 0 0);
	height: 1px;
	margin: 0;
	padding: 0;
	overflow: hidden;
	position: absolute;
	white-space: nowrap;
	width: 1px;
`;

const CheckBoxText = styled.label`
	padding-left: 8.75px;
	padding-bottom: 2px;
	font-family: 'Regular';
	font-size: 16px;
	line-height: 30px;
`;

export const Checkbox = ({ id, checked, onChange, label }) => {
	return (
		<CheckBoxContainer>
			<CheckBoxLabel htmlFor={id} checked={checked}>
				<CheckBoxInput id={id} type="checkbox" checked={checked} onChange={onChange} />
				{checked && <CheckedIcon />}
			</CheckBoxLabel>
			{label && <CheckBoxText htmlFor={id}>{label}</CheckBoxText>}
		</CheckBoxContainer>
	);
};

export const Radiobox = ({ id, checked, onClick, ...rest }) => {
	return (
		<CheckBoxContainer>
			<CheckBoxLabel htmlFor={id} checked={checked}>
				<CheckBoxInput id={id} type="radio" checked={checked} onClick={onClick} {...rest} />
				{checked && <CheckedIcon />}
			</CheckBoxLabel>
		</CheckBoxContainer>
	);
};

export const FeedCheckbox = ({ id, checked, onChange, label }) => {
	return (
		<CheckBoxContainer>
			<CheckBoxLabel
				htmlFor={id}
				checked={checked}
				css={css`
					border: none;
					width: 20px;
					height: 20px;
				`}
			>
				<CheckBoxInput id={id} type="checkbox" checked={checked} onChange={onChange} />
				{checked ? <CheckedIcon /> : <UnCheckedIcon />}
			</CheckBoxLabel>
			{label && (
				<CheckBoxText
					htmlFor={id}
					css={css`
						font-family: 'Medium';
						font-size: 14px;
						line-height: 20px;
						padding-left: 3px;
					`}
				>
					{label}
				</CheckBoxText>
			)}
		</CheckBoxContainer>
	);
};
