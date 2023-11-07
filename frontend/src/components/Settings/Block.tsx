import { Box, Button, Flex, Heading, Input, Text } from '@chakra-ui/react';
import { debounce } from 'lodash';
import { useCallback, useContext, useEffect, useState } from 'react';
import { blockPaginationState } from '../../state/initialState';
import { Client } from '../../util/client';
import { UserContext } from '../../context/user';
import { IBlock, IMinimalUser, IUserContext } from '../../interfaces';
import UserAvatar from '../Shared/UserAvatar';

interface IBlockProps {
  heading: string;
  text: string;
  blockType: string;
  block: IBlock;
}

const Block = ({ heading, text, blockType, block }: IBlockProps) => {
  const { user } = useContext(UserContext) as IUserContext;
  const [users, setUsers] = useState<IMinimalUser[]>([]);
  const [pagination, setPagination] = useState(blockPaginationState);
  const [name, setName] = useState('');
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);
  const [selectedUser, setSelectedUser] = useState({
    userId: 0,
    avatarUrl: '',
    fullName: '',
  });

  const search = (paginate: boolean, query: string) => {
    if (query.trim().length === 0) {
      setPagination(blockPaginationState);
      setUsers([]);
      return;
    }

    const pageNum = paginate ? pagination.page : -1;
    Client.getNonBlockedUsers(
      user.id,
      blockType,
      query,
      pageNum,
      pagination.pageSize,
      pagination.direction
    )
      .then((res) => {
        const { direction, page, pageSize, totalElements, totalPages, users } =
          res.data.data;
        setPagination((prevState) => ({
          ...prevState,
          direction,
          page,
          pageSize,
          totalElements,
          totalPages,
        }));

        if (paginate) {
          setUsers((prevState) => [...prevState, ...users]);
        } else {
          setUsers(users);
        }
      })
      .catch((err) => {
        throw new Error(err);
      });
  };

  const preformDebounce = debounce((query) => {
    search(false, query);
  }, 250);

  const debounceRequest = useCallback((query: string) => preformDebounce(query), []);

  const handleOnChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setName(e.target.value);
    debounceRequest(e.target.value);
  };

  const handleSetSelectedUser = (user: IMinimalUser) => {
    setSelectedUser(user);
    setName(user.fullName);
    setIsDropdownOpen(false);
  };

  useEffect(() => {
    if (users.length) {
      setIsDropdownOpen(true);
    } else {
      setIsDropdownOpen(false);
    }
  }, [users.length]);

  const blockUser = () => {
    if (selectedUser.userId === 0) return;
    Client.blockUser(user.id, selectedUser.userId, block)
      .then((res) => {
        console.log(res);
        setPagination(blockPaginationState);
        setUsers([]);
        setName("");
      })
      .catch((err) => {
        console.log(err);
        throw new Error(err);
      });
  };

  return (
    <Box my="3rem">
      <Flex flexDir={['column', 'column', 'row']}>
        <Heading mr="2rem" mb={['1rem', '1rem', '0']} fontSize="1.1rem">
          {heading}
        </Heading>
        <Text color="text.primary">{text}</Text>
      </Flex>
      <Flex position="relative" my="2rem">
        <Box width="90%" position="relative">
          <Input
            onChange={handleOnChange}
            value={name}
            fontSize="0.9rem"
            mr="0.5rem"
            width="95%"
            borderColor="border.primary"
            placeholder="Enter a user..."
          />
          {isDropdownOpen && (
            <Box
              borderRadius={8}
              p="0.5rem"
              mr="0.5rem"
              width="95%"
              bg="black.tertiary"
              position="absolute"
              top="45px"
              zIndex={5}
              left="0"
            >
              {users.map(({ userId, avatarUrl, fullName }) => {
                return (
                  <Flex
                    onClick={() => handleSetSelectedUser({ userId, avatarUrl, fullName })}
                    cursor="pointer"
                    my="1rem"
                    key={userId}
                  >
                    <UserAvatar
                      fullName={fullName}
                      avatarUrl={avatarUrl}
                      height="35px"
                      width="35px"
                    />
                    <Text ml="0.5rem">{fullName}</Text>
                  </Flex>
                );
              })}
              {pagination.page < pagination.totalPages - 1 && (
                <Flex my="0.5rem" justify="center">
                  <Button
                    onClick={() => search(true, name)}
                    bg="transparent"
                    color="text.secondary"
                    _hover={{ background: 'transparent' }}
                    size="sm"
                  >
                    See more
                  </Button>
                </Flex>
              )}
            </Box>
          )}
        </Box>
        <Button onClick={blockUser} colorScheme="blackAlpha">
          Block
        </Button>
      </Flex>
    </Box>
  );
};

export default Block;
