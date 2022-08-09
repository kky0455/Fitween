import React from 'react';
import Slider from 'react-slick';
import './slick.scss';
import './slick-theme.scss';
import styled from 'styled-components';
import { v4 as uuid } from 'uuid';
// /** @jsxImportSource @emotion/react */
// import { css } from '@emotion/react';

import modify_img from '../../../assets/modify_img.png';
import fitweenLogoBg from '../../../assets/FitweenLogoBg.png';
// import { ReactComponent as PrevIcon } from '../../../assets/arrow_back.svg';
// const PrevArrow = ({ className, style, onClick }) => {
// 	return (
// 		<div
// 			className={className}
// 			css={css`
// 				opacity: 0.5;
// 				&::before {
// 					display: none;
// 				}
// 				&:hover {
// 					opacity: 1;
// 				}
// 			`}
// 			onClick={onClick}
// 		>
// 			<PrevIcon />
// 		</div>
// 	);
// };

const StyledSlider = styled(Slider)`
	width: 100%;
	height: 100%;
	.slick-list {
		height: 100%;
		width: 100%;
	}
	.slick-dots {
		bottom: 0;
		li {
			width: 10px;
		}
		li > button::before {
			font-size: 30px;
			opacity: 0.25;
		}
		li.slick-active > button::before {
			opacity: 0.75;
		}
	}
	.slick-prev {
		left: 0;
		z-index: 1;
	}
	.slick-next {
		right: 0;
		z-index: 1;
	}
`;
const Carousel = ({ imgSrcList, style, contentHeight, ...rest }) => {
	const settings = {
		arrows: false,
		dots: true,
		infinite: true,
		speed: 500,
		slidesToShow: 1,
		slidesToScroll: 1,
		// prevArrow: <PrevArrow />,
	};
	return (
		<div style={{ ...style }} {...rest}>
			<StyledSlider {...settings}>
				{imgSrcList.length ? (
					imgSrcList.map(src => (
						<div key={uuid()}>
							<div style={{ height: contentHeight }}>
								<img
									src={src}
									alt=""
									style={{
										width: '100%',
										height: '100%',
									}}
								/>
							</div>
						</div>
					))
				) : (
					<div>
						<div style={{ height: contentHeight }}>
							<img
								src={modify_img}
								alt=""
								style={{
									width: '100%',
									height: '100%',
								}}
							/>
						</div>
					</div>
				)}
			</StyledSlider>
		</div>
	);
};

export default Carousel;
