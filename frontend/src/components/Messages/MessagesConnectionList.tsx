import { Box, Button, Divider, Flex, Heading, Input, Text } from '@chakra-ui/react';
import { useContext, useEffect, useRef, useState } from 'react';
import { UserContext } from '../../context/user';
import { IConnection, IUserContext } from '../../interfaces';
import { connectionPaginationState, connectionState } from '../../state/initialState';
import { Client } from '../../util/client';
import { AiOutlineSearch } from 'react-icons/ai';
import UserAvatar from '../Shared/UserAvatar';
import ChatWindow from './ChatWindow';

const MessagesConnectionList = () => {
  const shouldRun = useRef(true);
  const [pagination, setPagination] = useState(connectionPaginationState);
  const [connections, setConnections] = useState<IConnection[]>([]);
  const [filteredConnections, setFilteredConnections] =
    useState<IConnection[]>(connections);
  const { user } = useContext(UserContext) as IUserContext;
  const [showFilterBox, setShowFilterBox] = useState(false);
  const [filter, setFilter] = useState('');
  const [currentConnection, setCurrentConnection] =
    useState<IConnection>(connectionState);

  const getConnectionList = (paginate: boolean) => {
    const pageNum = paginate ? pagination.page : -1;
    Client.getConnections(user.id, pageNum, pagination.pageSize, pagination.direction, '')
      .then((res) => {
        const { direction, page, pageSize, totalElements, totalPages, connections } =
          res.data.data;
        setPagination((prevState) => ({
          ...prevState,
          page,
          pageSize,
          totalPages,
          direction,
          totalElements,
        }));
        setConnections((prevState) => [...prevState, ...connections]);
        setFilteredConnections((prevState) => [...prevState, ...connections]);
      })
      .catch((err) => {
        throw new Error(err);
      });
  };

  useEffect(() => {
    if (shouldRun.current && user.id !== 0) {
      shouldRun.current = false;
      getConnectionList(false);
    }
  }, []);

  const handleOnFilter = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFilter(e.target.value);

    const filtered = connections.filter((connection) =>
      connection.fullName.toLowerCase().includes(e.target.value.toLowerCase())
    );

    setFilteredConnections(filtered);
  };

  const closeChatWindow = () => {
    setCurrentConnection(connectionState);
  };

  return (
    <>
      <Box
        color="text.secondary"
        className="overflow-scroll"
        borderRadius={8}
        boxShadow="md"
        height="350px"
        overflowY="auto"
        bg="black.tertiary"
        width={['95%', '95%', '260px']}
        maxWidth="260px"
        position="fixed"
        p="1rem"
        zIndex={20}
        top="60%"
        right="0"
      >
        <Flex justify="space-between">
          <Box>
            <Heading fontSize="0.9rem" as="h3">
              Connections ({pagination.totalElements})
            </Heading>
          </Box>
          <Box>
            <Box
              _hover={{ opacity: '0.7' }}
              cursor="pointer"
              onClick={() => setShowFilterBox((prevState) => !prevState)}
            >
              <AiOutlineSearch />
            </Box>
          </Box>
        </Flex>
        {showFilterBox && (
          <Box my="0.25rem">
            <Input
              value={filter}
              onChange={handleOnFilter}
              placeholder="Filter connections..."
              size="sm"
              borderRadius={8}
              borderColor="border.primary"
            />
          </Box>
        )}
        {!showFilterBox && <Divider my="0.25rem" borderColor="#333" />}

        <Box>
          {filteredConnections.map((connection) => {
            return (
              <Flex
                onClick={() => setCurrentConnection(connection)}
                _hover={{ bg: 'border.primary' }}
                borderRadius={8}
                p={1}
                cursor="pointer"
                align="center"
                my="1rem"
                key={connection.id}
              >
                <Box position="relative">
                  <UserAvatar
                    height="30px"
                    width="30px"
                    fullName={connection.fullName}
                    avatarUrl={connection.avatarUrl}
                  />
                  <Box
                    position="absolute"
                    right="0"
                    top="25px"
                    width="8px"
                    height="8px"
                    borderRadius={50}
                    bg={connection.loggedIn ? 'limegreen' : 'text.primary'}
                  >
                    <Box></Box>
                  </Box>
                </Box>
                <Text ml="0.25rem" fontWeight="bold" fontSize="0.8rem">
                  {connection.fullName}
                </Text>
              </Flex>
            );
          })}
        </Box>
        {pagination.page < pagination.totalPages - 1 && (
          <Flex justify="center">
            <Button
              fontSize="0.8rem"
              _hover={{ bg: 'transparent' }}
              bg="transparent"
              color="text.secondary"
              size="sm"
              onClick={() => getConnectionList(true)}
            >
              See more
            </Button>
          </Flex>
        )}
      </Box>
      {currentConnection.id !== 0 && (
        <ChatWindow
          currentConnection={currentConnection}
          closeChatWindow={closeChatWindow}
        />
      )}
    </>
  );
};

export default MessagesConnectionList;
