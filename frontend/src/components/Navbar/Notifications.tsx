import { Box, Button, Flex, Text, useOutsideClick } from '@chakra-ui/react';
import { IoNotificationsOutline } from 'react-icons/io5';
import { over } from 'stompjs';
import SockJS from 'sockjs-client';
import { useContext, useRef, useEffect, useState } from 'react';
import { UserContext } from '../../context/user';
import { INotification, IUserContext } from '../../interfaces';
import { Client } from '../../util/client';
import { notificationPaginationState } from '../../state/initialState';
import UserAvatar from '../Shared/UserAvatar';
import { AiOutlineClose } from 'react-icons/ai';
import { nanoid } from 'nanoid';

let stompClient: any = null;

const Notifications = () => {
  const shouldRun = useRef(true);
  const [notifications, setNotifications] = useState<INotification[]>([]);
  const [notificationsOpen, setNotificationsOpen] = useState(false);
  const [pagination, setPagination] = useState(notificationPaginationState);
  const { user } = useContext(UserContext) as IUserContext;
  const ref = useRef<HTMLDivElement>(null);

  useOutsideClick({
    ref,
    handler: () => setNotificationsOpen(false),
  });

  const connect = () => {
    let Sock = new SockJS('http://localhost:8080/ws');
    stompClient = over(Sock);
    stompClient.connect({}, onConnected, onError);
  };

  const getNotifications = (paginate: boolean, userId: number) => {
    const pageNum = paginate ? pagination.page : -1;
    Client.getNotifications(userId, pageNum, pagination.pageSize, pagination.direction)
      .then((res) => {
        const { direction, notifications, page, pageSize, totalElements, totalPages } =
          res.data.data;

        setPagination((prevState) => ({
          ...prevState,
          page,
          pageSize,
          direction,
          totalPages,
          totalElements,
        }));

        setNotifications((prevState) => [...notifications, ...prevState]);
      })
      .catch((err) => {
        throw new Error(err);
      });
  };

  useEffect(() => {
    if (shouldRun.current && user.id !== 0) {
      shouldRun.current = false;
      connect();
      getNotifications(false, user.id);
    }
  }, [shouldRun.current, connect, user.id]);

  const onConnected = () => {
    stompClient.subscribe(`/user/${user.id}/notifications`, onNotification);
  };
  const onError = () => {};

  const addNotification = (notification: any) => {
    setNotifications((prevState) => [notification, ...prevState]);
    setPagination((prevState) => ({
      ...prevState,
      totalElements: prevState.totalElements + 1,
    }));
  };

  const onNotification = (payload: any) => {
    addNotification(JSON.parse(payload.body));
  };

  const handleOnDelete = (e: React.MouseEvent<HTMLDivElement>, id: number) => {
    e.stopPropagation();

    Client.removeNotification(id)
      .then(() => {
        setNotifications((prevState) => prevState.filter((n) => n.id !== id));
        setPagination((prevState) => ({
          ...prevState,
          totalElements: prevState.totalElements - 1,
        }));
      })
      .catch((err) => {
        throw new Error(err);
      });
  };

  useEffect(() => {
    return () => {
      if (stompClient !== null) {
        stompClient.disconnect();
      }
    };
  }, []);

  const toggleNotifications = (e: React.MouseEvent<HTMLDivElement>) => {
    e.stopPropagation();
    setNotificationsOpen((prevState) => !prevState);
  };

  return (
    <Box pos="relative" mx="1rem" color="text.secondary">
      <Box>
        <Box fontSize="1.4rem" cursor="pointer" onClick={toggleNotifications}>
          <IoNotificationsOutline />
        </Box>
        {pagination.totalElements > 0 && (
          <Flex
            flexDir="column"
            justify="center"
            align="center"
            fontSize="0.7rem"
            color="#fff"
            fontWeight="bold"
            top="-10px"
            left="-5px"
            pos="absolute"
            borderRadius={4}
            width="20px"
            height="20px"
            bg="error.primary"
          >
            {pagination.totalElements > 9 ? '9+' : pagination.totalElements}
          </Flex>
        )}
        {notificationsOpen && (
          <Box
            ref={ref}
            zIndex={5}
            pos="absolute"
            top="30px"
            right="0"
            bg="primary.dark"
            p="0.5rem"
            borderRadius={8}
            width="300px"
            height="200px"
            overflowY="auto"
            className="overflow-scroll"
            boxShadow="md"
          >
            <Box my="1rme">
              {notifications.map((notification) => {
                return (
                  <Box my="1rem" key={nanoid()}>
                    <Flex>
                      <UserAvatar
                        width="20px"
                        height="20px"
                        fullName={notification.fullName}
                        avatarUrl={notification.avatarUrl}
                      />
                      <Text ml="0.5rem" fontSize="0.7rem">
                        {notification.fullName}
                      </Text>
                    </Flex>
                    <Flex align="center" justify="space-evenly">
                      <Text fontSize="0.7rem">{notification.text}</Text>

                      <Flex
                        onClick={(e) => handleOnDelete(e, notification.id)}
                        cursor="pointer"
                        dir="column"
                        align="center"
                        justify="center"
                        height="20px"
                        width="20px"
                        borderRadius={50}
                        bg="blackAlpha.500"
                        fontSize="0.8rem"
                      >
                        <AiOutlineClose />
                      </Flex>
                    </Flex>
                  </Box>
                );
              })}
            </Box>

            {pagination.page < pagination.totalPages - 1 && (
              <Flex my="0.5rem" justify="center">
                <Button
                  onClick={() => getNotifications(true, user.id)}
                  color="text.secondary"
                  fontSize="0.8rem"
                  _hover={{ bg: 'transparent' }}
                  bg="transparent"
                >
                  See more
                </Button>
              </Flex>
            )}
          </Box>
        )}
      </Box>
    </Box>
  );
};

export default Notifications;
