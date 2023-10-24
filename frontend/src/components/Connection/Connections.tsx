import {
  Box,
  Heading,
  Flex,
  Grid,
  GridItem,
  Text,
  Button,
  FormControl,
  Input,
  FormLabel,
} from '@chakra-ui/react';
import { useCallback, useContext, useEffect, useRef, useState } from 'react';
import { UserContext } from '../../context/user';
import { IConnection, IUserContext } from '../../interfaces';
import { Client } from '../../util/client';
import ViewChanger from './ViewChanger';
import Connection from './Connection';
import { AiOutlineSearch } from 'react-icons/ai';
import { debounce } from 'lodash';

const Connections = () => {
  const { user } = useContext(UserContext) as IUserContext;
  const shouldRun = useRef(true);
  const [connections, setConnections] = useState<IConnection[]>([]);
  const [serverError, setServerError] = useState('');
  const [searchTerm, setSearchTerm] = useState('');
  const [mobileView, setMobileView] = useState(false);
  const [pagination, setPagination] = useState({
    page: 0,
    pageSize: 6,
    totalPages: 0,
    direction: 'next',
    totalElements: 0,
  });

  const getConnections = (paginate: boolean, searchTerm: string) => {
    const pageNum = paginate ? pagination.page : -1;
    setServerError('');
    Client.getConnections(
      user.id,
      pageNum,
      pagination.pageSize,
      pagination.direction,
      searchTerm
    )
      .then((res) => {
        const { connections, direction, page, pageSize, totalElements, totalPages } =
          res.data.data;

        setPagination((prevState) => ({
          ...prevState,
          direction,
          totalElements,
          totalPages,
          pageSize,
          page,
        }));
        if (searchTerm.length) {
          setConnections(connections);
        } else {
          setConnections((prevState) => [...prevState, ...connections]);
        }
      })
      .catch((err) => {
        setServerError(err.response.data.message);
        throw new Error(err.response.data.message);
      });
  };

  useEffect(() => {
    if (user.id !== 0 && shouldRun.current) {
      shouldRun.current = false;
      getConnections(false, '');
    }
  }, [user.id, shouldRun.current]);

  const removeConnection = (connectionId: number) => {
    Client.removeConnection(connectionId)
      .then(() => {
        setConnections((prevState) => prevState.filter((c) => c.id !== connectionId));
      })
      .catch((err) => {
        throw new Error(err.response.dat.message);
      });
  };

  const preformDebounce = debounce((query) => {
    setConnections([]);
    getConnections(false, query);
  }, 250);

  const debounceRequest = useCallback(
    (query: string) => preformDebounce(query),
    [user.id]
  );

  const handleOnChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    debounceRequest(e.target.value);
    setSearchTerm(e.target.value);
  };

  return (
    <Box color="#fff">
      <Flex color="text.secondary" align="center" justify="space-between">
        <Box as="header">
          <Heading fontSize="1.2rem">Connections</Heading>
          <Text fontWeight="bold" mt="1.5rem">
            {pagination.totalElements} connections available
          </Text>
        </Box>
        <ViewChanger mobileView={mobileView} setMobileView={setMobileView} />
      </Flex>
      <Flex my="2rem" justify="center">
        <FormControl>
          <FormLabel color="text.secondary">Search</FormLabel>
          <Box pos="relative">
            <Input
              _hover={{ borderColor: 'border.primary' }}
              color="text.secondary"
              p="1rem"
              _placeholder={{ padding: '1rem' }}
              placeholder="Search friends"
              borderRadius={20}
              borderColor="border.primary"
              onChange={handleOnChange}
              value={searchTerm}
              id="search"
              name="search"
            />
            {searchTerm.length === 0 && (
              <Box
                pos="absolute"
                top="9px"
                left="5px"
                fontSize="1.3rem"
                color="text.primary"
              >
                <AiOutlineSearch />
              </Box>
            )}
          </Box>
        </FormControl>
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
        {connections.map((connection) => {
          return (
            <GridItem key={connection.id}>
              <Connection connection={connection} removeConnection={removeConnection} />
            </GridItem>
          );
        })}
      </Grid>
      {pagination.page < pagination.totalPages - 1 && (
        <Flex my="2rem" justify="center">
          <Button
            onClick={() => getConnections(true, searchTerm)}
            colorScheme="blackAlpha"
          >
            See more connections
          </Button>
        </Flex>
      )}
    </Box>
  );
};

export default Connections;
