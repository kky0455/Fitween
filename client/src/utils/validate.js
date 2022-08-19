import * as authApi from '../api/auth';

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

export const checkLength = (str, start = 1, end = 10000) => {
	if (typeof str === 'string') {
		if (str.length >= start && str.length <= end) return true;
		return false;
	}

	if (typeof str === 'number') {
		if (str !== null) return true;
		return false;
	}
};

export const validateDateOfBirth = dateOfBirth => {
	if (checkLength(dateOfBirth)) return true;
	return false;
};

export const checkDuplicationNickName = async nickname => {
	const { isSuccess } = await authApi.duplicationCheck(nickname);
	return isSuccess;
};

export const validateNickName = async nickName => {
	if (checkSpecial(nickName) || checkSpace(nickName) || checkNum(nickName) || checkEmoji(nickName))
		return { state: false, errMessage: '별명은 특수문자, 이모지, 숫자를 입력받을 수 없습니다.' };

	if (!checkLength(nickName, 2, 8))
		return { state: false, errMessage: '별명은 2자 이상 8자 이하로 입력해주세요.' };

	const isDuplicationNickName = await checkDuplicationNickName(nickName);
	if (isDuplicationNickName) return { state: false, errMessage: '이미 존재하는 별명입니다.' };

	return { state: true };
};

export const validateHeight = height => {
	if (checkLength(height) && height >= 90 && height <= 250) return true;
	return false;
};

export const validateWeight = weight => {
	if (checkLength(weight) && weight >= 30 && weight <= 200) return true;
	return false;
};

export const validateFootSize = footSize => {
	if (checkLength(footSize) && footSize >= 0 && footSize <= 350) return true;
	return false;
};

export const validateAllInput = async info => {
	const { state } = await validateNickName(info.nickname);

	return state &&
		validateDateOfBirth(info.dateOfBirth) &&
		validateHeight(info.height) &&
		validateWeight(info.weight) &&
		validateFootSize(info.footSize)
		? true
		: false;
};
