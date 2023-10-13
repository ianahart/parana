import { Box, Button, Heading } from '@chakra-ui/react';
import { Client } from '../util/client';

const HomeRoute = () => {
  const sendHeartbeat = () => {
    Client.heartbeat()
      .then((res) => console.log(res))
      .catch((err) => console.log(err));
  };

  return (
    <Box>
      <Heading>Home</Heading>
      <Button onClick={sendHeartbeat}>Heartbeat</Button>
    </Box>
  );
};

export default HomeRoute;
