import React from 'react';
import { useNavigate } from 'react-router-dom';
import TopNavigation from '../../components/Common/TopNavigation/TopNavigation';

const JoinTown = () => {
	const navigate = useNavigate();
	return (
		<TopNavigation
			backClick
			onBackClick={() => {
				navigate(-1);
			}}
		/>
	);
};

export default JoinTown;
