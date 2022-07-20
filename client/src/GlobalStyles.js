import { createGlobalStyle } from 'styled-components';
import reset from 'styled-reset';
import './styles/colors.scss';
import common from './constants/commonStyle';
const GlobalStyles = createGlobalStyle`
${reset}
	a {
        text-decoration : none;
        color : inherit;
  }
	body {
	        font-family : Urbanist,apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell , 'Helvetica Neue', sans-serif;
	        font-size : 15px;
	        background-color : $background;
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
        }
  *{
            box-sizing: border-box;
   }
  #root {
           overflow-x: hidden;
          width: 100%;
          display: flex;
          justify-content: center;
          height: 100vh;
  }
`;
export default GlobalStyles;
