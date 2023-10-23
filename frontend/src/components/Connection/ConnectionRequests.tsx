import { Box, Heading, Flex, Grid, GridItem, Text, Button } from '@chakra-ui/react';
import { useContext, useEffect, useRef, useState } from 'react';
import { UserContext } from '../../context/user';
import { IConnectionRequest, IUserContext } from '../../interfaces';
import { Client } from '../../util/client';
import ConnectionRequest from './ConnectionRequest';
import ViewChanger from './ViewChanger';

const ConnectionRequests = () => {
  const { user } = useContext(UserContext) as IUserContext;
  const shouldRun = useRef(true);
  const [connectionRequests, setConnectionRequests] = useState<IConnectionRequest[]>([]);
  const [serverError, setServerError] = useState('');
  const [mobileView, setMobileView] = useState(false);
  const [pagination, setPagination] = useState({
    page: 0,
    pageSize: 6,
    totalPages: 0,
    direction: 'next',
    totalElements: 0,
  });

  const getConnectionRequests = (paginate: boolean) => {
    const { id, role } = user;
    const pageNum = paginate ? pagination.page : -1;
    Client.getConnectionRequests(
      id,
      role,
      pageNum,
      pagination.pageSize,
      pagination.direction
    )
      .then((res) => {
        const { connections, direction, page, pageSize, totalElements, totalPages } =
          res.data.data;
        setPagination((prevState) => ({
          ...prevState,
          direction,
          page,
          pageSize,
          totalElements,
          totalPages,
        }));
        setConnectionRequests((prevState) => [...prevState, ...connections]);
      })

      .catch((err) => {
        throw new Error(err.respones.data.message);
      });
  };

  useEffect(() => {
    if (user.id !== 0 && shouldRun.current) {
      shouldRun.current = false;
      getConnectionRequests(false);
    }
  }, [user.id, shouldRun.current]);

  const removeConnectionRequest = (connectionId: number) => {
    setServerError('');
    Client.removeConnectionRequest(connectionId)
      .then(() => {
        setConnectionRequests((prevState) =>
          prevState.filter((cr) => cr.id !== connectionId)
        );
      })
      .catch((err) => {
        setServerError(err.response.data.message);
        throw new Error(err.response.data.message);
      });
  };

  const confirmConnectionRequest = (connectionId: number, currentUserId: number) => {
    Client.confirmConnectionRequest(connectionId, currentUserId)
      .then(() => {
        setConnectionRequests((prevState) =>
          prevState.filter((cr) => cr.id !== connectionId)
        );
      })
      .catch((err) => {
        throw new Error(err.response.data.message);
      });
  };

  return (
    <Box color="#fff">
      <Flex color="text.secondary" align="center" justify="space-between">
        <Box as="header">
          <Heading fontSize="1.2rem">
            {user.role === 'TEACHER' ? 'Connection Requests' : 'Sent Connection Requests'}
          </Heading>
          <Text fontWeight="bold" mt="1.5rem">
            {pagination.totalElements} requests available
          </Text>
        </Box>
        <ViewChanger mobileView={mobileView} setMobileView={setMobileView} />
      </Flex>
      {serverError.length > 0 && (
        <Flex justify="center" my="0.5rem">
          <Text fontSize="0.85rem" color="error.primary">
            {serverError}
          </Text>
        </Flex>
      )}
      <Grid
        mt="4rem"
        mb="2rem"
        gridTemplateColumns={
          !mobileView
            ? ['repeat(1,1fr)', 'repeat(2,1fr)', 'repeat(3, 1fr)']
            : 'repeat(1, 1fr)'
        }
      >
        {connectionRequests.map((connectionRequest) => {
          return (
            <GridItem justifySelf="center" key={connectionRequest.id}>
              <ConnectionRequest
                confirmConnectionRequest={confirmConnectionRequest}
                removeConnectionRequest={removeConnectionRequest}
                connectionRequest={connectionRequest}
                role={user.role}
                currentUserId={user.id}
              />
            </GridItem>
          );
        })}
      </Grid>
      {pagination.page < pagination.totalPages - 1 && (
        <Flex my="2rem" justify="center">
          <Button onClick={() => getConnectionRequests(true)} colorScheme="blackAlpha">
            See more requests
          </Button>
        </Flex>
      )}
    </Box>
  );
};

export default ConnectionRequests;
