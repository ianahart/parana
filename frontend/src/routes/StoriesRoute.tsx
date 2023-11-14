import { Box, Flex } from '@chakra-ui/react';
import Sidebar from '../components/Stories/Sidebar';
import { Outlet } from 'react-router-dom';

const StoriesRoute = () => {
  return (
    <Box minH="100vh">
      <Box minH="100vh" className="settings-container">
        <Sidebar />
        <Flex minH="100vh" width="100%" bg="primary.dark" filter="brightness(70%)">
          <Outlet />
        </Flex>
      </Box>
    </Box>
  );
};

export default StoriesRoute;
