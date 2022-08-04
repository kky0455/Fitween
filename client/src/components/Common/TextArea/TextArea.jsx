import React, { useCallback, useRef } from 'react';
/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';
import colors from '../../../constants/colors';
import getByte from '../../../utils/getByte';

const TextArea = ({ error, maxByte, value, onChange, ...rest }) => {
	const textAreaRef = useRef(null);
	const handleResizeHeight = useCallback(() => {
		if (textAreaRef === null || textAreaRef.current === null) {
			return;
		}
		textAreaRef.current.style.height = 'auto';
		textAreaRef.current.style.height = textAreaRef.current.scrollHeight + 4 + 'px';
	}, []);
	return (
		<div
			css={css`
				position: relative;
				display: flex;
				flex-direction: column;
				align-items: flex-end;
				width: 100%;
				transition: background-color 0.2s ease;
				background-color: ${colors.background};
				border-radius: 12px;
				align-items: center;
				box-shadow: inset 0 0 0 1px rgba(0, 0, 0, 0.02);
				color: ${colors.text};
				height: auto;
			`}
		>
			<textarea
				css={css`
					width: 100%;
					border: 2px solid ${error ? colors.warn : colors.black};
					border-radius: 20px;
					padding: 24px;
					font-size: 18px;
					font-family: 'Medium';
					resize: none;
					&:focus {
						outline: none;
						border: 2px solid ${error ? colors.warn : colors.green100};
					}
				`}
				ref={textAreaRef}
				value={value}
				onChange={onChange}
				onInput={handleResizeHeight}
				{...rest}
			/>
			{maxByte && (
				<span
					css={css`
						align-self: flex-end;
						font-size: 12px;
						padding: 10px;
						font-family: 'Regular';
						color: ${colors.grey200};
					`}
				>
					{getByte(value)} / {maxByte}
				</span>
			)}
		</div>
	);
};

export default TextArea;
