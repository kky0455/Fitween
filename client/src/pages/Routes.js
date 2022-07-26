import { Route, Routes as ReactRouterRoutes, Navigate } from 'react-router-dom';
import ArticleDetail from './Article/ArticleDetail';
import ArticleModify from './Article/ArticleModify';
import ArticleRegist from './Article/ArticleRegist';
import ChatList from './Chat/ChatList';
import ChatRoom from './Chat/ChatRoom';
import DummyPage from './DummyPage/DummyPage';
import CallBackKakao from './Index/CallBackKakao';
import Index from './Index/Index';
import JoinIndex from './Join/JoinIndex';
import JoinInfo from './Join/JoinInfo';
import JoinTown from './Join/JoinTown';
import Like from './Like/Like';
import Main from './Main/Main';
import ProfileInfoModify from './Profile/ProfileInfoModify';
import ProfileTownModify from './Profile/ProfileTownModify';
import ProfileUser from './Profile/ProfileUser';

// todo : 회원가입 쪽 설계 다시 고민
const Join = () => {
	return (
		<ReactRouterRoutes>
			{/* 회원가입 안내페이지 */}
			<Route path="index" element={<JoinIndex />} />
			{/* 정보 입력 */}
			<Route path="info" element={<JoinInfo />} />
			{/* 동네 입력 */}
			<Route path="town" element={<JoinTown />} />
			<Route path="*" element={<Navigate replace to="/" />} />
		</ReactRouterRoutes>
	);
};

const Article = () => {
	return (
		<ReactRouterRoutes>
			{/* 글 상세 */}
			<Route path=":articleId" element={<ArticleDetail />} />
			{/* 글 등록 */}
			<Route path="regist" element={<ArticleRegist />} />
			{/* 글 수정 */}
			<Route path="modify/:articleId" element={<ArticleModify />} />
			<Route path="*" element={<Navigate replace to="/" />} />
		</ReactRouterRoutes>
	);
};

const Chat = () => {
	return (
		<ReactRouterRoutes>
			{/* 채팅리스트 */}
			<Route path="list" element={<ChatList />} />
			{/* 채팅상세 */}
			<Route path=":chatRoomId" element={<ChatRoom />} />
			<Route path="*" element={<Navigate replace to="list" />} />
		</ReactRouterRoutes>
	);
};

const Profile = () => {
	return (
		<ReactRouterRoutes>
			{/* 프로필 */}
			<Route path=":userId" element={<ProfileUser />} />
			{/* 내정보수정 */}
			<Route path="modify/info/:userId" element={<ProfileInfoModify />} />
			{/* 내동네수정 */}
			<Route path="modify/town/:userId" element={<ProfileTownModify />} />
			<Route path="*" element={<Navigate replace to="/" />} />
		</ReactRouterRoutes>
	);
};

export const Routes = () => {
	return (
		<ReactRouterRoutes>
			{/* 테스트용 더미 페이지 */}
			<Route path="dummy" element={<DummyPage />} />

			{/* 초기화면  */}
			<Route path="/" element={<Index />} />
			{/* 카카오 로그인 콜백  */}
			<Route path="/kakao" element={<CallBackKakao />} />
			<Route path="/oauth/redirect" element={<CallBackKakao />} />

			{/* 추가정보입력 페이지 */}
			<Route path="join/*" element={<Join />} />

			{/* 메인페이지 */}
			<Route path="main" element={<Main />} />
			{/* 찜피드 */}
			<Route path="like" element={<Like />} />

			{/* 글  */}
			<Route path="article/*" element={<Article />} />

			{/* 채팅 */}
			<Route path="chat/*" element={<Chat />} />

			{/* 프로필 */}
			<Route path="profile/*" element={<Profile />} />

			<Route path="*" element={<Navigate replace to="dummy" />} />
		</ReactRouterRoutes>
	);
};
