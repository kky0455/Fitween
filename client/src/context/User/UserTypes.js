export const setLogin = arg => ({
	type: 'LOGIN',
	loginedUserId: arg,
});

export const setSignUp = arg => ({
	type: 'SIGNUP',
	loginedUserId: arg,
});

export const setLogout = () => ({
	type: 'LOGOUT',
});
