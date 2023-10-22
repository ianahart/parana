import { Box, Flex, Text } from '@chakra-ui/react';
import Actions from './Actions';
import UserAvatar from '../Shared/UserAvatar';
import { useContext } from 'react';
import { UserContext } from '../../context/user';
import { IProfile, IUserContext } from '../../interfaces';
import RequestLesson from './RequestLesson';

interface ITeacherHeroProps {
  profile: IProfile;
}

const TeacherHero = ({ profile }: ITeacherHeroProps) => {
  const { user } = useContext(UserContext) as IUserContext;
  return (
    <>
      {user.role === 'USER' && user.id !== profile.userId && (
        <Flex justify="flex-end">
          <Actions />
        </Flex>
      )}
      <Flex mx="auto" align="center" flexDir="column">
        <UserAvatar
          width="160px"
          height="160px"
          fullName={profile.fullName}
          avatarUrl={profile.avatarUrl}
        />
      </Flex>
      <Flex justify="center" my="0.5rem">
        <Text fontWeight="bold" fontSize="1.3rem">
          {profile.firstName}
        </Text>
      </Flex>
      <Box width="80%" mx="auto">
        <Flex my="1rem" align="center" justifyContent="space-between">
          <Text fontWeight="bold">Hourly rate</Text>
          <Text fontWeight="bold" fontSize="1.3rem">
            ${profile.perHour}
          </Text>
        </Flex>
        <Flex my="1rem" align="center" justifyContent="space-between">
          <Text fontWeight="bold">Years snowboarding</Text>
          <Text fontWeight="bold" fontSize="1.3rem">
            {profile.yearsSnowboarding}
          </Text>
        </Flex>
        <Flex my="1rem" align="center" justifyContent="space-between">
          <Text fontWeight="bold">Number of students</Text>
          <Text fontWeight="bold" fontSize="1.3rem">
            0
          </Text>
        </Flex>
        {user.role === 'USER' && (
          <RequestLesson receiverId={profile.userId} senderId={user.id} />
        )}
      </Box>
    </>
  );
};

export default TeacherHero;
