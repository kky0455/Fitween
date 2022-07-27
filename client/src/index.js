import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import { BrowserRouter } from 'react-router-dom';

// msw
import { serviceWorker } from './mocks/browser';
import { GlobalContextProvider } from './contexts/GlobalContext';
serviceWorker.start({ onUnhandledRequest: 'bypass' });

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
	<React.StrictMode>
		<GlobalContextProvider>
			<BrowserRouter>
				<App />
			</BrowserRouter>
		</GlobalContextProvider>
	</React.StrictMode>,
);
