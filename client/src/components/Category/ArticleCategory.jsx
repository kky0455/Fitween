import React from 'react';
/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';

import CategoryBtn from './CategoryBtn';
import { ARTICLE_CATEGORY } from '../../constants/category';

const ArticleCategory = ({ activeCategory, onClickHandler }) => {
	return (
		<div
			css={css`
				display: grid;
				grid-template-rows: repeat(2, 1fr);
				grid-template-columns: repeat(5, 1fr);
				gap: 8px;
			`}
		>
			{ARTICLE_CATEGORY.map(category => (
				<CategoryBtn
					key={category.code}
					label={category.label}
					isActive={activeCategory === category.code}
					onClickHandler={onClickHandler}
					code={category.code}
				/>
			))}
		</div>
	);
};

export default ArticleCategory;
