import { Box, Text, Flex, useDisclosure, Fade, Button } from '@chakra-ui/react';
import { AiOutlineCheck, AiOutlineClose } from 'react-icons/ai';
import { emailChangeState } from '../../state/initialState';
import { IEmailChangeForm, IUserContext } from '../../interfaces';
import { useNavigate } from 'react-router-dom';
import { useContext, useState } from 'react';
import FormField from '../Shared/FormField';
import { AiOutlineMail, AiOutlineLock } from 'react-icons/ai';
import { Client } from '../../util/client';
import { UserContext } from '../../context/user';

interface IChangeEmailFormProps {
  emailLastUpdated: string;
}

const ChangeEmailForm = ({ emailLastUpdated }: IChangeEmailFormProps) => {
  const navigate = useNavigate();
  const { tokens, logout, user } = useContext(UserContext) as IUserContext;
  const { isOpen, onToggle } = useDisclosure();
  const [form, setForm] = useState(emailChangeState);
  const [error, setError] = useState('');

  const skipConfirmPassword = (name: string, attribute: string) => {
    return name === 'confirmPassword' && attribute === 'type';
  };

  const updateField = (name: string, value: string, attribute: string) => {
    if (skipConfirmPassword(name, attribute)) {
      return;
    }

    setForm((prevState) => ({
      ...prevState,
      [name]: { ...prevState[name as keyof IEmailChangeForm], [attribute]: value },
    }));
  };

  const resetFormAttributes = (form: IEmailChangeForm, attribute: string) => {
    for (const key of Object.keys(form)) {
      updateField(key, '', attribute);
    }
  };

  const handleOnClose = () => {
    onToggle();
    resetFormAttributes(form, 'value');
  };

  const validateForm = (form: IEmailChangeForm) => {
    let noErrors = true;
    for (const key of Object.keys(form)) {
      const { value } = form[key as keyof IEmailChangeForm];
      if (value.trim().length === 0 || value.length > 200) {
        noErrors = false;
      }
    }
    return noErrors;
  };

  const logoutUser = () => {
    Client.logout(tokens.refreshToken)
      .then(() => {
        logout();
        navigate('/login');
      })
      .catch((err) => {
        throw new Error(err.response.data.message);
      });
  };

  const changeEmail = (form: IEmailChangeForm) => {
    const { email, password } = form;
    Client.changeEmail(email.value, password.value, user.id)
      .then(() => {
        logoutUser();
      })
      .catch((err) => {
        setError(err.response.data.message);
      });
  };

  const handleOnSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setError('');
    resetFormAttributes(form, 'error');
    if (!validateForm(form)) {
      return;
    }

    changeEmail(form);
  };

  return (
    <Flex
      flexDir={['column', 'column', 'row']}
      mt="5rem"
      align="flex-start"
      justify="space-between"
    >
      <Box>
        <Text fontSize="1.2rem">Change Email</Text>
        <Text fontSize="0.85rem">Email last updated on</Text>
        {emailLastUpdated.length > 0 && (
          <Flex align="center" color="limegreen">
            <Box mr="0.25rem" fontSize="0.85rem">
              <AiOutlineCheck />
            </Box>
            <Text fontSize="0.85rem" fontWeight="bold">
              {emailLastUpdated}
            </Text>
          </Flex>
        )}
      </Box>
      <Box mt="1rem">
        <Box mb="0.25rem">
          {!isOpen && (
            <>
              <Text
                width={['100%', '350px', '350px']}
                maxW={['100%', '350px', '350px']}
                color="text.primary"
                fontSize="0.85rem"
              >
                The address used to verify your Parana account to you and others. You can
                change this address.
              </Text>
              <Flex
                onClick={onToggle}
                cursor="pointer"
                p="0.5rem"
                align="center"
                width={['100%', '350px', '350px']}
                maxW={['100%', '350px', '350px']}
                minH="40px"
                borderRadius={12}
                border="1px solid"
                borderColor="border.primary"
              >
                <Text fontSize="0.9rem">Change email...</Text>
              </Flex>
            </>
          )}
          <Fade in={isOpen}>
            <form onSubmit={handleOnSubmit}>
              <Box
                border="1px solid"
                borderColor="border.primary"
                p="0.35rem"
                borderRadius={8}
                width={['100%', '350px', '350px']}
                maxW={['100%', '350px', '350px']}
              >
                <Flex justify="flex-end" m="1rem">
                  <Box cursor="pointer" onClick={handleOnClose}>
                    <AiOutlineClose />
                  </Box>
                </Flex>
                {error.length > 0 && (
                  <Flex justify="center" m="1rem">
                    <Text fontSize="0.8rem" color="error.primary" textAlign="center">
                      {error}
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
                  placeHolder="Enter new email"
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
                <Flex justify="center">
                  <Button type="submit" colorScheme="blackAlpha">
                    Proceed
                  </Button>
                </Flex>
              </Box>
            </form>
          </Fade>
        </Box>
      </Box>
    </Flex>
  );
};

export default ChangeEmailForm;
