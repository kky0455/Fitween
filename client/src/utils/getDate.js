export function getDay(date) {
	//날짜문자열 형식은 자유로운 편

	let week = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];

	let dayOfWeek = week[new Date(date).getDay()];

	return dayOfWeek;
}
