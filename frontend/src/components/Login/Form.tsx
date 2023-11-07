import { Box, Button, Heading, Text, Flex, Checkbox } from '@chakra-ui/react';
import { Link as RouterLink } from 'react-router-dom';
import Logo from '../Logo/Logo';
import { useContext, useState } from 'react';
import { loginFormState } from '../../state/initialState';
import { ILoginForm, IUserContext } from '../../interfaces';
import FormField from '../Shared/FormField';
import { AiOutlineLock, AiOutlineMail } from 'react-icons/ai';
import { Client } from '../../util/client';
import { useNavigate } from 'react-router-dom';
import BasicSpinner from '../Shared/BasicSpinner';
import { UserContext } from '../../context/user';

const Form = () => {
  const { updateUser, stowTokens } = useContext(UserContext) as IUserContext;
  const navigate = useNavigate();
  const [form, setForm] = useState<ILoginForm>(loginFormState);
  const [serverError, setServerError] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  const [rememberMe, setRememberMe] = useState(false);

  const skipConfirmPassword = (name: string, attribute: string) => {
    return name === 'confirmPassword' && attribute === 'type';
  };

  const updateField = (name: string, value: string, attribute: string) => {
    if (skipConfirmPassword(name, attribute)) {
      return;
    }
    setForm((prevState) => ({
      ...prevState,
      [name]: { ...prevState[name as keyof ILoginForm], [attribute]: value },
    }));
  };

  const validateForm = (form: ILoginForm) => {
    let noErrors = true;
    for (const key of Object.keys(form)) {
      const { error, value } = form[key as keyof ILoginForm];
      if (value.trim().length === 0 || value.length > 200 || error.length > 0) {
        noErrors = false;
      }
    }
    return noErrors;
  };

  const resetErrors = (form: ILoginForm) => {
    setServerError('');
    for (const key of Object.keys(form)) {
      updateField(key, '', 'error');
    }
  };

  const login = (form: ILoginForm) => {
    setIsLoading(true);
    Client.login(form, rememberMe)
      .then((res) => {
        const { refreshToken, token, user } = res.data;
        updateUser(user);
        stowTokens({ refreshToken, token });
        navigate('/');
        setIsLoading(false);
      })
      .catch((err) => {
        setIsLoading(false);
        setServerError(err.response.data.message);
      });
  };

  const handleOnSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    resetErrors(form);
    if (!validateForm(form)) {
      return;
    }
    login(form);
  };

  return (
    <Box p="0.25rem" color="text.secondary">
      <Flex mb="5rem" align="center" justify="flex-end">
        <Text mr="0.25rem">Not a member?</Text>
        <Box color="purple.300">
          <RouterLink to="/register">Register now</RouterLink>
        </Box>
      </Flex>
      <Box as="header" display="flex" flexDir="column" alignItems="center" my="2rem">
        <Flex
          flexDir="column"
          align="center"
          justify="center"
          height="60px"
          width="60px"
          borderRadius="50%"
          bg="purple.300"
        >
          <Logo height="45px" width="45px" />
        </Flex>
        <Heading color="light.primary">Hello again!</Heading>
        <Text mt="0.5rem">Welcome back, you've been missed!</Text>
      </Box>
      <Box p="0.25rem" width={['95%', '95%', '90%']} mx="auto" as="main">
        <form onSubmit={handleOnSubmit}>
          {serverError.length > 0 && (
            <Flex justify="center">
              <Text color="error.primary" fontSize="0.85rem">
                {serverError}
              </Text>
            </Flex>
          )}
          <FormField
            updateField={updateField}
            name={form.email.name}
            value={form.email.value}
            error={form.email.error}
            type={form.email.type}
            label="Email"
            id="email"
            errorField="Email"
            icon={<AiOutlineMail />}
            placeHolder="Enter your email"
          />
          <FormField
            updateField={updateField}
            name={form.password.name}
            value={form.password.value}
            error={form.password.error}
            type={form.password.type}
            label="Password"
            id="password"
            errorField="Password"
            icon={<AiOutlineLock />}
            placeHolder="Enter your password"
          />
          <Flex align="center" justify="space-between">
            <Checkbox
              onChange={(e) => setRememberMe(e.target.checked)}
              isChecked={rememberMe}
              colorScheme="purple"
            >
              <Text fontSize="0.85rem">Remember me</Text>
            </Checkbox>
            <Flex my="2rem" justify="flex-end" fontSize="0.85rem" color="purple.300">
              <RouterLink to="/forgot-password">Recovery Password</RouterLink>
            </Flex>
          </Flex>
          {isLoading && (
            <Box>
              <BasicSpinner color="light.primary" message="Authenticating user..." />
            </Box>
          )}
          {!isLoading && (
            <Flex my="2rem">
              <Button colorScheme="blackAlpha" type="submit" width="100%">
                Sign In
              </Button>
            </Flex>
          )}
        </form>
      </Box>
    </Box>
  );
};

export default Form;
