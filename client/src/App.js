/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';

import './styles/app.scss';
import GlobalStyles from './GlobalStyles';
import { Routes } from './pages/Routes';

function Layout({ children }) {
	return (
		<div
			css={css`
				width: 100%;
				max-width: 1200px;
				display: flex;
				flex-direction: column;
				justify-content: center;
				align-items: center;
			`}
		>
			{children}
		</div>
	);
}
function App() {
	return (
		<>
			<Layout>
				<Routes />
			</Layout>
			<GlobalStyles />
		</>
	);
}

export default App;
