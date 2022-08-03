import React from 'react';
import styled from 'styled-components';

const Modal = ({ className, onClose, maskClosable, visible, children }) => {
	const onMaskClick = e => {
		if (e.target === e.currentTarget) {
			onClose(e);
		}
	};

	return (
		<ModalOverlay visible={visible}>
			<ModalWrapper
				className={className}
				onClick={maskClosable ? onMaskClick : null}
				tabIndex="-1"
				visible={visible}
			>
				<ModalInner tabIndex={0} className="modal-inner">
					{children}
				</ModalInner>
			</ModalWrapper>
		</ModalOverlay>
	);
};
const ModalWrapper = styled.div`
	box-sizing: border-box;
	display: ${props => (props.visible ? 'block' : 'none')};
	position: fixed;
	top: 0;
	right: 0;
	bottom: 0;
	left: 0;
	z-index: 1000;
	overflow: auto;
	outline: 0;
`;

const ModalOverlay = styled.div`
	box-sizing: border-box;
	display: ${props => (props.visible ? 'block' : 'none')};
	position: fixed;
	top: 0;
	left: 0;
	bottom: 0;
	right: 0;
	background-color: rgba(0, 0, 0, 0.6);
	z-index: 999;
	animation: fadeIn 0.3s;
	@keyframes fadeIn {
		0% {
			opacity: 0;
		}
		to {
			opacity: 1;
		}
	}
`;

const ModalInner = styled.div`
	box-sizing: border-box;
	position: fixed;
	box-shadow: 0 0 6px 0 rgba(0, 0, 0, 0.5);
	background-color: #fff;
	border-radius: 10px 10px 0 0;
	width: 100%;
	max-width: 480px;
	bottom: 0;
	left: 50%;
	transform: translateX(-50%);
	margin: 0 auto;
	padding: 40px 20px;
	animation: fadeInUp 0.3s;
	@keyframes fadeInUp {
		0% {
			opacity: 0;
			transform: translateX(-50%) translateY(100%);
		}
		to {
			opacity: 1;
			transform: translateX(-50%) translateY(0);
		}
	}
`;
export default Modal;
