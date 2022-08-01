import { createGlobalStyle } from 'styled-components';
import reset from 'styled-reset';
import './styles/colors.scss';
import common from './constants/commonStyle';
const GlobalStyles = createGlobalStyle`
${reset}
          -ms-overflow-style: none !important; 
          scrollbar-width: none !important; 
          ::-webkit-scrollbar {
            display: none !important; 
            width: 0 !important;  
            height: 0 !important;
            background: white !important;  
            -webkit-appearance: none !important;
            appearance: none !important;
          }
	a {
        text-decoration : none;
        color : inherit;
  }
	body {
          height: 100vh;
	        font-family : apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell , 'Helvetica Neue', sans-serif;
	        font-size : 15px;
	        background-color : $background;
          
          padding-top: ${({ hasTop }) => (hasTop ? common.topHeaderHeight : 0)};
          padding-bottom: ${({ hasBottom }) => (hasBottom ? common.bottomHeaderHeight : 0)};
          overflow: hidden;
        }
  *{
            box-sizing: border-box;
   }
  #root {
           overflow: hidden;
          width: 100%;
          display: flex;
          justify-content: center;
          height: 100%;
  }
`;
export default GlobalStyles;
