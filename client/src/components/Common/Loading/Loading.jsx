import React from 'react';
/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';
import colors from '../../../constants/colors';
import styled from 'styled-components';

const Spinner = styled('div')`
	width: 200px;
	height: 200px;
	display: inline-block;
	overflow: hidden;
	background: ${colors.background};
	@keyframes ldio-24y3gec0hkn {
		0% {
			opacity: 1;
			backface-visibility: hidden;
			transform: translateZ(0) scale(1.5, 1.5);
		}
		100% {
			opacity: 0;
			backface-visibility: hidden;
			transform: translateZ(0) scale(1, 1);
		}
	}
	.ldio-24y3gec0hkn div > div {
		position: absolute;
		width: 24px;
		height: 24px;
		border-radius: 50%;
		background: #6cc4a1;
		animation: ldio-24y3gec0hkn 0.9615384615384615s linear infinite;
	}
	.ldio-24y3gec0hkn div:nth-of-type(1) > div {
		left: 148px;
		top: 88px;
		animation-delay: -0.8413461538461539s;
	}
	.ldio-24y3gec0hkn > div:nth-of-type(1) {
		transform: rotate(0deg);
		transform-origin: 160px 100px;
	}
	.ldio-24y3gec0hkn div:nth-of-type(2) > div {
		left: 130px;
		top: 130px;
		animation-delay: -0.7211538461538461s;
	}
	.ldio-24y3gec0hkn > div:nth-of-type(2) {
		transform: rotate(45deg);
		transform-origin: 142px 142px;
	}
	.ldio-24y3gec0hkn div:nth-of-type(3) > div {
		left: 88px;
		top: 148px;
		animation-delay: -0.6009615384615384s;
	}
	.ldio-24y3gec0hkn > div:nth-of-type(3) {
		transform: rotate(90deg);
		transform-origin: 100px 160px;
	}
	.ldio-24y3gec0hkn div:nth-of-type(4) > div {
		left: 46px;
		top: 130px;
		animation-delay: -0.4807692307692307s;
	}
	.ldio-24y3gec0hkn > div:nth-of-type(4) {
		transform: rotate(135deg);
		transform-origin: 58px 142px;
	}
	.ldio-24y3gec0hkn div:nth-of-type(5) > div {
		left: 28px;
		top: 88px;
		animation-delay: -0.3605769230769231s;
	}
	.ldio-24y3gec0hkn > div:nth-of-type(5) {
		transform: rotate(180deg);
		transform-origin: 40px 100px;
	}
	.ldio-24y3gec0hkn div:nth-of-type(6) > div {
		left: 46px;
		top: 46px;
		animation-delay: -0.24038461538461536s;
	}
	.ldio-24y3gec0hkn > div:nth-of-type(6) {
		transform: rotate(225deg);
		transform-origin: 58px 58px;
	}
	.ldio-24y3gec0hkn div:nth-of-type(7) > div {
		left: 88px;
		top: 28px;
		animation-delay: -0.12019230769230768s;
	}
	.ldio-24y3gec0hkn > div:nth-of-type(7) {
		transform: rotate(270deg);
		transform-origin: 100px 40px;
	}
	.ldio-24y3gec0hkn div:nth-of-type(8) > div {
		left: 130px;
		top: 46px;
		animation-delay: 0s;
	}
	.ldio-24y3gec0hkn > div:nth-of-type(8) {
		transform: rotate(315deg);
		transform-origin: 142px 58px;
	}
	.ldio-24y3gec0hkn {
		width: 100%;
		height: 100%;
		position: relative;
		transform: translateZ(0) scale(1);
		backface-visibility: hidden;
		transform-origin: 0 0; /* see note above */
	}
	.ldio-24y3gec0hkn div {
		box-sizing: content-box;
	}
`;

// todo :
const Loading = ({ msg }) => {
	return (
		<>
			<Spinner>
				<div className="ldio-24y3gec0hkn">
					<div>
						<div />
					</div>
					<div>
						<div />
					</div>
					<div>
						<div />
					</div>
					<div>
						<div />
					</div>
					<div>
						<div />
					</div>
					<div>
						<div />
					</div>
					<div>
						<div />
					</div>
					<div>
						<div />
					</div>
				</div>
			</Spinner>
			{msg && <span>{msg}</span>}
		</>
	);
};

export default Loading;
