import { Box, Heading, Text, ListItem, UnorderedList, Flex } from '@chakra-ui/react';
import { useRef, useState, useEffect, useContext } from 'react';
import {
  BsChevronDoubleLeft,
  BsChevronDoubleRight,
  BsImages,
  BsPencil,
} from 'react-icons/bs';
import { Link as RouterLink, useLocation } from 'react-router-dom';
import { UserContext } from '../../context/user';
import { IUserContext } from '../../interfaces';
import UserAvatar from '../Shared/UserAvatar';

const Sidebar = () => {
  const { user } = useContext(UserContext) as IUserContext;
  const shouldRun = useRef(true);
  const [selected, setSelected] = useState('/stories/all');
  const [sidebarOpen, setSidebarOpen] = useState(true);
  const location = useLocation();

  useEffect(() => {
    if (shouldRun.current) {
      shouldRun.current = false;
      setSelected(location.pathname);
    }
  }, [shouldRun.current, location.pathname]);

  return (
    <Box
      p="1rem"
      pos="relative"
      height="100vh"
      width="360px"
      minW="360px"
      transition="ease-out all 0.3s"
      transform={!sidebarOpen ? 'translateX(-360px)' : 'unset'}
      border="1px solid"
      borderLeft="none"
      borderColor="border.primary"
      borderTopRightRadius={8}
      borderBottomRadius={8}
    >
      <Flex
        display={['none', 'flex', 'flex']}
        dir="column"
        align="center"
        justify="center"
        borderRightRadius={50}
        boxShadow="lg"
        pos="absolute"
        right="-50px"
        zIndex={3}
        bg="primary.light"
        width="50px"
        height="50px"
      >
        {sidebarOpen ? (
          <Box
            cursor="pointer"
            onClick={() => setSidebarOpen(false)}
            fontSize="1.2rem"
            color="text.secondary"
          >
            <BsChevronDoubleLeft />
          </Box>
        ) : (
          <Box
            cursor="pointer"
            onClick={() => setSidebarOpen(true)}
            fontSize="1.2rem"
            color="text.secondary"
          >
            <BsChevronDoubleRight />
          </Box>
        )}
      </Flex>
      <Box>
        <Box as="header">
          <Heading color="text.secondary" fontSize="1.3rem">
            Stories
          </Heading>
        </Box>
        <Box my="2rem" color="text.secondary">
          <Text fontSize="1.1rem" fontWeight="bold">
            Your Story
          </Text>
          <Flex my="1rem" align="center">
            <Flex
              dir="column"
              align="center"
              justify="center"
              height="50px"
              width="50px"
              borderRadius={50}
              border="3px solid"
              cursor="pointer"
            >
              <UserAvatar
                height="45px"
                width="45px"
                avatarUrl={user.avatarUrl}
                fullName={user.fullName}
              />
            </Flex>
            <Text ml="0.5rem">{user.fullName}</Text>
          </Flex>
        </Box>
        <Box my="3rem" color="text.secondary">
          <UnorderedList listStyleType="none">
            <ListItem
              onClick={() => setSelected('/stories/create')}
              borderRadius={8}
              p="0.5rem"
              _hover={{ bg: 'black.tertiary' }}
              my="1rem"
            >
              <RouterLink to="/stories/create">
                <Flex align="center">
                  <Flex
                    dir="column"
                    align="center"
                    height="40px"
                    width="40px"
                    justify="center"
                    bg={
                      selected === '/stories/create' ? 'primary.light' : 'black.tertiary'
                    }
                    fontSize="1.4rem"
                    borderRadius={50}
                    mr="0.25rem"
                  >
                    <BsPencil />
                  </Flex>
                  <Text fontSize="1.1rem" fontWeight="bold">
                    Create
                  </Text>
                </Flex>
              </RouterLink>
            </ListItem>
            <ListItem
              onClick={() => setSelected('/stories/all')}
              borderRadius={8}
              p="0.5rem"
              _hover={{ bg: 'black.tertiary' }}
              my="1rem"
            >
              <RouterLink to="/stories/all">
                <Flex align="center">
                  <Flex
                    dir="column"
                    align="center"
                    height="40px"
                    width="40px"
                    justify="center"
                    bg={selected === '/stories/all' ? 'primary.light' : 'black.tertiary'}
                    fontSize="1.4rem"
                    borderRadius={50}
                    mr="0.25rem"
                  >
                    <BsImages />
                  </Flex>
                  <Text fontSize="1.1rem" fontWeight="bold">
                    View Stories
                  </Text>
                </Flex>
              </RouterLink>
            </ListItem>
          </UnorderedList>
        </Box>
      </Box>
    </Box>
  );
};

export default Sidebar;
