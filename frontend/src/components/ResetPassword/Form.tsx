import { Box, Button, Flex, Heading, Text } from '@chakra-ui/react';
import { useState } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { passwordResetState } from '../../state/initialState';
import { IPasswordResetForm } from '../../interfaces';
import FormField from '../Shared/FormField';
import { AiOutlineLock } from 'react-icons/ai';
import Logo from '../Logo/Logo';
import OtpInput from './OtpInput';
import BasicSpinner from '../Shared/BasicSpinner';
import { Client } from '../../util/client';

const Form = () => {
  const OTP_INPUTS = 5;
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const [passCode, setPassCode] = useState<string[]>([]);
  const [form, setForm] = useState<IPasswordResetForm>(passwordResetState);
  const [serverError, setServerError] = useState('');
  const [isLoading, setIsLoading] = useState(false);

  const updateField = (name: string, value: string, attribute: string) => {
    setForm((prevState) => ({
      ...prevState,
      [name]: { ...prevState[name as keyof IPasswordResetForm], [attribute]: value },
    }));
  };

  const validateForm = (form: IPasswordResetForm) => {
    let hasNoErrors = true;
    for (const key of Object.keys(form)) {
      const { value } = form[key as keyof IPasswordResetForm];
      if (
        value.trim().length === 0 ||
        value.length > 200 ||
        OTP_INPUTS !== passCode.length
      ) {
        hasNoErrors = false;
      }
    }
    return hasNoErrors;
  };

  const resetErrors = (form: IPasswordResetForm) => {
    for (const key of Object.keys(form)) {
      updateField(key, '', 'error');
    }
    setServerError('');
  };

  const resetPassword = (form: IPasswordResetForm) => {
    setIsLoading(true);
    Client.resetPassword(
      form.password.value,
      form.confirmPassword.value,
      passCode.join(''),
      searchParams.get('token') as string
    )
      .then(() => {
        navigate('/login');
        setIsLoading(false);
      })
      .catch((err) => {
        console.log(err);
        setIsLoading(false);
        setServerError(err.response.data.message);
      });
  };

  const handleOnSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    resetErrors(form);
    if (!validateForm(form)) return;
    resetPassword(form);
  };

  return (
    <Box p="0.5rem" width={['95%', '450px', '450px']}>
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
              Set new password
            </Heading>
            <Text fontSize="0.85rem" mt="0.5rem" mb="1rem" color="text.secondary">
              Password must include 1 uppercase, 1 lowercase, 1 digit and 1 special char.
            </Text>
          </Box>
          {serverError.length > 0 && (
            <Flex justify="center" color="error.primary">
              <Text fontSize="0.85rem">{serverError}</Text>
            </Flex>
          )}
          <OtpInput
            passCode={passCode}
            setPassCode={setPassCode}
            numOfInputs={OTP_INPUTS}
          />
          <FormField
            updateField={updateField}
            name={form.password.name}
            value={form.password.value}
            error={form.password.error}
            type={form.password.type}
            label="New Password"
            id="password"
            errorField="New password"
            icon={<AiOutlineLock />}
            placeHolder="Enter your new password"
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
          {isLoading && (
            <Flex my="1.5rem" justify="center">
              <BasicSpinner color="light.primary" message="Saving new password..." />
            </Flex>
          )}
          {!isLoading && (
            <Flex width="100%" my="2rem" justify="center">
              <Button type="submit" colorScheme="blackAlpha" width="100%">
                Reset Password
              </Button>
            </Flex>
          )}
        </Flex>
      </form>
    </Box>
  );
};
export default Form;
