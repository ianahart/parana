import { Box, Flex, Switch, Text } from '@chakra-ui/react';

interface IChangeRememberMeFormProps {
  rememberMe: boolean;
  handleSetRememberMe: (rememberMe: boolean) => void;
}

const ChangeRememberMeForm = ({
  rememberMe,
  handleSetRememberMe,
}: IChangeRememberMeFormProps) => {
  const handleOnSwitchChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    handleSetRememberMe(e.target.checked);
  };

  return (
    <Flex
      my="3rem"
      flexDir={['column', 'column', 'row']}
      align="flex-start"
      justify="space-between"
    >
      <Box>
        <Text fontSize="1.2rem">Remember me</Text>
        <Text
          width={['100%', '350px', '350px']}
          maxW={['100%', '350px', '350px']}
          color="text.primary"
          fontSize="0.85rem"
        >
          Toggling this switch on will allow you to bypass logging in with your email and
          password. This feature will expire in roughly a month for security reasons or
          until you turn this feature off.
        </Text>
      </Box>
      <Box>
        <Switch
          onChange={handleOnSwitchChange}
          isChecked={rememberMe}
          colorScheme="purple"
          size="lg"
        />
      </Box>
    </Flex>
  );
};

export default ChangeRememberMeForm;
