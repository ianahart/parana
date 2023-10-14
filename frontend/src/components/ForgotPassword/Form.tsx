import {
  Box,
  Button,
  Flex,
  FormControl,
  FormLabel,
  Heading,
  Input,
  Text,
} from '@chakra-ui/react';
import Logo from '../Logo/Logo';
import { BsArrowLeft } from 'react-icons/bs';
import { Link as RouterLink } from 'react-router-dom';
import BasicSpinner from '../Shared/BasicSpinner';
import { useState } from 'react';
import { Client } from '../../util/client';
import { AiOutlineCheck } from 'react-icons/ai';

const Form = () => {
  const [isLoading, setIsLoading] = useState(false);
  const [email, setEmail] = useState('');
  const [serverError, setServerError] = useState('');
  const [successMessage, setSuccessMessage] = useState('');

  const validateForm = (email: string) => {
    return email.trim().length !== 0 && email.length < 200;
  };

  const sendForgotPasswordEmail = (email: string) => {
    setSuccessMessage('');
    setIsLoading(true);
    Client.sendForgotPasswordEmail(email)
      .then((res) => {
        console.log(res);
        setIsLoading(false);
        setSuccessMessage('Email successfully sent.');
      })
      .catch((err) => {
        setServerError(err.response.data.message);
        setIsLoading(false);
        throw new Error(err.response.data.message);
      });
  };

  const handleOnSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (!validateForm(email)) {
      return;
    }
    sendForgotPasswordEmail(email);
  };
  return (
    <Box
      p="0.5rem"
      width={['95%', '450px', '450px']}
      borderRadius={8}
      boxShadow="primary.boxShadow"
    >
      <form onSubmit={handleOnSubmit}>
        <Flex flexDir="column" align="center" justify="center">
          <Flex
            p="0.5rem"
            flexDir="column"
            align="center"
            justify="center"
            borderRadius={8}
            bg="primary.light"
          >
            <Logo width="40px" height="40px" />
          </Flex>
          <Box as="header" my="1rem">
            <Heading textAlign="center" color="light.primary" fontSize="1.5rem">
              Forgot Password?
            </Heading>
            <Text fontSize="0.85rem" mt="0.5rem" mb="1rem" color="text.secondary">
              No worries, we'll send you reset instructions
            </Text>
          </Box>
          {successMessage.length > 0 && (
            <Flex color="text.secondary" align="center" my="1rem">
              <Box>
                <AiOutlineCheck />
              </Box>
              <Text fontSize="0.85rem">{successMessage}</Text>
            </Flex>
          )}
          {serverError.length > 0 && (
            <Flex my="1rem">
              <Text fontSize="0.85rem" color="error.primary">
                {serverError}
              </Text>
            </Flex>
          )}
          <FormControl color="text.secondary">
            <FormLabel htmlFor="email">Email</FormLabel>
            <Input
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              placeholder="Enter your email"
              _placeholder={{ color: 'text.secondary' }}
              name="email"
              type="email"
              id="email"
              borderColor="border.primary"
            />
          </FormControl>
          {!isLoading && (
            <Flex my="1.5rem" width="100%">
              <Button type="submit" width="100%" colorScheme="blackAlpha">
                Reset password
              </Button>
            </Flex>
          )}
          {isLoading && (
            <Flex>
              <BasicSpinner
                color="text.secondary"
                message="Sending email... Please wait."
              />
            </Flex>
          )}
          <Flex color="text.secondary" my="1.5rem" justify="center" align="center">
            <Box color="text.secondary" mr="0.25rem">
              <BsArrowLeft />
            </Box>
            <RouterLink to="/login">Back to log in</RouterLink>
          </Flex>
        </Flex>
      </form>
    </Box>
  );
};

export default Form;
