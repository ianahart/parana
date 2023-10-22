import { Box, Button, Flex, Text } from '@chakra-ui/react';
import { useEffect, useRef, useState } from 'react';
import { BsChatLeft, BsLink } from 'react-icons/bs';
import { Client } from '../../util/client';
import { MdOutlinePending } from 'react-icons/md';

interface IRequestLessonProps {
  receiverId: number;
  senderId: number;
}

const RequestLesson = ({ receiverId, senderId }: IRequestLessonProps) => {
  const shouldRun = useRef(true);
  const [connectionStatus, setConnectionStatus] = useState({
    status: 'Request a lesson',
    accepted: false,
    pending: false,
  });
  const [error, setError] = useState('');

  const getConnectionStatus = () => {
    Client.getConnectionStatus(senderId, receiverId)
      .then((res) => {
        const { data } = res.data;
        setConnectionStatus({ ...data });
      })
      .catch((err) => {
        setError(err.response.data?.message ?? '');
        throw new Error(err.response.data?.message ?? '');
      });
  };

  useEffect(() => {
    if (shouldRun.current) {
      getConnectionStatus();
      shouldRun.current = false;
    }
  }, [shouldRun.current]);

  const createConnectionRequest = () => {
    setError('');
    if (
      connectionStatus.pending ||
      (!connectionStatus.pending && connectionStatus.accepted)
    )
      return;

    Client.createConnectionRequest(senderId, receiverId)
      .then((res) => {
        const { data } = res.data;
        setConnectionStatus({ ...data });
      })
      .catch((err) => {
        setError(err.response.data.message);
        throw new Error(err.response.data.message);
      });
  };

  const displayIcon = () => {
    if (connectionStatus.pending && !connectionStatus.accepted) {
      return <MdOutlinePending />;
    } else if (!connectionStatus.pending && connectionStatus.accepted) {
      return <BsLink />;
    } else {
      return <BsChatLeft />;
    }
  };

  return (
    <>
      <Flex my="1.5rem" justify="center">
        <Button onClick={createConnectionRequest} colorScheme="blackAlpha">
          <Box display="inline" mr="0.25rem">
            {displayIcon()}
          </Box>
          {connectionStatus.status}
        </Button>
      </Flex>
      {error.length > 0 && (
        <Text textAlign="center" fontSize="0.85rem">
          {error}
        </Text>
      )}
    </>
  );
};

export default RequestLesson;
