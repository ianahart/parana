import {
  Box,
  Button,
  Divider,
  Flex,
  Text,
  Modal,
  Select,
  ModalOverlay,
  ModalContent,
  ModalHeader,
  ModalFooter,
  ModalBody,
  ModalCloseButton,
  useDisclosure,
} from '@chakra-ui/react';
import { IoOptionsOutline } from 'react-icons/io5';
import { BsList } from 'react-icons/bs';
import { CiGrid41 } from 'react-icons/ci';
import { yearState, monthState } from '../../../state/initialState';
import { AiOutlineClose } from 'react-icons/ai';

interface IManagePostsProps {
  postView: string;
  setPostView: (postView: string) => void;
  getFilteredPosts: (paginate: boolean, year: number, month: number) => void;
  resetState: () => void;
  setFiltersApplied: (filtersApplied: boolean) => void;
  filtersApplied: boolean;
  setYear: (year: number) => void;
  setMonth: (month: number) => void;
  year: number;
  month: number;
}

const ManagePosts = ({
  postView,
  setPostView,
  getFilteredPosts,
  resetState,
  setFiltersApplied,
  filtersApplied,
  setYear,
  setMonth,
  year,
  month,
}: IManagePostsProps) => {
  const { isOpen, onOpen, onClose } = useDisclosure();
  const handleOnApplyFilters = () => {
    setFiltersApplied(true);
    getFilteredPosts(false, year, month);
    onClose();
  };

  const clearFilters = () => {
    setYear(yearState[0].value);
    setMonth(monthState[0].value);
    setFiltersApplied(false);
    resetState();
  };

  const handleOnYearChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setYear(parseInt(e.target.value));
  };

  const handleOnMonthChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setMonth(parseInt(e.target.value));
  };

  return (
    <Box
      p="1rem"
      border="1px solid"
      borderColor="border.primary"
      borderRadius={8}
      my="1.5rem"
    >
      {!filtersApplied && (
        <Flex justify="space-between">
          <Text fontSize="1.2rem" fontWeight="bold">
            Posts
          </Text>
          <Button onClick={onOpen} colorScheme="blackAlpha">
            <Box mr="0.25rem">
              <IoOptionsOutline />
            </Box>
            <Text>Filters</Text>
          </Button>
        </Flex>
      )}
      {filtersApplied && (
        <Flex my="0.5rem" justify="flex-end">
          <Button onClick={clearFilters} colorScheme="blackAlpha">
            <Box mr="0.25rem">
              <AiOutlineClose />
            </Box>
            <Text>Clear filters</Text>
          </Button>
        </Flex>
      )}

      <Modal isOpen={isOpen} onClose={onClose}>
        <ModalOverlay />
        <ModalContent bg="primary.dark" color="text.secondary">
          <ModalHeader>Filter Posts</ModalHeader>
          <ModalCloseButton />
          <ModalBody>
            <Flex justify="center" mt="1.5rem" mb="3rem">
              <Box m="1rem">
                <Select
                  defaultValue={yearState[0].value}
                  onChange={handleOnYearChange}
                  placeholder="Select a year"
                  borderColor="border.primary"
                >
                  {yearState.map((year) => {
                    return (
                      <option key={year.id} value={year.value}>
                        {year.name}
                      </option>
                    );
                  })}
                </Select>
              </Box>
              <Box m="1rem">
                <Select
                  defaultValue={monthState[0].value}
                  onChange={handleOnMonthChange}
                  placeholder="Select a month"
                  borderColor="border.primary"
                >
                  {monthState.map((month) => {
                    return (
                      <option key={month.id} value={month.value}>
                        {month.name}
                      </option>
                    );
                  })}
                </Select>
              </Box>
            </Flex>
          </ModalBody>

          <ModalFooter>
            <Button colorScheme="blackAlpha" mr={3} onClick={handleOnApplyFilters}>
              Apply
            </Button>
            <Button onClick={onClose} colorScheme="gray">
              Cancel
            </Button>
          </ModalFooter>
        </ModalContent>
      </Modal>
      <Divider my="0.25rem" />
      <Flex justify="space-around" fontSize="0.9rem" my="0.5rem">
        <Flex
          align="center"
          cursor="pointer"
          onClick={() => setPostView('list')}
          color={postView === 'list' ? 'primary.light' : 'text.secondary'}
        >
          <Box fontSize="1.1rem" mr="0.25rem">
            <BsList />
          </Box>
          <Text
            borderBottom={postView === 'list' ? '2px solid' : 'none'}
            borderColor="text.secondary"
          >
            List view
          </Text>
        </Flex>
        <Flex
          align="center"
          cursor="pointer"
          onClick={() => setPostView('grid')}
          color={postView === 'grid' ? 'primary.light' : 'text.secondary'}
        >
          <Box fontSize="1.1rem" mr="0.25rem">
            <CiGrid41 />
          </Box>
          <Text
            borderBottom={postView === 'grid' ? '2px solid' : 'none'}
            borderColor="text.secondary"
            fontSize="0.9rem"
          >
            Grid view
          </Text>
        </Flex>
      </Flex>
    </Box>
  );
};

export default ManagePosts;
