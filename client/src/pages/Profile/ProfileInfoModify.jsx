import React from 'react';
import { useParams } from 'react-router-dom';
const ProfileInfoModify = () => {
	const { userId } = useParams();
	return <div>내 정보 수정 : {userId}</div>;
};

export default ProfileInfoModify;
