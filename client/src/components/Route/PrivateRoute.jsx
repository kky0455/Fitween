import React from 'react';
import { Outlet, Navigate } from 'react-router-dom';

import { getRefreshToken } from '../../storage/Cookie';

const PrivateRoute = () => {
	const refreshToken = getRefreshToken();

	return refreshToken ? <Outlet /> : <Navigate to="/" />;
};

export default PrivateRoute;
