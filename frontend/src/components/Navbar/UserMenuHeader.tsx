import { useContext } from 'react';
import { UserContext } from '../../context/user';
import { IUserContext } from '../../interfaces';
import { Box, Text, Flex } from '@chakra-ui/react';
import UserAvatar from '../Shared/UserAvatar';

const UserMenuHeader = () => {
  const { user } = useContext(UserContext) as IUserContext;

  return (
    <Flex align="center" p="0.25rem" color="light.primary">
      <Box position="relative">
        <UserAvatar
          width="45px"
          height="45px"
          avatarUrl={user.avatarUrl}
          fullName={user.fullName}
        />
        <Box
          borderRadius="50%"
          height="10px"
          width="10px"
          border="1px solid gray"
          bg="limegreen"
          pos="absolute"
          top="0"
          left="0"
        ></Box>
      </Box>
      <Box ml="0.25rem">
        <Text fontWeight="bold" fontSize="0.8rem">
          {user.fullName}
        </Text>
      </Box>
    </Flex>
  );
};

export default UserMenuHeader;
