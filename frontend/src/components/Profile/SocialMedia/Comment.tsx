import {
  Box,
  Button,
  Flex,
  Text,
  Popover,
  PopoverTrigger,
  PopoverContent,
  PopoverBody,
  PopoverCloseButton,
  IconButton,
  useDisclosure,
} from '@chakra-ui/react';
import { IComment, IUserContext } from '../../../interfaces';
import UserAvatar from '../../Shared/UserAvatar';
import { BsThreeDots, BsTrash } from 'react-icons/bs';
import { useContext } from 'react';
import { UserContext } from '../../../context/user';

interface ICommentProps {
  comment: IComment;
  ownerId: number;
  deleteComment: (commentId: number, ownerId: number) => void;
}

const Comment = ({ comment, ownerId, deleteComment }: ICommentProps) => {
  const { onOpen, onClose, isOpen } = useDisclosure();
  const { user } = useContext(UserContext) as IUserContext;

  const handleDeleteComment = (e: React.MouseEvent<HTMLDivElement>) => {
    e.stopPropagation();
    deleteComment(comment.id, ownerId);
    onClose();
  };

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
      <Box
        mx="0.5rem"
        pointerEvents={
          user.id === comment.userId || user.id === ownerId ? 'unset' : 'none'
        }
      >
        <Popover isOpen={isOpen} onOpen={onOpen} onClose={onClose}>
          <PopoverTrigger>
            <IconButton
              _hover={{ bg: 'transparent' }}
              colorScheme="ghost"
              aria-label="open"
              size="sm"
              icon={<BsThreeDots />}
            />
          </PopoverTrigger>
          <PopoverContent
            maxW="150px"
            minH="80px"
            boxShadow="md"
            border="none"
            bg="blackAlpha.700"
          >
            <PopoverCloseButton />
            <PopoverBody>
              <Flex
                onClick={(e) => handleDeleteComment(e)}
                _hover={{ bg: 'black.tertiary' }}
                borderRadius={8}
                p="0.5rem"
                align="center"
              >
                <Box mr="0.25rem">
                  <BsTrash />
                </Box>
                <Text>Delete</Text>
              </Flex>
            </PopoverBody>
          </PopoverContent>
        </Popover>
      </Box>
    </Flex>
  );
};

export default Comment;
