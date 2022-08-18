import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter } from 'react-router-dom';

import './index.css';
import App from './App';
import * as serviceWorkerResgistration from './serviceWorkerRegistration';
import { UserProvider } from './contexts/User/UserContext';

// msw
import { serviceWorker } from './mocks/browser';
import { GlobalContextProvider } from './contexts/GlobalContext';
import { onRefresh } from './utils/auth';

serviceWorker.start({ onUnhandledRequest: 'bypass' });
serviceWorkerResgistration.register();

const root = ReactDOM.createRoot(document.getElementById('root'));

const startRendering = async () => {
	try {
		alert('startRendering');
		await onRefresh();

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
	} catch (err) {
		throw err;
	}
};

startRendering();
