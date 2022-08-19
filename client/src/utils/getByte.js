const getByte = text =>
	text
		.split('')
		.map(s => s.charCodeAt(0))
		.reduce((prev, c) => prev + (c === 10 ? 2 : c >> 7 ? 2 : 1), 0);

export default getByte;
