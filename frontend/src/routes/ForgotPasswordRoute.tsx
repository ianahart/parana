import { Flex } from '@chakra-ui/react';
import Form from '../components/ForgotPassword/Form';

const ForgotPasswordRoute = () => {
  return (
    <Flex minH="70vh" flexDir="column" align="center" justify="center">
      <Form />
    </Flex>
  );
};

export default ForgotPasswordRoute;
