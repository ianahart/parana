import { Box, Flex } from '@chakra-ui/react';
import Hero from '../components/Register/Hero';
import Form from '../components/Register/Form';

const RegisterRoute = () => {
  return (
    <Box>
      <Flex
        flexDir={['column', 'column', 'row']}
        className="register-route-container"
        mx="auto"
        p="0.25rem"
        minH="100vh"
      >
        <Box mx="auto" width={['95%', '95%', '640px']} minH="100vh">
          <Form />
        </Box>
        <Box width={['95%', '95%', '640px']} mx="auto" minH="100vh" height="100%">
          <Hero />
        </Box>
      </Flex>
    </Box>
  );
};

export default RegisterRoute;
