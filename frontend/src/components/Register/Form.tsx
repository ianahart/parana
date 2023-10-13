import {
  Flex,
  Box,
  Heading,
  Text,
  HStack,
  useRadioGroup,
  Button,
} from '@chakra-ui/react';
import { IRegisterForm } from '../../interfaces';
import { registerFormState } from '../../state/initialState';
import { useState } from 'react';
import FormField from '../Shared/FormField';
import { AiOutlineLock, AiOutlineMail, AiOutlineUser } from 'react-icons/ai';
import RadioCard from '../Shared/RadioCard';
import { Client } from '../../util/client';
import { isAxiosError } from 'axios';
import { nanoid } from 'nanoid';
import { BiErrorCircle } from 'react-icons/bi';
import BasicSpinner from '../Shared/BasicSpinner';
import { useNavigate } from 'react-router-dom';

interface ServerError<T> {
  [key: string]: any;
  data: T;
}

const Form = () => {
  const navigate = useNavigate();
  const radioOptions = ['teacher', 'user'];
  const [role, setRole] = useState('user');
  const [form, setForm] = useState(registerFormState);
  const [serverErrors, setServerErrors] = useState<string[]>([]);
  const [isLoading, setIsLoading] = useState(false);

  const { getRootProps, getRadioProps } = useRadioGroup({
    name: 'user',
    defaultValue: 'user',
    onChange(nextValue) {
      setRole(nextValue);
    },
  });

  const group = getRootProps();

  const updateField = (name: string, value: string, attribute: string) => {
    setForm((prevState) => ({
      ...prevState,
      [name]: { ...prevState[name as keyof IRegisterForm], [attribute]: value },
    }));
  };

  const validate = (form: IRegisterForm) => {
    let noErrors = true;
    for (const key of Object.keys(form)) {
      const { value, error } = form[key as keyof IRegisterForm];
      if (value.trim().length === 0 || value.length > 200 || error.length > 0) {
        noErrors = false;
      }
    }
    return noErrors;
  };

  const resetErrors = () => {
    for (const key of Object.keys(form)) {
      updateField(key, '', 'error');
    }
    setServerErrors([]);
  };

  const applyServerErrors = <T extends ServerError<T>>(data: T) => {
    for (const key of Object.keys(data)) {
      setServerErrors((prevState) => [...prevState, data[key]]);
    }
  };

  const register = async (form: IRegisterForm) => {
    try {
      setIsLoading(true);
      await Client.register(form, role);
      setIsLoading(false);
      navigate('/login');
    } catch (err: unknown) {
      setIsLoading(false);
      if (isAxiosError(err) && err.response) {
        applyServerErrors(err.response.data);
      }
    }
  };

  const handleOnSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    resetErrors();
    if (!validate(form)) return;
    register(form);
  };

  return (
    <Box color="text.secondary">
      <Box as="header" display="flex" flexDir="column" alignItems="center" my="2rem">
        <Heading color="light.primary">Create Account</Heading>
        <Text mt="0.5rem">Let's get started with some basic information.</Text>
      </Box>
      <Box p="0.25rem" width={['95%', '95%', '90%']} mx="auto" as="main">
        <form onSubmit={handleOnSubmit}>
          <Flex align="center" flexDir="column">
            {serverErrors.map((error) => {
              return (
                <Flex key={nanoid()} align="center" my="0.25rem">
                  <Box mr="0.25rem" color="error.primary">
                    <BiErrorCircle />
                  </Box>
                  <Text color="error.primary" fontSize="0.8rem">
                    {error}
                  </Text>
                </Flex>
              );
            })}
          </Flex>
          <FormField
            updateField={updateField}
            name={form.firstName.name}
            value={form.firstName.value}
            error={form.firstName.error}
            type={form.firstName.type}
            label="First Name"
            id="firstName"
            errorField="First name"
            icon={<AiOutlineUser />}
            placeHolder="Enter your first name"
          />
          <FormField
            updateField={updateField}
            name={form.lastName.name}
            value={form.lastName.value}
            error={form.lastName.error}
            type={form.lastName.type}
            label="Last Name"
            id="lastName"
            errorField="Last name"
            icon={<AiOutlineUser />}
            placeHolder="Enter your last name"
          />
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
          <FormField
            updateField={updateField}
            name={form.confirmPassword.name}
            value={form.confirmPassword.value}
            error={form.confirmPassword.error}
            type={form.confirmPassword.type}
            label="Confirm Password"
            id="confirmPassword"
            errorField="Confirm password"
            icon={<AiOutlineLock />}
            placeHolder="Retype your password"
          />
          <Flex justify="center" flexDir="column" align="center">
            <Text mb="0.5rem">Who are you?</Text>
            <HStack {...group}>
              {radioOptions.map((value) => {
                const radio = getRadioProps({ value });
                return (
                  <RadioCard key={value} {...radio}>
                    {value}
                  </RadioCard>
                );
              })}
            </HStack>
          </Flex>
          {isLoading && (
            <Flex my="2rem" justify="center">
              <BasicSpinner
                message="Creating account. Please wait."
                color="text.secondary"
              />
            </Flex>
          )}
          {!isLoading && (
            <Flex mt="3rem" mb="2rem" justify="center">
              <Button width="100%" type="submit" colorScheme="blackAlpha">
                Sign Up
              </Button>
            </Flex>
          )}
        </form>
      </Box>
    </Box>
  );
};

export default Form;
