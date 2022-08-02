import React, { useState } from 'react';
/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';

import TopNavigation from '../../components/Common/TopNavigation/TopNavigation';
import FeedItem from '../../components/Feed/FeedItem';
import BottomNavigation from '../../components/Common/BottomNavigation/BottomNavigation';
import feed_article_img from '../../assets/feed_article_img.png';
import heart_active from '../../assets/heart_active.svg';

const Main = () => {
	const [userId, setUserId] = useState('userid');
	return (
		<>
			<TopNavigation
				leftContent={<span>FITWEEN</span>}
				rightMenu={
					<>
						<button>+</button>
						<button>sch</button>
						<button>ring</button>
					</>
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
				<FeedItem
					userId="userID"
					articleImg={feed_article_img}
					title="제목입니다. 옷장 대방출합니다. 많관부"
					rentPrice="4000"
					heartIcon={heart_active}
					likeCnt="129"
					createTime="1시간 전"
				/>
			</div>
			<BottomNavigation />
		</>
	);
};

export default Main;
