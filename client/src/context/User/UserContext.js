import { createContext, useContext, useReducer } from 'react';

const initialState = {
	user_id: null,
	accessToken: null,
};

const reducer = (state, action) => {
	switch (action.type) {
		case 'LOGIN':
			return {
				user_id: action.userId,
				accessToken: action.accessToken,
			};
		case 'LOGOUT':
			return {
				user_id: null,
				accessToken: null,
			};
		default:
			return state;
	}
};

const UserStateContext = createContext(null);
const UserDispatchContext = createContext(null);

export const UserProvider = ({ children }) => {
	const [state, dispatch] = useReducer(reducer, initialState);

	return (
		<UserStateContext.Provider value={state}>
			<UserDispatchContext.Provider value={dispatch}>{children}</UserDispatchContext.Provider>
		</UserStateContext.Provider>
	);
};

export const useUserState = () => {
	const state = useContext(UserStateContext);
	if (!state) throw new Error('Cannot find UserProvider');
	return state;
};

export const useUserDispatch = () => {
	const dispatch = useContext(UserDispatchContext);
	if (!dispatch) throw new Error('Cannot find UserProvider');
	return dispatch;
};