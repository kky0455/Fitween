import * as userHandlers from './handlers/user';
import * as articleHandlers from './handlers/article';
import * as chatHandlers from './handlers/chat';

export const handlers = [
	...Object.values(userHandlers),
	...Object.values(articleHandlers),
	...Object.values(chatHandlers),
];
