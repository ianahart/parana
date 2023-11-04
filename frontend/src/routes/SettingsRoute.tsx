import { Box, Flex } from '@chakra-ui/react';
import { Outlet } from 'react-router-dom';
import Sidebar from '../components/Settings/Sidebar';

const SettingsRoute = () => {
  return (
    <Box minH="100vh">
      <Box minH="100vh" className="settings-container">
        <Box
          height="100vh"
          width="360px"
          minW="360px"
          border="1px solid"
          borderLeft="none"
          borderColor="border.primary"
          borderTopRightRadius={8}
          borderBottomRadius={8}
        >
          <Sidebar />
        </Box>
        <Flex flexGrow={2} bg="primary.dark">
          <Box
            boxShadow="md"
            border="1px solid gray"
            borderRadius={8}
            maxW={['95%', '640px', '640px']}
            width={['95%', '95%', '640px']}
            minH="600px"
            mx="auto"
            className="settings-outlet-container"
            p="1rem"
          >
            <Outlet />
          </Box>
        </Flex>
      </Box>
    </Box>
  );
};

export default SettingsRoute;
