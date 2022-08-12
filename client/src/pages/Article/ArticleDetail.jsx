import React, { useState, useEffect } from 'react';
/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';
import { useNavigate } from 'react-router-dom';

import TopNavigation from '../../components/Common/TopNavigation/TopNavigation';
import ArticleDetailItem from '../../components/Feed/ArticleDetailItem';
import article_img from '../../assets/article_img.jpg';
import null_profile from '../../assets/null_profile_img.png';
import ArticleBottom from '../../components/Feed/ArticleBottom';
import { getArticleDetail } from '../../api/article';

const ArticleDetail = () => {
	const navigate = useNavigate();
	const [articleDetail, setArticleDetail] = useState(null);
	useEffect(() => {
		const fetch = async () => {
			const data = await getArticleDetail();
			setArticleDetail(data);
		};
		fetch();
	}, []);
	return (
		<>
			<TopNavigation
				backClick
				onBackClick={() => navigate(-1)}
				leftContent={<span>FITWEEN</span>}
			/>
			<div
				className="wrapper"
				style={{
					width: '100%',
					height: '100%',
					overflowY: 'scroll',
				}}
			>
				{/* 게시글 상세 정보 */}
				{/* todo : article img 받아와야 함 */}
				{articleDetail && (
					<ArticleDetailItem
						key={articleDetail.articleId}
						articleImg={article_img}
						userId={articleDetail.user.userID}
						title={articleDetail.title}
						updateTime={articleDetail.lastUpdateTime}
						content={articleDetail.content}
						profileImg={null_profile}
						isRent={articleDetail.lendStatus}
					/>
				)}
			</div>
			{/* 하단부 */}
			{articleDetail && (
				<ArticleBottom
					likeCnt={articleDetail.likesCount}
					rentPrice={articleDetail.price}
					userId={articleDetail.user.userID}
				/>
			)}
		</>
	);
};

export default ArticleDetail;
