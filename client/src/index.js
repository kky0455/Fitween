import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter } from 'react-router-dom';

import { worker } from './mocks/browser';
import './index.css';
import App from './App';
import { UserProvider } from './context/User/UserContext';

if (process.env.NODE_ENV === 'development') {
	worker.start();
}

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
	<React.StrictMode>
		<UserProvider>
			<BrowserRouter>
				<App />
			</BrowserRouter>
		</UserProvider>
	</React.StrictMode>,
);
