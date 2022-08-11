export const checkSpecial = str => {
	const regExp = /[!?@#$%^&*():;+-=~{}<>\_\[\]\|\\\"\'\,\.\/\`\₩]/g;
	if (regExp.test(str)) return true;
	return false;
};

export const checkNum = str => {
	const regExp = /[0-9]/g;
	if (regExp.test(str)) return true;
	return false;
};

export const checkEmoji = str => {
	const regExp =
		/([\u2700-\u27BF]|[\uE000-\uF8FF]|\uD83C[\uDC00-\uDFFF]|\uD83D[\uDC00-\uDFFF]|[\u2011-\u26FF]|\uD83E[\uDD10-\uDDFF])/g;
	if (regExp.test(str)) return true;
	return false;
};

export const checkSpace = str => {
	if (str.search(/\s/) !== -1) return true;
	return false;
};

export const checkLength = str => {
	if (str.length < 2 || str.length > 8) return true;
	return false;
};

export const validateNickName = nickName => {
	if (checkSpecial(nickName) || checkSpace(nickName) || checkNum(nickName) || checkEmoji(nickName))
		return { state: false, type: 'notLengthErr' };
	if (checkLength(nickName)) return { state: false, type: 'lengthErr' };
	return { state: true };
};

export const validateHeight = height => {
	if (height >= 90 && height <= 250) return true;
	return false;
};

export const validateWeight = weight => {
	if (weight >= 30 && weight <= 200) return true;
	return false;
};

export const validateFootSize = footSize => {
	if (footSize >= 0 && footSize <= 350) return true;
	return false;
};

export const validateAllInput = info => {
	return validateNickName(info.nickname) &&
		validateHeight(info.height) &&
		validateWeight(info.weight) &&
		validateFootSize(info.footSize)
		? true
		: false;
};
