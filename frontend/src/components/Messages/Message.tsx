import { Box, Flex, Text } from '@chakra-ui/react';
import { useContext } from 'react';
import { UserContext } from '../../context/user';
import { IMessage, IUserContext } from '../../interfaces';
import UserAvatar from '../Shared/UserAvatar';

interface IMessageProps {
  message: IMessage;
}

const Message = ({ message }: IMessageProps) => {
  const { user } = useContext(UserContext) as IUserContext;
  return (
    <Flex
      p="0.5rem"
      my="1rem"
      justify={`${user.id === message.senderUserId ? 'flex-end' : 'flex-start'}`}
    >
      <Flex flexDir={`${user.id === message.senderUserId ? 'row' : 'row-reverse'}`}>
        <Box>
          <Text
            fontSize="0.85rem"
            wordBreak="break-all"
            minH="40px"
            minW="150px"
            borderRadius={20}
            bg="#303032"
            textAlign="center"
            p="0.25rem"
            mx="1rem"
            color="text.primary"
          >
            {message.text}
          </Text>
        </Box>
        <Box>
          <UserAvatar
            avatarUrl={message.senderAvatarUrl}
            fullName={message.senderFullName}
            width="30px"
            height="30px"
          />
          <Text fontSize="0.6rem" color="text.secondary">
            {message.readableDate}
          </Text>
        </Box>
      </Flex>
    </Flex>
  );
};
export default Message;
