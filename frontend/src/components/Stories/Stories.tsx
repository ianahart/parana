import { Box, Flex } from '@chakra-ui/react';
import { useContext, useEffect, useRef, useState } from 'react';
import { over } from 'stompjs';
import SockJS from 'sockjs-client';
import { UserContext } from '../../context/user';
import { IConnectionStory, IUserContext } from '../../interfaces';
import { storyPaginationState } from '../../state/initialState';
import { Client } from '../../util/client';
import Story from './Story';
import { BsChevronLeft, BsChevronRight } from 'react-icons/bs';

let stompClient: any = null;
const Stories = () => {
  const shouldRun = useRef(true);
  const { user } = useContext(UserContext) as IUserContext;
  const [pagination, setPagination] = useState(storyPaginationState);
  const [connectionStories, setConnectionStories] = useState<IConnectionStory[]>([]);

  const getStories = (paginate: boolean, userId: number, direction: string) => {
    const pageNum = paginate ? pagination.page : -1;
    Client.getConnectionStories(userId, pageNum, pagination.pageSize, direction)
      .then((res) => {
        const { connections, direction, page, pageSize, totalElements, totalPages } =
          res.data.data;
        setPagination((prevState) => ({
          ...prevState,
          page,
          pageSize,
          direction,
          totalPages,
          totalElements,
        }));

        setConnectionStories(connections);
      })
      .catch((err) => {
        throw new Error(err);
      });
  };

  const connect = () => {
    let Sock = new SockJS('https://parana-hart-6c0dd51d52f9.herokuapp.com/wss');
    stompClient = over(Sock);
    stompClient.connect({}, onConnected, onError);
  };

  const onConnected = () => {
    stompClient.subscribe(`/user/${user.id}/stories`, onPrivateStory);
  };
  const onError = () => {};

  const addStory = (story: any) => {
    setConnectionStories((prevState) => {
      const exists = prevState.findIndex((cs) => cs.userId == story.userId);
      if (exists === -1) {
        return [
          {
            userId: story.userId,
            fullName: story.fullName,
            avatarUrl: story.avatarUrl,
            stories: [story],
          },
          ...prevState,
        ];
      } else {
        return prevState.map((connectionStory) => {
          if (connectionStory.userId === story.userId) {
            connectionStory.stories = [story, ...connectionStory.stories];
          }
          return connectionStory;
        });
      }
    });
  };

  const onPrivateStory = (payload: any) => {
    addStory(JSON.parse(payload.body));
  };

  useEffect(() => {
    if (shouldRun.current && user.id !== 0) {
      shouldRun.current = false;
      connect();
      getStories(false, user.id, 'next');
    }
  }, [shouldRun.current, user.id]);

  useEffect(() => {
    return () => {
      if (stompClient !== null) {
        stompClient.disconnect();
      }
    };
  }, []);

  return (
    <Box ml={['0', '0', '5rem']} minH="100vh" mt="5rem">
      <Flex align="center">
        {pagination.page > 0 && (
          <Flex
            onClick={() => getStories(true, user.id, 'prev')}
            fontSize="3rem"
            color="text.secondary"
          >
            <BsChevronLeft />
          </Flex>
        )}
        <Flex width={['95%', '95%', '500px']} overflowX="auto" justify="flex-start">
          {connectionStories.map((connectionStory) => {
            return (
              <Story key={connectionStory.userId} connectionStory={connectionStory} />
            );
          })}
        </Flex>
        {pagination.page < pagination.totalPages - 1 && (
          <Flex
            onClick={() => getStories(true, user.id, 'next')}
            color="text.secondary"
            fontSize="3rem"
          >
            <BsChevronRight />
          </Flex>
        )}
      </Flex>
    </Box>
  );
};

export default Stories;
