/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';

import './styles/app.scss';
import GlobalStyles from './GlobalStyles';
import { Routes } from './pages/Routes';
import { useGlobalContext } from './contexts/GlobalContext';

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
	const { hasTop, hasBottom } = useGlobalContext();

	return (
		<>
			<Layout>
				<Routes />
			</Layout>
			<GlobalStyles hasTop={hasTop} hasBottom={hasBottom} />
		</>
	);
}

export default App;
