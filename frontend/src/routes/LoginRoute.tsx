import { Box, Flex } from '@chakra-ui/react';
import VideoBackground from '../components/Login/VideoBackground';
import Form from '../components/Login/Form';
const LoginRoute = () => {
  return (
    <Box>
      <Flex
        flexDir={['column', 'column', 'row']}
        className="login-route-container"
        mx="auto"
        p="0.25rem"
        minH="100vh"
      >
        <Box mx="auto" width={['95%', '95%', '640px']} minH="100vh">
          <VideoBackground />
        </Box>
        <Box width={['95%', '95%', '640px']} mx="auto" minH="100vh" height="100%">
          <Form />
        </Box>
      </Flex>
    </Box>
  );
};

export default LoginRoute;
