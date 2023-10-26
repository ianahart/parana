import { Box, Flex, Text } from '@chakra-ui/react';
import Actions from './Actions';
import UserAvatar from '../Shared/UserAvatar';
import { useContext, useEffect, useRef, useState } from 'react';
import { UserContext } from '../../context/user';
import { IProfile, IUserContext } from '../../interfaces';
import RequestLesson from './RequestLesson';
import { Client } from '../../util/client';

interface ITeacherHeroProps {
  profile: IProfile;
}

const TeacherHero = ({ profile }: ITeacherHeroProps) => {
  const { user } = useContext(UserContext) as IUserContext;
  const [numOfStudents, setNumberOfStudents] = useState(0);
  const pagination = {
    page: 0,
    pageSize: 6,
    totalPages: 0,
    direction: 'next',
    totalElements: 0,
  };

  const shouldRun = useRef(true);

  const getNumberOfStudents = (paginate: boolean, searchTerm: string) => {
    const pageNum = paginate ? pagination.page : -1;

    Client.getConnections(
      profile.userId,
      pageNum,
      pagination.pageSize,
      pagination.direction,
      searchTerm
    )
      .then((res) => {
        const { totalElements } = res.data.data;
        setNumberOfStudents(totalElements);
      })
      .catch((err) => {
        throw new Error(err.response.data.message);
      });
  };

  useEffect(() => {
    if (shouldRun.current && user.id !== 0) {
      shouldRun.current = false;
      getNumberOfStudents(false, '');
    }
  }, [user.id, shouldRun.current]);

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
            {numOfStudents}
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
