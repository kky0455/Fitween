import { createContext, useContext, useReducer } from 'react';

import userReducer from './UserReducer';
import userState from './UserState';

const UserStateContext = createContext(null);
const UserDispatchContext = createContext(null);

export const UserProvider = ({ children }) => {
	const [state, dispatch] = useReducer(userReducer, userState);

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
