import React, { useState, useEffect } from 'react';
/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';
import { Link } from 'react-router-dom';

import TopNavigation from '../../components/Common/TopNavigation/TopNavigation';
import FeedItem from '../../components/Feed/FeedItem';
import BottomNavigation from '../../components/Common/BottomNavigation/BottomNavigation';
import feed_article_img from '../../assets/feed_article_img.png';
import logo_h from '../../assets/logo_h.png';
import add_box from '../../assets/add_box.svg';
import { getArticleList } from '../../api/article';
import FeedCategory from '../../components/Category/FeedCategory';

const LeftContent = () => {
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
	const [articleList, setArticleList] = useState(null);
	const [activeCategoryCode, setActiveCategoryCode] = useState('all');
	useEffect(() => {
		const fetch = async () => {
			try {
				const data = await getArticleList(activeCategoryCode);
				setArticleList(data);
			} catch (err) {
				console.log(err);
			}
		};
		fetch();
	}, [activeCategoryCode]);
	const categoryClickHandler = async e => {
		setActiveCategoryCode(e.target.id);
	};
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
				<FeedCategory
					activeCategoryCode={activeCategoryCode}
					onClickHandler={categoryClickHandler}
				/>
				{/* 피드item */}
				{articleList &&
					articleList.map(article => (
						<FeedItem
							key={article.articleIdx}
							userId={article.userId}
							nickname={article.nickname}
							articleId={article.articleIdx}
							articleImg={
								article.feedArticleImg.length > 0 &&
								article.feedArticleImg[0].baseUrl + article.feedArticleImg[0].img
							}
							title={article.title}
							rentPrice={article.price}
							isLiked={article.likeStatus}
							likeCnt={article.likesCount}
							isRent={article.lendStatus}
						/>
					))}
			</div>
			<BottomNavigation />
		</>
	);
};

export default Main;
