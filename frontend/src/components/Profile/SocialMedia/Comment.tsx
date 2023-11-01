import { Box, Button, Flex, Text } from '@chakra-ui/react';
import { IComment } from '../../../interfaces';
import UserAvatar from '../../Shared/UserAvatar';
import { BsThreeDots } from 'react-icons/bs';

interface ICommentProps {
  comment: IComment;
}

const Comment = ({ comment }: ICommentProps) => {
  return (
    <Flex my="1rem" align="center">
      <UserAvatar
        width="35px"
        height="35px"
        fullName={comment.fullName}
        avatarUrl={comment.avatarUrl}
      />
      <Box>
        <Box
          maxW="250px"
          ml="0.5rem"
          bg="primary.dark"
          borderRadius={12}
          px="0.5rem"
          py="0.7rem"
        >
          <Text fontSize="0.85rem" fontWeight="bold">
            {comment.fullName}
          </Text>
          <Text fontSize="0.9rem">{comment.text}</Text>
        </Box>
        <Flex align="center" justify="space-around">
          <Button
            color="text.secondary"
            fontSize="0.8rem"
            colorScheme="ghost"
            _hover={{ bg: 'transparent' }}
          >
            Like
          </Button>
          <Button
            color="text.secondary"
            fontSize="0.8rem"
            colorScheme="ghost"
            _hover={{ bg: 'transparent' }}
          >
            Reply
          </Button>
        </Flex>
      </Box>
      <Box mx="0.5rem" cursor="pointer">
        <BsThreeDots />
      </Box>
    </Flex>
  );
};

export default Comment;
