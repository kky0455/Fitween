import React from 'react';
/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';
import colors from '../../constants/colors';

const Message = ({ isMine, message, isRead, sendTime }) => {
	return (
		<div
			className="partner-message-wrapper"
			css={css`
				display: flex;
				flex-direction: ${isMine ? 'row-reverse' : 'row'};
				gap: 10px;
			`}
		>
			<div
				className="message"
				css={css`
					padding: 10px 12px;
					max-width: 70%;
					background-color: ${isMine ? colors.green100 : colors.green50};
					color: ${isMine ? colors.white : colors.text};
					border-radius: 20px;
					font-size: 14px;
					line-height: 20px;
					font-family: 'Regular';
				`}
			>
				<span>{message}</span>
			</div>
			<div
				className="send-date"
				css={css`
					display: flex;
					flex-direction: column;
					justify-content: flex-end;
					font-size: 11px;
					line-height: 15px;
					font-family: 'Regular';
					text-align: ${isMine ? 'right' : 'left'};
					color: ${colors.grey200};
				`}
			>
				{isRead && <span>읽음</span>}
				<span>{sendTime}</span>
			</div>
		</div>
	);
};

export default Message;
