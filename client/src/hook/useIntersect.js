import { useCallback, useEffect, useState } from 'react';

export const useIntersect = (onIntersect, option) => {
	const [ref, setRef] = useState(null);
	const checkIntersect = useCallback(([entry], observer) => {
		if (entry.isIntersecting) {
			onIntersect(entry, observer);
		}
	}, []);
	useEffect(() => {
		let observer;
		if (ref) {
			observer = new IntersectionObserver(checkIntersect, {
				...option,
			});
			observer.observe(ref);
		}
		return () => observer && observer.disconnect();
	}, [ref, option.root, option.threshold, option.rootMargin, checkIntersect]);
	return [ref, setRef];
};
