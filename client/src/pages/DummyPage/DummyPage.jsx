import React, { useEffect } from 'react';
/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';
import API from '../../api';
import styled from 'styled-components';
import colors from '../../constants/colors';

const StyledEl = styled.span`
	font-family: Urbanist;
	font-size: 20px;
	color: ${colors.text};
`;

const DummyPage = () => {
	useEffect(() => {
		const fetch = async () => {
			const res = await API.get('/test');
			console.log(res);
		};
		fetch();
	}, []);
	return (
		<>
			<span
				css={css`
					font-family: Inter;
					font-size: 30px;
					color: ${colors.text};
				`}
			>
				dummy
			</span>
			<StyledEl>asd</StyledEl>
			<span>basic font</span>
			<span
				css={css`
					font-family: Inter;
				`}
			>
				asdasd
			</span>
		</>
	);
};

export default DummyPage;
