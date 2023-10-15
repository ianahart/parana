import { Flex } from '@chakra-ui/react';
import Form from '../components/ResetPassword/Form';

const ResetPasswordRoute = () => {
  return (
    <Flex
      minH="70vh"
      color="text.secondary"
      flexDir="column"
      align="center"
      justify="center"
    >
      <Form />
    </Flex>
  );
};

export default ResetPasswordRoute;
