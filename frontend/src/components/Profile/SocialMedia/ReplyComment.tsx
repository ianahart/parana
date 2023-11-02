import {
  Box,
  Flex,
  IconButton,
  PopoverBody,
  PopoverCloseButton,
  Text,
  PopoverContent,
  Popover,
  useDisclosure,
  PopoverTrigger,
} from '@chakra-ui/react';
import { IReplyComment, IUserContext } from '../../../interfaces';
import UserAvatar from '../../Shared/UserAvatar';
import { BsThreeDots, BsTrash } from 'react-icons/bs';
import { useContext } from 'react';
import { UserContext } from '../../../context/user';

interface IReplyCommentProps {
  replyComment: IReplyComment;
  deleteReplyComment: (replyCommentId: number, ownerId: number) => void;
  ownerId: number;
}

const ReplyComment = ({
  replyComment,
  deleteReplyComment,
  ownerId,
}: IReplyCommentProps) => {
  const { onOpen, onClose, isOpen } = useDisclosure();
  const { user } = useContext(UserContext) as IUserContext;

  const handleDeleteReplyComment = () => {
    deleteReplyComment(replyComment.id, ownerId);
    onClose();
  };

  return (
    <Flex align="center" my="1rem">
      <UserAvatar
        width="35px"
        height="35px"
        fullName={replyComment.fullName}
        avatarUrl={replyComment.avatarUrl}
      />
      <Box>
        <Box
          pos="relative"
          maxW="250px"
          minW="250px"
          ml="0.5rem"
          bg="primary.dark"
          borderRadius={12}
          px="0.5rem"
          py="0.7rem"
        >
          <Text fontSize="0.85rem" fontWeight="bold">
            {replyComment.fullName}
          </Text>
          <Text fontSize="0.9rem">{replyComment.text}</Text>
        </Box>
      </Box>
      <Box
        mx="0.5rem"
        pointerEvents={
          user.id === replyComment.userId || user.id === ownerId ? 'unset' : 'none'
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
                onClick={handleDeleteReplyComment}
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

export default ReplyComment;
