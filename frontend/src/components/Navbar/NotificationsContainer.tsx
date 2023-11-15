import { Box, useDisclosure } from '@chakra-ui/react';
import { IoNotificationsOutline } from 'react-icons/io5';
import { over } from 'stompjs';
import SockJS from 'sockjs-client';
import { useContext, useRef, useEffect, useState } from 'react';
import { UserContext } from '../../context/user';
import { INotification, IUserContext } from '../../interfaces';

let stompClient: any = null;

const NotificationsContainer = () => {
  const shouldRun = useRef(true);
  const [notifications, setNotifications] = useState<INotification[]>([]);
  const { user } = useContext(UserContext) as IUserContext;

  const connect = () => {
    let Sock = new SockJS('https://parana-hart-6c0dd51d52f9.herokuapp.com/wss');
    stompClient = over(Sock);
    stompClient.connect({}, onConnected, onError);
  };

  useEffect(() => {
    if (shouldRun.current && user.id !== 0) {
      shouldRun.current = false;

      connect();
    }
  }, [shouldRun.current, connect, user.id]);

  const onConnected = () => {
    stompClient.subscribe(`/user/${user.id}/notifications`, onNotification);
  };
  const onError = () => {};

  const addNotification = (notification: any) => {
    console.log(notification);
    setNotifications((prevState) => [notification, ...prevState]);
  };

  const onNotification = (payload: any) => {
    addNotification(JSON.parse(payload.body));
  };

  useEffect(() => {
    return () => stompClient.disconnect();
  }, []);

  return (
    <Box mx="1rem" color="text.secondary">
      <Box fontSize="1.4rem" cursor="pointer">
        <IoNotificationsOutline />
      </Box>
    </Box>
  );
};

export default NotificationsContainer;
