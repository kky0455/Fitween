// toDo : 별명 중복검사, 특수문자, 이모지, 숫자 확인 검사, 글자길이 검사
export const validateNickName = nickName => {
	function checkSpecial(str) {
		const regExp = /[!?@#$%^&*():;+-=~{}<>\_\[\]\|\\\"\'\,\.\/\`\₩]/g;
		if (regExp.test(str)) return true;
		return false;
	}

	function checkNum(str) {
		const regExp = /[0-9]/g;
		if (regExp.test(str)) return true;
		return false;
	}

	function checkSpace(str) {
		if (str.search(/\s/) !== -1) return true;
		return false;
	}

	function checkLength(str) {
		if (str.length < 2 || str.length > 8) return true;
		return false;
	}

	if (checkSpecial(nickName) || checkSpace(nickName) || checkNum(nickName))
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
