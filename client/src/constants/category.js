export const FEED_CATEGORY = [
	{ label: '전체', code: 'all' },
	{ label: '아우터', code: 'outer' },
	{ label: '상의', code: 'top' },
	{ label: '바지', code: 'pants' },
	{ label: '치마', code: 'skirt' },
	{ label: '원피스', code: 'onepiece' },
	{ label: '투피스', code: 'twopiece' },
	{ label: '가방', code: 'bag' },
	{ label: '신발', code: 'shoes' },
	{ label: '잡화', code: 'acc' },
	{ label: '기타', code: 'etc' },
];

export const ARTICLE_CATEGORY = FEED_CATEGORY.filter(category => category.code !== 'all');
