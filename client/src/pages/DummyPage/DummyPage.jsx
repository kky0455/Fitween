import React, { useEffect } from 'react';
/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';
import API from '../../api';
import styled from 'styled-components';
import colors from '../../constants/colors';
import TopNavigation from '../../components/Common/TopNavigation/TopNavigation';

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
			<TopNavigation
				backClick
				centerContent={<span>Fitwell</span>}
				rightMenu={
					<>
						<button>asd</button>
						<button>asd</button>
						<button>asd</button>
					</>
				}
			/>
			<div style={{ height: '400px' }}>옷 공유 합니다 1234</div>
			<div style={{ height: '400px' }}>testseet</div>
			<div style={{ height: '400px' }}>옷 공유 합니다</div>
			<div style={{ height: '400px' }}>옷 공유 합니다</div>
		</>
	);
};

export default DummyPage;
