import { Box, Button, Fade, Flex, Text, Tooltip, useDisclosure } from '@chakra-ui/react';
import { AiOutlineCheck, AiOutlineClose, AiOutlineLock } from 'react-icons/ai';
import { changePasswordState } from '../../state/initialState';
import { IChangePasswordForm, IUserContext } from '../../interfaces';
import { useContext, useEffect, useRef, useState } from 'react';
import FormField from '../Shared/FormField';
import BasicSpinner from '../Shared/BasicSpinner';
import { Client } from '../../util/client';
import { UserContext } from '../../context/user';
import { useNavigate } from 'react-router-dom';

const ChangePasswordForm = () => {
  const navigate = useNavigate();
  const shouldRun = useRef(true);
  const { user, tokens, logout } = useContext(UserContext) as IUserContext;
  const { isOpen, onToggle } = useDisclosure();
  const [form, setForm] = useState(changePasswordState);
  const [isLoading, setIsLoading] = useState(false);
  const [passwordLastUpdated, setPasswordLastUpdated] = useState('');
  const [error, setError] = useState('');

  const getSettings = (settingId: number) => {
    Client.getSettings(settingId)
      .then((res) => {
        const { updatedFormattedDate } = res.data.data;
        setPasswordLastUpdated(updatedFormattedDate ? updatedFormattedDate : '');
      })
      .catch((err) => {
        throw new Error(err);
      });
  };

  useEffect(() => {
    if (shouldRun.current && user.settingId !== 0) {
      shouldRun.current = false;
      getSettings(user.settingId);
    }
  }, [shouldRun.current, user.settingId]);

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

  const updateField = (name: string, value: string, attribute: string) => {
    setForm((prevState) => ({
      ...prevState,
      [name]: { ...prevState[name as keyof IChangePasswordForm], [attribute]: value },
    }));
  };

  const handleOnClose = () => {
    resetForm(form, 'value');
    onToggle();
  };

  const resetForm = (form: IChangePasswordForm, attribute: string) => {
    for (const key of Object.keys(form)) {
      updateField(key, '', attribute);
    }
  };

  const validateForm = (form: IChangePasswordForm) => {
    let noErrors = true;
    for (const key of Object.keys(form)) {
      const { value } = form[key as keyof IChangePasswordForm];
      if (value.trim().length === 0 || value.length > 200) {
        noErrors = false;
      }
    }
    return noErrors;
  };

  const changePassword = (form: IChangePasswordForm) => {
    const { oldPassword, password, confirmPassword } = form;
    setIsLoading(true);
    Client.changePassword(
      user.id,
      oldPassword.value,
      password.value,
      confirmPassword.value
    )
      .then((res) => {
        setPasswordLastUpdated(res.data);
        setIsLoading(false);
        onToggle();
        logoutUser();
      })
      .catch((err) => {
        setIsLoading(false);
        setError(err.response.data.message);
      });
  };

  const handleOnSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    resetForm(form, 'error');
    setError('');
    if (!validateForm(form)) {
      return;
    }
    changePassword(form);
  };

  return (
    <Flex
      flexDir={['column', 'column', 'row']}
      mt="5rem"
      align="flex-start"
      justify="space-between"
    >
      <Box>
        <Text fontSize="1.2rem">Change Password</Text>
        <Text fontSize="0.85rem">Password last updated on</Text>
        {passwordLastUpdated.length > 0 && (
          <Flex align="center" color="limegreen">
            <Box mr="0.25rem" fontSize="0.85rem">
              <AiOutlineCheck />
            </Box>
            <Text fontSize="0.85rem" fontWeight="bold">
              11/11/2023
            </Text>
          </Flex>
        )}
      </Box>
      <Flex my={['1rem', '1rem', '0']} flexDir="column" align="flex-end">
        {!isOpen && (
          <>
            <Box mb="0.25rem">
              <Text
                width={['100%', '350px', '350px']}
                maxW={['100%', '350px', '350px']}
                color="text.primary"
                fontSize="0.85rem"
              >
                Change your password to be more secure. Your new password cannot be the
                same as your old password.
              </Text>
            </Box>
            <Flex
              onClick={handleOnClose}
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
              <Text fontSize="0.9rem">Change password...</Text>
            </Flex>
          </>
        )}
        {/*FORM STARTS HERE*/}
        <Fade in={isOpen}>
          <form onSubmit={handleOnSubmit} style={{ marginTop: '1rem' }}>
            <Box
              border="1px solid"
              borderColor="border.primary"
              borderRadius={8}
              p="0.35rem"
              width={['100%', '350px', '350px']}
              maxW={['100%', '350px', '350px']}
            >
              <Flex m="1rem" justify="flex-end">
                <Tooltip label="Close form">
                  <Box onClick={onToggle} fontSize="1.2rem" cursor="pointer">
                    <AiOutlineClose />
                  </Box>
                </Tooltip>
              </Flex>
              {error.length > 0 && (
                <Flex my="0.5rem" justify="center">
                  <Text fontSize="0.8rem" color="error.primary" textAlign="center">
                    {error}
                  </Text>
                </Flex>
              )}

              <FormField
                updateField={updateField}
                name={form.oldPassword.name}
                value={form.oldPassword.value}
                error={form.oldPassword.error}
                type={form.oldPassword.type}
                label="Current Password"
                id="oldPassword"
                errorField="Current password"
                icon={<AiOutlineLock />}
                placeHolder="Enter your current password"
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
                <Flex my="0.5rem" justify="center">
                  <BasicSpinner
                    color="text.secondary"
                    message="Updating your password..."
                  />
                </Flex>
              )}
              {!isLoading && (
                <Flex justify="center" my="1rem">
                  <Button type="submit" colorScheme="blackAlpha">
                    Proceed
                  </Button>
                </Flex>
              )}
            </Box>
          </form>
        </Fade>
        {/*FORM ENDS HERE*/}
      </Flex>
    </Flex>
  );
};

export default ChangePasswordForm;
