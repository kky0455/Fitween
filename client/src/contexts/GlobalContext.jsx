import React, { createContext, useState, useContext } from 'react';

const Context = createContext();
export function GlobalContextProvider({ children }) {
	const [hasTop, setHasTop] = useState(false);
	const [hasBottom, setHasBottom] = useState(false);

	return (
		<Context.Provider value={{ hasTop, setHasTop, hasBottom, setHasBottom }}>
			{children}
		</Context.Provider>
	);
}

export function useGlobalContext() {
	return useContext(Context);
}
