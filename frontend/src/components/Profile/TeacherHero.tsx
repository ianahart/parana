import { Box, Button, Flex, Text } from '@chakra-ui/react';
import Actions from './Actions';
import UserAvatar from '../Shared/UserAvatar';
import { useContext } from 'react';
import { UserContext } from '../../context/user';
import { IProfile, IUserContext } from '../../interfaces';
import { BsChatLeft } from 'react-icons/bs';

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
      <>
        <Flex my="1rem" align="center" justifyContent="space-around">
          <Text fontWeight="bold">Hourly rate</Text>
          <Text fontWeight="bold" fontSize="1.3rem">
            ${profile.perHour}
          </Text>
        </Flex>
        <Flex my="1rem" align="center" justifyContent="space-around">
          <Text fontWeight="bold">Years snowboarding</Text>
          <Text fontWeight="bold" fontSize="1.3rem">
            {profile.yearsSnowboarding}
          </Text>
        </Flex>
        <Flex my="1rem" align="center" justifyContent="space-around">
          <Text fontWeight="bold">Number of students</Text>
          <Text fontWeight="bold" fontSize="1.3rem">
            0
          </Text>
        </Flex>
        {user.role === 'USER' && (
          <Flex my="1.5rem" justify="center">
            <Button colorScheme="blackAlpha">
              <Box display="inline" mr="0.25rem">
                <BsChatLeft />
              </Box>
              Request a lesson
            </Button>
          </Flex>
        )}
      </>
    </>
  );
};

export default TeacherHero;
