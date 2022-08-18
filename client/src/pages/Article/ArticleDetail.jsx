import React, { useState, useEffect } from 'react';
import { useLocation, useNavigate, useParams } from 'react-router-dom';

import TopNavigation from '../../components/Common/TopNavigation/TopNavigation';
import ArticleDetailItem from '../../components/Feed/ArticleDetailItem';
import null_profile from '../../assets/null_profile_img.png';
import ArticleBottom from '../../components/Feed/ArticleBottom';
import { getArticleDetail } from '../../api/article';

const ArticleDetail = () => {
	const { articleId } = useParams();
	const { state } = useLocation();
	const navigate = useNavigate();
	const [articleDetail, setArticleDetail] = useState(null);
	useEffect(() => {
		const fetch = async () => {
			const data = await getArticleDetail(articleId);
			setArticleDetail(data);
		};
		fetch();
	}, []);
	return (
		<>
			<TopNavigation
				backClick
				onBackClick={() => navigate(state.prevRouter)}
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
				{articleDetail && (
					<ArticleDetailItem
						key={articleDetail.Id}
						articleImg={articleDetail.feedImg}
						nickname={articleDetail.nickname}
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
					isLiked={articleDetail.likeStatus}
					likeCnt={articleDetail.likesCount}
					rentPrice={articleDetail.price}
					writerId={articleDetail.writerId}
				/>
			)}
		</>
	);
};

export default ArticleDetail;
