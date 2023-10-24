import { Box, Button, Flex, Text } from '@chakra-ui/react';
import { IConnectionRequest } from '../../interfaces';
import ConnectionAvatar from './ConnectionAvatar';

interface IConnectionRequestProps {
  connectionRequest: IConnectionRequest;
  role: string;
  currentUserId: number;
  removeConnectionRequest: (connectionId: number) => void;
  confirmConnectionRequest: (connectionId: number, currentUserId: number) => void;
}

const ConnectionRequest = ({
  connectionRequest,
  role,
  currentUserId,
  removeConnectionRequest,
  confirmConnectionRequest,
}: IConnectionRequestProps) => {
  const action = role === 'TEACHER' ? 'received' : 'sent';

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
      <ConnectionAvatar
        width="200px"
        height="160px"
        avatarUrl={connectionRequest.avatarUrl}
        fullName={connectionRequest.fullName}
      />
      <Flex flexDir="column" p="0.25rem">
        <Flex mt="0.5rem" flexDir="column" align="center">
          <Text fontWeight="bold">{connectionRequest.fullName}</Text>
          <Text
            fontStyle="italic"
            fontSize="0.7rem"
            color="text.primary"
            my="0.25rem"
            fontWeight="bold"
          >
            {action} {connectionRequest.readableDate}
          </Text>
        </Flex>
        <Flex p="0.25rem" flexDir="column">
          {role === 'TEACHER' && (
            <Button
              onClick={() =>
                confirmConnectionRequest(connectionRequest.id, currentUserId)
              }
              my="0.5rem"
              size="sm"
              width="100%"
              colorScheme="telegram"
            >
              Confirm
            </Button>
          )}
          <Button
            onClick={() => removeConnectionRequest(connectionRequest.id)}
            size="sm"
            width="100%"
            colorScheme="gray"
          >
            Delete
          </Button>
        </Flex>
      </Flex>
    </Box>
  );
};

export default ConnectionRequest;
