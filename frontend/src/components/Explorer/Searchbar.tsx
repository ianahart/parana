import {
  Box,
  Button,
  Flex,
  Input,
  InputGroup,
  InputLeftElement,
  Text,
  useOutsideClick,
} from '@chakra-ui/react';
import { useNavigate } from 'react-router-dom';
import { debounce } from 'lodash';
import { useCallback, useRef, useState } from 'react';
import { AiOutlineClose, AiOutlineSearch } from 'react-icons/ai';
import { Client } from '../../util/client';
import { IRecentSearch, ISearch } from '../../interfaces';
import UserAvatar from '../Shared/UserAvatar';

const Searchbar = () => {
  const initialPaginationState = {
    page: 0,
    direction: 'next',
    totalPages: 0,
    pageSize: 1,
    totalElements: 0,
  };
  const RECENT_SEARCH_LIMIT = 10;
  const navigate = useNavigate();
  const ref = useRef<HTMLDivElement>(null);
  const [searchTerm, setSearchTerm] = useState('');
  const [searches, setSearches] = useState<ISearch[]>([]);
  const [recentSearches, setRecentSearches] = useState<IRecentSearch[]>([]);
  const [pagination, setPagination] = useState(initialPaginationState);
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);

  useOutsideClick({
    ref,
    handler: () => {
      setIsDropdownOpen(false);
      resetSearches();
    },
  });

  const search = (paginate: boolean, query: string) => {
    const pageNum = paginate ? pagination.page : -1;
    Client.searchTeachers(query, pageNum, pagination.pageSize, pagination.direction)
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
        paginate
          ? setSearches((prevState) => [...prevState, ...users])
          : setSearches(users);
      })
      .catch((err) => {
        throw new Error(err.response.data.message);
      });
  };

  const preformDebounce = debounce((query) => {
    search(false, query);
  }, 250);

  const debounceRequest = useCallback((query: string) => preformDebounce(query), []);

  const resetSearches = () => {
    setPagination(initialPaginationState);
    setSearches([]);
    setRecentSearches([]);
    setSearchTerm('');
  };

  const getRecentSearches = () => {
    setIsDropdownOpen(true);
    Client.getRecentSearches(RECENT_SEARCH_LIMIT)
      .then((res) => {
        const { data } = res.data;
        setRecentSearches(data);
      })
      .catch((err) => {
        throw new Error(err);
      });
  };

  const handleOnChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setSearchTerm(e.target.value);
    if (e.target.value.trim().length) {
      debounceRequest(e.target.value);
    } else {
      resetSearches();
    }
  };

  const goToProfile = (profileId: number) => {
    navigate(`/profiles/${profileId}`);
  };

  const removeRecentSearch = (recentSearchId: number) => {
    Client.deleteRecentSearch(recentSearchId)
      .then(() => {
        setRecentSearches((prevState) =>
          prevState.filter((rs) => rs.recentSearchId !== recentSearchId)
        );
      })
      .catch((err) => {
        throw new Error(err);
      });
  };

  return (
    <Box position="relative">
      <InputGroup>
        <InputLeftElement pointerEvents="none">
          <AiOutlineSearch color="gray" />
        </InputLeftElement>
        <Input
          onClick={getRecentSearches}
          autoComplete="off"
          _hover={{ borderColor: 'border.primary' }}
          value={searchTerm}
          onChange={handleOnChange}
          width={['100%', '350px', '450px']}
          borderRadius={15}
          color="text.secondary"
          id="search"
          name="search"
          borderColor="border.primary"
          type="text"
          placeholder="Search by name..."
        />
      </InputGroup>
      {isDropdownOpen && (
        <Box
          ref={ref}
          borderRadius={15}
          top="40px"
          zIndex={10}
          position="absolute"
          height="250px"
          overflowY="auto"
          className="overflow-scroll"
          bg="primary.dark"
          width={['100%', '350px', '450px']}
        >
          {pagination.totalElements === 0 && (
            <Box color="text.secondary" p="1rem">
              <Text fontSize="0.8rem" fontWeight="bold">
                Recent Searches
              </Text>
              <Box my="1rem">
                {recentSearches.map(({ recentSearchId, term }) => {
                  return (
                    <Flex
                      onClick={() => search(false, term)}
                      _hover={{ background: 'blackAlpha.500' }}
                      p="0.5rem"
                      borderRadius={8}
                      justify="space-between"
                      key={recentSearchId}
                    >
                      <Text cursor="pointer">{term}</Text>
                      <Box
                        cursor="pointer"
                        onClick={() => removeRecentSearch(recentSearchId)}
                      >
                        <AiOutlineClose />
                      </Box>
                    </Flex>
                  );
                })}
              </Box>
            </Box>
          )}
          {pagination.totalElements > 0 && (
            <Flex justify="flex-start" my="1rem" px="1rem">
              <Text color="text.secondary">
                <Box fontWeight="bold" as="span">
                  {pagination.totalElements}
                </Box>{' '}
                search results
              </Text>
            </Flex>
          )}
          {searches.map((search) => {
            return (
              <Flex
                onClick={() => goToProfile(search.profileId)}
                cursor="pointer"
                _hover={{ background: 'blackAlpha.500' }}
                p="1rem"
                key={search.id}
                align="center"
                justify="space-between"
              >
                <Flex align="center">
                  <UserAvatar
                    height="40px"
                    width="40px"
                    fullName={search.fullName}
                    avatarUrl={search.avatarUrl}
                  />
                  <Text ml="0.5rem" color="text.secondary" fontWeight="bold">
                    {search.firstName}
                  </Text>
                </Flex>
                <Box color="text.primary" fontSize="0.8rem">
                  <Text>{search.city}</Text>
                  <Text>{search.state}</Text>
                </Box>
              </Flex>
            );
          })}
          {pagination.page < pagination.totalPages - 1 && (
            <Flex justify="center" mt="1rem" mb="0.5rem">
              <Button onClick={() => search(true, searchTerm)} colorScheme="blackAlpha">
                See more
              </Button>
            </Flex>
          )}
        </Box>
      )}
    </Box>
  );
};

export default Searchbar;
