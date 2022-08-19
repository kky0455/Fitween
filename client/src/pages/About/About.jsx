import React from 'react';
/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';

import TopNavigation from '../../components/Common/TopNavigation/TopNavigation';
import { useNavigate } from 'react-router-dom';
import colors from '../../constants/colors';
import infoscreen from '../../assets/infoscreen.png';
import feedscreen from '../../assets/feedscreen.png';
import çhatscreen from '../../assets/chatscreen.png';

const About = () => {
	const navigate = useNavigate();
	return (
		<>
			<TopNavigation backClick onBackClick={() => navigate(-1)} centerContent="about FITWEEN" />
			<div
				className="Wrapper"
				style={{
					width: '100%',
					height: '100%',
					overflowY: 'scroll',
				}}
			>
				<div
					css={css`
						display: flex;
						flex-direction: column;
						padding: 35px;
					`}
				>
					<div>
						<div
							css={css`
								font-family: 'bold';
								font-size: 30px;
								line-height: 43.44px;
								margin-bottom: 10px;
							`}
						>
							나와 비슷한 취향의
							<br />
							가까운 이웃을
							<br />
							만나보세요
						</div>
						<div
							css={css`
								font-family: 'regular';
								font-size: 20px;
								line-height: 28.96px;
								margin-bottom: 20px;
							`}
						>
							피트윈은 나와 비슷한 신체 사이즈의 동네 이웃과 옷장을 공유할 수 있도록 매칭해주는
							플랫폼입니다.
						</div>
					</div>
					<div>
						<div
							css={css`
								font-family: 'bold';
								font-size: 30px;
								line-height: 43.44px;
								margin-bottom: 10px;
							`}
						>
							신체 데이터 기반과
							<br />
							동네인증으로
							<br />
							믿을 수 있는 이웃들과
							<br />
							함께해보세요
						</div>
						<div
							css={css`
								font-family: 'regular';
								font-size: 20px;
								line-height: 28.96px;
								margin-bottom: 15px;
							`}
						>
							가입 시 입력한 신체 데이터를 기반으로 매칭된 사람들을 피드에서 만날 수 있어요! 또한,
							동네 인증을 통해 믿을 수 있는 이웃들과 함께 의류 공유 생활을 시작해보세요.
						</div>
						<div>
							<img
								src={infoscreen}
								alt=""
								css={css`
									display: flex;
									justify-content: center;
									width: 100%;
									margin-bottom: 20px;
								`}
							/>
						</div>
					</div>
					<div>
						<div
							css={css`
								font-family: 'bold';
								font-size: 30px;
								line-height: 43.44px;
								margin-bottom: 10px;
							`}
						>
							이웃과 함께 나누는
							<br />
							의류 생활 공유 플랫폼
						</div>
						<div
							css={css`
								font-family: 'regular';
								font-size: 20px;
								line-height: 28.96px;
								margin-bottom: 15px;
							`}
						>
							내가 안 입는 옷이나 이웃과 공유하고 싶은 옷이 있다면 올려보세요. 마찬가지로, 입을 옷이
							없거나 갑자기 옷이 필요할 때 피트윈에 접속해보세요. 나와 비슷한 체형의 같은 동네
							사람들의 게시글을 피드에서 확인할 수 있어요!
						</div>
						<div>
							<img
								src={feedscreen}
								alt=""
								css={css`
									display: flex;
									justify-content: center;
									width: 100%;
									margin-bottom: 20px;
								`}
							/>
						</div>
					</div>
					<div>
						<div
							css={css`
								font-family: 'bold';
								font-size: 30px;
								line-height: 43.44px;
								margin-bottom: 10px;
							`}
						>
							채팅으로 편하게
							<br />
							거래 약속을 잡아보세요
						</div>
						<div
							css={css`
								font-family: 'regular';
								font-size: 20px;
								line-height: 28.96px;
								margin-bottom: 15px;
							`}
						>
							대여하고 싶은 옷이 있나요? 채팅으로 편하게 이야기를 나눠보세요! 실시간 대화가 가능하고
							읽음 확인 기능을 통해 상대방이 대화를 확인 했는지 확인할 수 있어요.
						</div>
						<div>
							<img
								src={çhatscreen}
								alt=""
								css={css`
									display: flex;
									justify-content: center;
									width: 100%;
								`}
							/>
						</div>
					</div>
				</div>
				<div
					css={css`
						background-color: ${colors.green100};
						color: ${colors.background};
					`}
				>
					<div
						css={css`
							display: flex;
							flex-direction: column;
							padding: 35px;
						`}
					>
						<div
							css={css`
								font-family: 'bold';
								font-size: 30px;
								line-height: 43.44px;
								margin-bottom: 10px;
							`}
						>
							피트윈은
							<br />
							친환경 의류 소비를
							<br />
							지향합니다.
						</div>
						<div
							css={css`
								font-family: 'regular';
								font-size: 20px;
								line-height: 28.96px;
							`}
						>
							최근 의류 산업으로 인한 환경 문제의 심각성 속에서 피트윈이 탄생되었습니다. 피트윈은
							일상 생활에서 의류의 재활용 선순환이 자연스럽게 이루어질 수 있도록 노력할 것입니다.
							의류 공유를 통한 리사이클로 친환경적 의류 소비 문화를 주도하고자 합니다.
						</div>
					</div>
				</div>
			</div>
		</>
	);
};

export default About;
