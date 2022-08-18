import { Route, Routes as ReactRouterRoutes, Navigate } from 'react-router-dom';

import PrivateRoute from '../components/Route/PrivateRoute';
import About from './About/About';
import ArticleDetail from './Article/ArticleDetail';
import ArticleModify from './Article/ArticleModify';
import ArticleRegist from './Article/ArticleRegist';
import ChatList from './Chat/ChatList';
import ChatRoom from './Chat/ChatRoom';
import Index from './Index/Index';
import Redirect from './Index/Redirect';
import Join from './Join/Join';
import Like from './Like/Like';
import Main from './Main/Main';
import ProfileInfoModify from './Profile/ProfileInfoModify';
import ProfileTownModify from './Profile/ProfileTownModify';
import ProfileUser from './Profile/ProfileUser';

const Article = () => {
	return (
		<ReactRouterRoutes>
			{/* 글 상세 */}
			<Route exact path="/" element={<PrivateRoute />}>
				<Route path=":articleId" element={<ArticleDetail />} />
			</Route>
			{/* 글 등록 */}
			<Route exact path="/" element={<PrivateRoute />}>
				<Route path="regist" element={<ArticleRegist />} />
			</Route>
			{/* 글 수정 */}
			<Route exact path="/" element={<PrivateRoute />}>
				<Route path="modify/:articleId" element={<ArticleModify />} />
			</Route>
			<Route path="*" element={<Navigate replace to="/" />} />
		</ReactRouterRoutes>
	);
};

const Chat = () => {
	return (
		<ReactRouterRoutes>
			{/* 채팅리스트 */}
			<Route exact path="/" element={<PrivateRoute />}>
				<Route path="list" element={<ChatList />} />
			</Route>
			{/* 채팅상세 */}
			<Route exact path="/" element={<PrivateRoute />}>
				<Route path="room" element={<ChatRoom />} />
			</Route>
			<Route path="*" element={<Navigate replace to="list" />} />
		</ReactRouterRoutes>
	);
};

const Profile = () => {
	return (
		<ReactRouterRoutes>
			{/* 프로필 */}
			<Route exact path="/" element={<PrivateRoute />}>
				<Route path=":userId" element={<ProfileUser />} />
			</Route>
			{/* 내정보수정 */}
			<Route exact path="/" element={<PrivateRoute />}>
				<Route path="modify/info/:userId" element={<ProfileInfoModify />} />
			</Route>
			{/* 내동네수정 */}
			<Route exact path="/" element={<PrivateRoute />}>
				<Route path="modify/town/:userId" element={<ProfileTownModify />} />
			</Route>
			<Route path="*" element={<Navigate replace to="/" />} />
		</ReactRouterRoutes>
	);
};

export const Routes = () => {
	return (
		<ReactRouterRoutes>
			{/* 초기화면  */}
			<Route exact path="/" element={<Index />} />
			{/* about 소개 페이지 */}
			<Route exact path="/about" element={<About />} />
			{/* 소셜 로그인 리다이렉트  */}
			<Route exact path="/oauth/redirect" element={<Redirect />} />

			{/* 회원가입 페이지 */}
			<Route exact path="join/*" element={<Join />} />
			{/* 메인페이지 */}
			<Route exact path="/" element={<PrivateRoute />}>
				<Route path="main" element={<Main />} />
			</Route>
			{/* 찜피드 */}
			<Route exact path="/" element={<PrivateRoute />}>
				<Route path="like" element={<Like />} />
			</Route>
			{/* 글  */}
			<Route exact path="/" element={<PrivateRoute />}>
				<Route path="article/*" element={<Article />} />
			</Route>
			{/* 채팅 */}
			<Route exact path="/" element={<PrivateRoute />}>
				<Route path="chat/*" element={<Chat />} />
			</Route>
			{/* 프로필 */}
			<Route exact path="/" element={<PrivateRoute />}>
				<Route path="profile/*" element={<Profile />} />
			</Route>
			<Route path="*" element={<Navigate replace to="/" />} />
		</ReactRouterRoutes>
	);
};
