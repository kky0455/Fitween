import React from 'react';
import { Outlet, Navigate } from 'react-router-dom';

import { useUserState } from '../../contexts/User/UserContext';

const PrivateRoute = () => {
	const { loginedUserId } = useUserState();

	return loginedUserId ? <Outlet /> : <Navigate to="/" />;
};

export default PrivateRoute;
