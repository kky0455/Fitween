import { Route, Routes as ReactRouterRoutes, Navigate } from 'react-router-dom';
import DummyPage from './DummyPage/DummyPage';

export const Routes = () => {
	return (
		<ReactRouterRoutes>
			<Route path="/" element={<DummyPage />} />
			<Route path="*" element={<Navigate replace to="/" />} />
		</ReactRouterRoutes>
	);
};
