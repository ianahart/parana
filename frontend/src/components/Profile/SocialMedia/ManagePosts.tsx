import { Box, Button, Divider, Flex, Text } from '@chakra-ui/react';
import { IoOptionsOutline } from 'react-icons/io5';
import { BsList } from 'react-icons/bs';
import { CiGrid41 } from 'react-icons/ci';

interface IManagePostsProps {
  postView: string;
  setPostView: (postView: string) => void;
}

const ManagePosts = ({ postView, setPostView }: IManagePostsProps) => {
  return (
    <Box
      p="1rem"
      border="1px solid"
      borderColor="border.primary"
      borderRadius={8}
      my="1.5rem"
    >
      <Flex justify="space-between">
        <Text fontSize="1.2rem" fontWeight="bold">
          Posts
        </Text>
        <Button colorScheme="blackAlpha">
          <Box mr="0.25rem">
            <IoOptionsOutline />
          </Box>
          <Text>Filters</Text>
        </Button>
      </Flex>
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
