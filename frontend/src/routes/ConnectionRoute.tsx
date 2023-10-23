import { Box, Flex } from '@chakra-ui/react';
import { Outlet } from 'react-router-dom';
import Sidebar from '../components/Connection/Sidebar';

const ConnectionRoute = () => {
  return (
    <Box minH="100vh">
      <Box minH="100vh" className="connection-container">
        <Box
          height="100vh"
          width="360px"
          minW="360px"
          border="1px solid"
          borderLeft="none"
          borderColor="border.primary"
        >
          <Sidebar />
        </Box>
        <Flex flexGrow={2}>
          <Box width="90%" mx="auto" className="connection-outlet-container" p="1rem">
            <Outlet />
          </Box>
        </Flex>
      </Box>
    </Box>
  );
};

export default ConnectionRoute;
