import React from 'react';
/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';

import colors from '../../constants/colors';

const RoomItem = ({ imgSrc, partnerName, message, unreadCnt, onClickHander }) => {
	return (
		<div
			css={css`
				display: grid;
				grid-template-columns: 50px 1fr 50px;
				grid-column-gap: 10px;
				align-items: center;
			`}
			onClick={onClickHander}
		>
			<div
				css={css`
					width: 40px;
					height: 40px;
					justify-self: center;
					border-radius: 30%;
					overflow: hidden;
				`}
			>
				<img
					src={imgSrc}
					alt=""
					css={css`
						width: 100%;
						height: 100%;
						object-fit: cover;
					`}
				/>
			</div>
			<div>
				<h3 className="fw-700 fs-15" style={{ lineHeight: '24px' }}>
					{partnerName}
				</h3>
				<span
					className="fw-400 fs-14"
					css={css`
						overflow: hidden;
						text-overflow: ellipsis;
						word-break: break-word;
						height: 40px;
						line-height: 20px;
						display: -webkit-box;
						-webkit-line-clamp: 2; /* 라인수 */
						-webkit-box-orient: vertical;
					`}
				>
					{message}
				</span>
			</div>
			<div
				css={css`
					display: flex;
					justify-content: center;
					align-items: center;
				`}
			>
				{unreadCnt > 0 && (
					<div
						css={css`
							background-color: ${colors.green100};
							width: 30px;
							height: 30px;
							border-radius: 50%;
							display: flex;
							justify-content: center;
							align-items: center;
						`}
					>
						<span
							css={css`
								color: white;
								text-align: center;
							`}
						>
							{unreadCnt}
						</span>
					</div>
				)}
			</div>
		</div>
	);
};

export default RoomItem;
