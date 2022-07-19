import { createGlobalStyle } from 'styled-components';
import reset from 'styled-reset';
import './styles/colors.scss';
const GlobalStyles = createGlobalStyle`
${reset}
	a {
        text-decoration : none;
        color : inherit;
  }
	body {
	        font-family : Urbanist,apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell , 'Helvetica Neue', sans-serif;
	        font-size : 10px;
	        background-color : $background;
	        overflow-x : hidden;
          display: flex;
          justify-content: center;
          height: auto;
	}
`;
export default GlobalStyles;
