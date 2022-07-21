import React from 'react';
import { useParams } from 'react-router';

const ArticleDetail = () => {
	const { articleId } = useParams();
	return <div>게시글 상세 {articleId}</div>;
};

export default ArticleDetail;
