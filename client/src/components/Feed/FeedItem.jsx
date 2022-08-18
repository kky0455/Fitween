import React, { useState } from 'react';
/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';
import { useNavigate } from 'react-router-dom';

import FeedProfile from '../../components/Feed/FeedProfile';
import null_profile from '../../assets/null_profile_img.png';
import { ReactComponent as Heart } from '../../assets/heart_active.svg';
import { doArticleLike, cancelArticleLike } from '../../api/article';

const FeedItem = ({
	nickname,
	articleId,
	articleImg,
	title,
	rentPrice,
	isLiked,
	likeCnt,
	isRent,
	userId,
	removeItem,
}) => {
	const [liked, setLiked] = useState(isLiked);
	const [count, setCount] = useState(likeCnt);
	const navigate = useNavigate();
	const rentPriceComma = rentPrice.toLocaleString();
	const doHeartClickHandler = async e => {
		e.stopPropagation();
		const data = await doArticleLike(articleId);
		if (data === '좋아요 성공') {
			setLiked(!liked);
			setCount(count + 1);
		}
	};

	const cancelHeartClickHandler = async e => {
		e.stopPropagation();
		const data = await cancelArticleLike(articleId);
		if (data === '좋아요 취소') {
			setLiked(!liked);
			setCount(count - 1);
			removeItem && removeItem(articleId);
		}
	};

	return (
		<div
			css={css`
				flex-direction: column;
				display: flex;
				justify-content: center;
				align-items: center;
				padding: 0px 30px 20px;
			`}
		>
			{/* 상단부 - 작성자 정보, 대여가능 여부 */}
			<FeedProfile userId={userId} imgSrc={null_profile} nickname={nickname} isRent={isRent} />
			{/* 게시글 이미지 */}
			<div
				css={css`
					width: 100%;
				`}
				onClick={() =>
					navigate(`/article/${articleId}`, {
						state: { prevRouter: `/main` },
					})
				}
			>
				<img
					src={articleImg}
					alt=""
					css={css`
						display: flex;
						justify-content: center;
						align-items: center;
						width: 100%;
						height: 200px;
						object-fit: cover;
						border-left: 1.5px solid #f0f0f0;
						border-right: 1.5px solid #f0f0f0;
					`}
				/>
				{/* 하단부 - 제목, 가격, 좋아요 수, 시간 */}
				<div
					css={css`
						display: flex;
						flex-direction: row;
						justify-content: space-between;
						align-items: center;
						width: 100%;
						box-sizing: border-box;
						border: 1.5px solid #f0f0f0;
						border-radius: 0px 0px 20px 20px;
					`}
				>
					{/* 하단부 왼쪽 - 제목, 대여 가격*/}
					<div
						css={css`
							display: flex;
							flex-direction: column;
							padding: 20px 0px 20px 20px;
							width: 70%;
						`}
					>
						{/* 제목 */}
						<h1
							className="fw-500 fs-16"
							css={css`
								overflow: hidden;
								text-overflow: ellipsis;
								word-break: break-word;
								height: 20px;
								line-height: 18px;
								display: -webkit-box;
								-webkit-line-clamp: 1; /* 라인수 */
								-webkit-box-orient: vertical;
							`}
						>
							{title}
						</h1>
						{/* 대여 가격 */}
						<div>
							<span className="fw-400 fs-14" style={{ lineHeight: '20.27px' }}>
								일 {rentPriceComma}원
							</span>
						</div>
					</div>
					{/* 하단부 오른쪽 - 좋아요*/}
					<div
						css={css`
							display: flex;
							flex-direction: column;
							justify-content: center;
							align-items: center;
							padding: 20px;
							width: 30%;
							max-width: 90px;
						`}
						onClick={liked ? cancelHeartClickHandler : doHeartClickHandler}
					>
						{/* 찜 */}
						<Heart fill={liked ? 'red' : 'white'} stroke={liked ? 'red' : 'black'} />
						<div className="fw-500 fs-16" style={{ lineHeight: '22.59px' }}>
							{count}
						</div>
					</div>
				</div>
			</div>
		</div>
	);
};

export default FeedItem;
