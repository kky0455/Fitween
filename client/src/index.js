import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter } from 'react-router-dom';

import './index.css';
import App from './App';
import { UserProvider } from './contexts/User/UserContext';

// msw
import { GlobalContextProvider } from './contexts/GlobalContext';
import { onRefresh } from './utils/auth';

const root = ReactDOM.createRoot(document.getElementById('root'));

const startRendering = async () => {
	try {
		await onRefresh();
	} catch (err) {
		throw err;
	} finally {
		root.render(
			// <React.StrictMode>
			<GlobalContextProvider>
				<UserProvider>
					<BrowserRouter>
						<App />
					</BrowserRouter>
				</UserProvider>
			</GlobalContextProvider>,
			// </React.StrictMode>,
		);
	}
};

startRendering();
