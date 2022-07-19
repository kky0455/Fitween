import './styles/app.scss';
import GlobalStyles from './GlobalStyles';
import { Routes } from './pages/Routes';
/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';
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
				height: auto;
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
