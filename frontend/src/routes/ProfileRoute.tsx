import { Box } from '@chakra-ui/react';
import { useEffect, useRef, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { Client } from '../util/client';
import { profileState } from '../state/initialState';
import { IProfile } from '../interfaces';
import TeacherProfile from '../components/Profile/TeacherProfile';
import UserProfile from '../components/Profile/UserProfile';

const ProfileRoute = () => {
  const navigate = useNavigate();
  const { id } = useParams();
  const shouldRun = useRef(true);
  const [profile, setProfile] = useState<IProfile>(profileState);

  const retrieveProfile = (profileId: string) => {
    Client.getProfile(parseInt(profileId))
      .then((res) => {
        const { data } = res.data;
        setProfile(data);
      })
      .catch((err) => {
        if (err.response.status === 404) {
          navigate('/not-found');
        }
      });
  };

  useEffect(() => {
    if (shouldRun.current) {
      retrieveProfile(id as string);
      shouldRun.current = false;
    }
  }, [shouldRun.current]);

  return (
    <Box minH="100vh">
      <Box p="0.5rem" minH="100vh" mx="auto" className="profile-container">
        {profile.id !== 0 && (
          <>
            {profile.role === 'TEACHER' ? (
              <TeacherProfile profile={profile} />
            ) : (
              <UserProfile profile={profile} />
            )}
          </>
        )}
      </Box>
    </Box>
  );
};

export default ProfileRoute;
