import React, { useState, useEffect } from 'react';
/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';
import { Link } from 'react-router-dom';

import TopNavigation from '../../components/Common/TopNavigation/TopNavigation';
import FeedItem from '../../components/Feed/FeedItem';
import BottomNavigation from '../../components/Common/BottomNavigation/BottomNavigation';
import feed_article_img from '../../assets/feed_article_img.png';
import heart_active from '../../assets/heart_active.svg';
import logo_h from '../../assets/logo_h.png';
import add_box from '../../assets/add_box.svg';
import { getArticleList } from '../../api/article';

const LeftContent = ({}) => {
	return (
		<Link to="/main">
			<img
				src={logo_h}
				alt=""
				css={css`
					display: flex;
					align-items: center;
				`}
			/>
		</Link>
	);
};

const Main = () => {
	const [userId, setUserId] = useState('userid');
	const [articleList, setArticleList] = useState(null);
	useEffect(() => {
		const fetch = async () => {
			const data = await getArticleList();
			setArticleList(data);
			console.log(data);
		};
		fetch();
	}, []);
	return (
		<>
			<TopNavigation
				leftContent={<LeftContent />}
				rightMenu={
					<Link to="/article/regist">
						<img
							src={add_box}
							alt=""
							css={css`
								/* padding-right: 26px; */
								width: 27px;
								height: 27px;
								margin-right: 20px;
							`}
						/>
					</Link>
				}
			/>
			<div
				className="wrapper"
				style={{
					width: '100%',
					height: '100%',
					overflowY: 'scroll',
				}}
			>
				{/* 피드item */}
				{articleList &&
					articleList.map(article => (
						<FeedItem
							key={article.articleId}
							// todo : userId, aritlceImg, isRent API에서 받아와야 함
							userId="userID"
							articleId={article.articleId}
							// articleImg={article.articleImgList}
							articleImg={feed_article_img}
							title={article.articleTitle}
							rentPrice={article.articlePrice}
							heartIcon={heart_active}
							likeCnt={article.articleLikeCount}
							createTime={article.articleLastUpdateTime}
							isRent={true}
						/>
					))}
			</div>
			<BottomNavigation />
		</>
	);
};

export default Main;
