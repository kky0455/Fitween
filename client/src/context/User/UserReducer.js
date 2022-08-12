const userReducer = (state, action) => {
	switch (action.type) {
		case 'LOGIN':
			return {
				loginedUserId: action.loginedUserId,
			};
		case 'LOGOUT':
			return {
				loginedUserId: null,
			};
		case 'SIGNUP':
			return {
				loginedUserId: action.loginedUserId,
			};
		default:
			return state;
	}
};

export default userReducer;
