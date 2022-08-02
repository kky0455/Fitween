import React from 'react';
/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';
import { useParams } from 'react-router';

import TopNavigation from '../../components/Common/TopNavigation/TopNavigation';
import ArticleDetailItem from '../../components/Feed/ArticleDetailItem';
import Button from '../../components/Common/Button/Button';
import common from '../../constants/commonStyle';
import colors from '../../constants/colors';
import article_img from '../../assets/article_img.jpg';
import null_profile from '../../assets/null_profile_img.png';
import heart_active from '../../assets/heart_active.svg';
import ArticleBottom from '../../components/Feed/ArticleBottom';

const ArticleDetail = () => {
	const { articleId } = useParams();
	return (
		<>
			<TopNavigation
				backClick
				onBackClick={() => {
					alert('클릭');
				}}
				leftContent={<span>FITWEEN</span>}
			/>
			<div
				className="wrapper"
				style={{
					width: '100%',
					height: '100%',
				}}
			>
				{/* 게시글 상세 정보 */}
				<ArticleDetailItem
					articleImg={article_img}
					userId="userID"
					title="제목입니다.확인해보세요"
					createTime="1시간 전"
					content="옷 대여를 원하시는 분들은 채팅 주세요."
					profileImg={null_profile}
					isRent
				/>
			</div>
			{/* 하단부 */}
			<ArticleBottom likeCnt="126" rentPrice="4000" />
		</>
	);
};

export default ArticleDetail;
