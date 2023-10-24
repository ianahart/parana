import { Box, Button, ButtonGroup, Flex, Text } from '@chakra-ui/react';
import { IConnection } from '../../interfaces';
import ConnectionAvatar from './ConnectionAvatar';
import { BiUserMinus } from 'react-icons/bi';
import { ImProfile } from 'react-icons/im';
import { useNavigate } from 'react-router-dom';

interface IConnectionProps {
  connection: IConnection;
  removeConnection: (connectionId: number) => void;
}

const Connection = ({ connection, removeConnection }: IConnectionProps) => {
  const navigate = useNavigate();

  const navigateToProfile = () => {
    navigate(`/profiles/${connection.profileId}`);
  };

  return (
    <Box
      my="1rem"
      width="200px"
      color="text.secondary"
      borderRadius={8}
      mx="auto"
      bg="blackAlpha.500"
      minH="275px"
    >
      <Flex flexDir="column" justify="space-between" minH="275px">
        <Box>
          <ConnectionAvatar
            width="200px"
            height="160px"
            avatarUrl={connection.avatarUrl}
            fullName={connection.fullName}
          />
          <Flex mt="0.5rem" flexDir="column" align="center">
            <Text fontWeight="bold">{connection.fullName}</Text>
          </Flex>
        </Box>
        <Box>
          <ButtonGroup display="flex" justifyContent="space-between" p="0.5rem">
            <Button
              size="sm"
              onClick={() => removeConnection(connection.id)}
              colorScheme="gray"
            >
              <Box>
                <BiUserMinus />
              </Box>
              Remove
            </Button>
            <Button onClick={navigateToProfile} size="sm" colorScheme="telegram">
              <Box>
                <ImProfile />
              </Box>
              Profile
            </Button>
          </ButtonGroup>
        </Box>
      </Flex>
    </Box>
  );
};

export default Connection;
