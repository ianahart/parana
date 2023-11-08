import { Box, Flex, Text, Switch, FormLabel, FormControl } from '@chakra-ui/react';
import { IPrivacy } from '../../interfaces';
import UserAvatar from '../Shared/UserAvatar';

interface IBlockedUserProps {
  privacy: IPrivacy;
  handleUpdatePrivacy: (name: string, checked: boolean, privacyId: number) => void;
}

const BlockedUser = ({ privacy, handleUpdatePrivacy }: IBlockedUserProps) => {
  const handleOnSwitchChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, checked } = e.target;
    handleUpdatePrivacy(name, checked, privacy.id);
  };

  return (
    <Flex
      p="0.25rem"
      justify="space-between"
      my="1rem"
      borderBottom="1px solid"
      borderColor="border.primary"
    >
      <Flex>
        <UserAvatar
          width="40px"
          height="40px"
          fullName={privacy.fullName}
          avatarUrl={privacy.avatarUrl}
        />
        <Box ml="0.5rem">
          <Text fontWeight="bold" fontSize="0.85rem">
            {privacy.fullName}
          </Text>
        </Box>
      </Flex>
      <Flex align="center" flexDir={['column', 'column', 'row']} justify="space-around">
        <FormControl mx="0.5rem" display="flex" alignItems="center">
          <FormLabel fontSize="0.75rem" htmlFor="messages" mb="0">
            Messages
          </FormLabel>
          <Switch
            onChange={handleOnSwitchChange}
            isChecked={privacy.messages}
            name="messages"
            colorScheme="purple"
            id="messages"
          />
        </FormControl>
        <FormControl mx="0.5rem" display="flex" alignItems="center">
          <FormLabel fontSize="0.75rem" htmlFor="posts" mb="0">
            Posts
          </FormLabel>
          <Switch
            onChange={handleOnSwitchChange}
            isChecked={privacy.posts}
            name="posts"
            colorScheme="purple"
            id="posts"
          />
        </FormControl>
        <FormControl mx="0.5rem" display="flex" alignItems="center">
          <FormLabel fontSize="0.75rem" htmlFor="comments" mb="0">
            Comments
          </FormLabel>
          <Switch
            onChange={handleOnSwitchChange}
            isChecked={privacy.comments}
            name="comments"
            colorScheme="purple"
            id="comments"
          />
        </FormControl>
      </Flex>
    </Flex>
  );
};
export default BlockedUser;
