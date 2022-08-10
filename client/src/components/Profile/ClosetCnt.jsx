import React from 'react';
/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';

const ClosetCnt = ({ clothesCnt }) => {
	return (
		<div>
			<span className="fw-400 fs-18" style={{ lineHeight: '26.24px' }}>
				옷장에
				<span className="fw-500 fs-20" style={{ paddingLeft: '5px', lineHeight: '32.8px' }}>
					{clothesCnt}
				</span>
				벌의 옷이 있습니다
			</span>
		</div>
	);
};

export default ClosetCnt;
