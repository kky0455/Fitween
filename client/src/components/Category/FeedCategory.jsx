import React from 'react';
/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';

import CategoryBtn from './CategoryBtn';
import { FEED_CATEGORY } from '../../constants/category';

const FeedCategory = ({ activeCategoryCode, onClickHandler }) => {
	return (
		<div
			css={css`
				padding: 2px 30px 20px 30px;
				overflow: hidden;
			`}
		>
			<div
				css={css`
					display: flex;
					gap: 10px;
					overflow: auto;
					white-space: nowrap;
				`}
			>
				{FEED_CATEGORY.map(category => (
					<CategoryBtn
						key={category.code}
						label={category.label}
						isActive={activeCategoryCode === category.code}
						onClickHandler={onClickHandler}
						code={category.code}
					/>
				))}
			</div>
		</div>
	);
};

export default FeedCategory;
