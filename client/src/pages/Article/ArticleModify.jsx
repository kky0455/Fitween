import React from 'react';
import { useParams } from 'react-router-dom';
const ArticleModify = () => {
	const { articleId } = useParams();
	return <div>게시글 수정 : {articleId}</div>;
};

export default ArticleModify;
