import { Box, Flex } from '@chakra-ui/react';
import { Outlet } from 'react-router-dom';
import Sidebar from '../components/Settings/Sidebar';
import { useContext, useEffect, useRef } from 'react';
import { UserContext } from '../context/user';
import { IUserContext } from '../interfaces';
import { Client } from '../util/client';

const SettingsRoute = () => {
  const shouldRun = useRef(true);
  const { user } = useContext(UserContext) as IUserContext;

  const updateSettings = () => {
    Client.updateSettings(user.settingId, false, false, false, false)
      .then((res) => {
        console.log(res);
      })
      .catch((err) => {
        throw new Error(err);
      });
  };

  useEffect(() => {
    if (shouldRun.current && user.id !== 0) {
      shouldRun.current = false;
      updateSettings();
    }
  }, [shouldRun.current, user.id]);

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
