import React from 'react';
import { useParams } from 'react-router-dom';
const ProfileTownModify = () => {
	const { userId } = useParams();
	return <div>내 동네 수정 : {userId}</div>;
};

export default ProfileTownModify;
