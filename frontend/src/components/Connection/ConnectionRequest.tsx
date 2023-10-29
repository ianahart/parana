import { Button, Flex, Text } from '@chakra-ui/react';
import { IConnectionRequest } from '../../interfaces';
import ConnectionContainer from './ConnectionContainer';

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
    <ConnectionContainer data={connectionRequest}>
      <Text
        textAlign="center"
        fontStyle="italic"
        fontSize="0.7rem"
        color="text.primary"
        my="0.25rem"
        fontWeight="bold"
      >
        {action} {connectionRequest.readableDate}
      </Text>
      <Flex p="0.25rem" flexDir="column">
        {role === 'TEACHER' && (
          <Button
            onClick={() => confirmConnectionRequest(connectionRequest.id, currentUserId)}
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
    </ConnectionContainer>
  );
};

export default ConnectionRequest;
