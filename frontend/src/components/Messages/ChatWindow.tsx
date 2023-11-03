import { Box, Divider, Flex, FormLabel, Input, Text } from '@chakra-ui/react';

import { IConnection, IMessage, IUserContext } from '../../interfaces';
import UserAvatar from '../Shared/UserAvatar';
import { AiOutlineClose } from 'react-icons/ai';
import { useContext, useEffect, useRef, useState } from 'react';
import { UserContext } from '../../context/user';
import { Client } from '../../util/client';
import Message from './Message';
import { over } from 'stompjs';
import SockJS from 'sockjs-client';

interface IChatWindowProps {
  currentConnection: IConnection;
  closeChatWindow: () => void;
}

let stompClient: any = null;

const ChatWindow = ({ currentConnection, closeChatWindow }: IChatWindowProps) => {
  const shouldRun = useRef(true);
  const { user } = useContext(UserContext) as IUserContext;
  const [messageText, setMessageText] = useState('');
  const [messages, setMessages] = useState<IMessage[]>([]);

  const connect = () => {
    let Sock = new SockJS('http://localhost:8080/ws');
    stompClient = over(Sock);
    stompClient.connect({}, onConnected, onError);
  };

  useEffect(() => {
    if (shouldRun.current && user.id !== 0) {
      shouldRun.current = false;
      getMessages(user.id, currentConnection.userId);

      connect();
    }
  }, [shouldRun.current, connect, user.id, currentConnection.userId]);

  const onConnected = () => {
    stompClient.subscribe(`/user/${user.id}/private`, onPrivateMessage);
  };
  const onError = () => {};

  const addMessage = (message: any) => {
    setMessages((prevState) => [message, ...prevState]);
  };

  const onPrivateMessage = (payload: any) => {
    addMessage(JSON.parse(payload.body));
  };

  const sendMessage = (receiverId: number, senderId: number) => {
    if (stompClient && receiverId !== 0 && messageText.trim().length > 0) {
      stompClient.send(
        '/api/v1/private-message',
        {},
        JSON.stringify({ receiverId, senderId, text: messageText })
      );
      setMessageText('');
    }
  };

  const getMessages = (userId: number, connectionUserId: number) => {
    Client.getMessages(userId, connectionUserId)
      .then((res) => {
        setMessages((prevState) => [...prevState, ...res.data.data]);
      })
      .catch((err) => {
        throw new Error(err);
      });
  };

  const handleOnKeyDown = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key.toUpperCase() === 'ENTER') {
      sendMessage(currentConnection.userId, user.id);
    }
  };

  return (
    <Box
      borderRadius={8}
      boxShadow="md"
      height="350px"
      bg="black.tertiary"
      width={['95%', '95%', '285px']}
      maxWidth="285px"
      p="0.5rem"
      zIndex={20}
      top="60%"
      right="265px"
      position="fixed"
      color="text.secondary"
    >
      <Flex align="center" justify="space-between">
        <Box>
          <Flex align="center">
            <UserAvatar
              height="30px"
              width="30px"
              avatarUrl={currentConnection.avatarUrl}
              fullName={currentConnection.fullName}
            />
            <Text ml="0.25rem" fontSize="0.75rem" fontWeight="bold">
              {currentConnection.fullName}
            </Text>
          </Flex>
          <Flex align="center">
            <Box
              mr="0.25rem"
              borderRadius={50}
              height="8px"
              width="8px"
              bg={currentConnection.loggedIn ? 'limegreen' : 'text.primary'}
            ></Box>
            <Text fontSize="0.75rem">
              {currentConnection.loggedIn ? 'Active' : 'Not active'}
            </Text>
          </Flex>
        </Box>
        <Box onClick={closeChatWindow} cursor="pointer">
          <AiOutlineClose />
        </Box>
      </Flex>
      <Divider borderColor="#333" my="0.25rem" />
      <Flex flexDir="column" justify="space-between" height="280px">
        <Flex
          flexDir="column-reverse"
          height="250px"
          className="messages overflow-scroll"
          overflowY="auto"
        >
          {messages.map((message) => {
            return <Message key={message.id} message={message} />;
          })}
        </Flex>
        <Box>
          <FormLabel visibility="hidden" htmlFor="message">
            Message
          </FormLabel>
          <Input
            id="message"
            name="message"
            value={messageText}
            onChange={(e) => setMessageText(e.target.value)}
            onKeyDown={handleOnKeyDown}
            fontSize="0.85rem"
            _placeholder={{ fontSize: '0.85rem' }}
            placeholder="Write a message..."
            bg="#333"
            border="none"
            type="text"
          />
        </Box>
      </Flex>
    </Box>
  );
};

export default ChatWindow;
