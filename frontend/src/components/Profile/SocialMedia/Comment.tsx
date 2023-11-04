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
  Tooltip,
  Textarea,
  ScaleFade,
} from '@chakra-ui/react';
import { IComment, IReplyComment, IUserContext } from '../../../interfaces';
import UserAvatar from '../../Shared/UserAvatar';
import { BsEmojiSmile, BsHandThumbsUpFill, BsThreeDots, BsTrash } from 'react-icons/bs';
import { useContext, useState } from 'react';
import { UserContext } from '../../../context/user';
import EmojiPicker, { EmojiClickData, Theme } from 'emoji-picker-react';
import { AiOutlineSend } from 'react-icons/ai';
import { nanoid } from 'nanoid';
import { Client } from '../../../util/client';
import { replyCommentPaginationState } from '../../../state/initialState';
import ReplyComment from './ReplyComment';

interface IError {
  [key: string]: string;
}

interface ICommentProps {
  comment: IComment;
  ownerId: number;
  deleteComment: (commentId: number, ownerId: number) => void;
  likeComment: (commentId: number, userId: number) => void;
  unlikeComment: (commentId: number, userId: number) => void;
  updateReplyToComment: (commentId: number, exists: boolean) => void;
}

const Comment = ({
  comment,
  ownerId,
  deleteComment,
  likeComment,
  unlikeComment,
  updateReplyToComment,
}: ICommentProps) => {
  const REPLY_CHAR_LIMIT = 300;
  const { onOpen, onClose, isOpen } = useDisclosure();
  const { user } = useContext(UserContext) as IUserContext;
  const [replyTriggerClicked, setReplyTriggerClicked] = useState(false);
  const [text, setText] = useState('');
  const [errors, setErrors] = useState<string[]>([]);
  const [isEmojiPickerOpen, setIsEmojiPickerOpen] = useState(false);
  const [pagination, setPagination] = useState(replyCommentPaginationState);
  const [replyComments, setReplyComments] = useState<IReplyComment[]>([]);

  const handleDeleteComment = (e: React.MouseEvent<HTMLDivElement>) => {
    e.stopPropagation();
    deleteComment(comment.id, ownerId);
    onClose();
  };

  const deleteReplyComment = (replyCommentId: number, ownerId: number) => {
    Client.removeReplyComment(replyCommentId, ownerId)
      .then(() => {
        setPagination(replyCommentPaginationState);
        setReplyComments([]);
      })
      .catch((err) => {
        throw new Error(err);
      });
  };

  const handleOnEmojiClick = (emoji: EmojiClickData) => {
    setText((prevState) => prevState + ' ' + emoji.emoji);
    setIsEmojiPickerOpen(false);
  };

  const handleLikeComment = (e: React.MouseEvent<HTMLButtonElement>) => {
    e.stopPropagation();
    comment.currentUserHasLikedComment
      ? unlikeComment(comment.id, user.id)
      : likeComment(comment.id, user.id);
  };

  const validateReply = (text: string) => {
    return !(text.trim().length === 0 || text.length > REPLY_CHAR_LIMIT);
  };

  const applyErrors = <T extends IError>(data: T) => {
    for (const key of Object.keys(data)) {
      setErrors((prevState) => [...prevState, data[key]]);
    }
  };

  const createReplyComment = (
    userId: number,
    commentId: number,
    text: string,
    ownerId: number
  ) => {
    Client.createReplyComment(userId, commentId, text, ownerId)
      .then(() => {
        setReplyTriggerClicked(false);
        setText('');
        setPagination(replyCommentPaginationState);
        setReplyComments([]);
      })
      .catch((err) => {
        applyErrors(err.response.data);
        throw new Error(err);
      });
  };

  const handleReplyToComment = (e: React.MouseEvent<HTMLDivElement>) => {
    e.stopPropagation();
    setErrors([]);
    if (!validateReply(text)) {
      setErrors((prevState) => [
        ...prevState,
        'Comment must be between 1 and 300 characters',
      ]);
      return;
    }
    createReplyComment(user.id, comment.id, text, ownerId);
    updateReplyToComment(comment.id, true);
  };

  const getReplyComments = () => {
    Client.getReplyComments(
      comment.id,
      pagination.page,
      pagination.pageSize,
      pagination.direction
    )
      .then((res) => {
        const { direction, page, pageSize, replyComments, totalElements, totalPages } =
          res.data.data;
        setPagination((prevState) => ({
          ...prevState,
          direction,
          page,
          pageSize,
          totalElements,
          totalPages,
        }));
        setReplyComments((prevState) => [...prevState, ...replyComments]);
      })
      .catch((err) => {
        throw new Error(err);
      });
  };

  return (
    <>
      <Flex my={replyTriggerClicked ? '0' : '1rem'} align="center">
        <UserAvatar
          width="35px"
          height="35px"
          fullName={comment.fullName}
          avatarUrl={comment.avatarUrl}
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
              {comment.fullName}
            </Text>
            <Text fontSize="0.9rem">{comment.text}</Text>
            {comment.likesCount > 0 && (
              <Box pos="absolute" right="0" bottom="1px">
                <Flex align="center">
                  <Text fontSize="0.8rem" mr="0.25rem">
                    {comment.likesCount}
                  </Text>
                  <Flex
                    height="20px"
                    width="20px"
                    borderRadius={50}
                    bg="blue.500"
                    flexDir="column"
                    justify="center"
                    align="center"
                  >
                    <BsHandThumbsUpFill />
                  </Flex>
                </Flex>
              </Box>
            )}
          </Box>
          <Flex align="center" justify="space-around">
            <Button
              color={comment.currentUserHasLikedComment ? 'blue.500' : 'text.secondary'}
              onClick={(e) => handleLikeComment(e)}
              fontSize="0.8rem"
              colorScheme="ghost"
              _hover={{ bg: 'transparent' }}
            >
              Like
            </Button>
            <Button
              onClick={() => setReplyTriggerClicked((prevState) => !prevState)}
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
      <>
        <ScaleFade hidden={!replyTriggerClicked} in={replyTriggerClicked}>
          <Flex mb="0.25rem" justify="center" align="center">
            <UserAvatar
              width="45px"
              height="45px"
              avatarUrl={user.avatarUrl}
              fullName={user.fullName}
            />
            <Box
              ml="0.5rem"
              p="0.5rem"
              borderRadius={8}
              border="1px solid"
              borderColor="border.primary"
              width="70%"
            >
              {errors.length > 0 && (
                <Box my="0.5rem" mx="auto">
                  {errors.map((error) => {
                    return (
                      <Text
                        key={nanoid()}
                        textAlign="center"
                        fontSize="0.8rem"
                        color="error.primary"
                      >
                        {error}
                      </Text>
                    );
                  })}
                </Box>
              )}
              <Textarea
                onChange={(e) => setText(e.target.value)}
                value={text}
                resize="none"
                fontSize="0.9rem"
                _placeholder={{ fontSize: '0.9rem' }}
                placeholder={`Replying to ${comment.fullName}...`}
                _hover={{ borderColor: 'transparent' }}
                borderColor="transparent"
              />
              <Flex align="center" justify="flex-end">
                {isEmojiPickerOpen ? (
                  <EmojiPicker onEmojiClick={handleOnEmojiClick} theme={Theme.DARK} />
                ) : (
                  <Tooltip label="Emoji" placement="end-start">
                    <Box
                      cursor="pointer"
                      mx="0.5rem"
                      onClick={() => setIsEmojiPickerOpen(true)}
                    >
                      <BsEmojiSmile />
                    </Box>
                  </Tooltip>
                )}
                <Tooltip label="Comment" placement="end-start">
                  <Box cursor="pointer" onClick={(e) => handleReplyToComment(e)}>
                    <AiOutlineSend />
                  </Box>
                </Tooltip>
              </Flex>
            </Box>
          </Flex>
        </ScaleFade>
        {comment.replyCommentsExist && pagination.page !== pagination.totalPages && (
          <Box ml={['0', '5rem', '5rem']}>
            <Box onClick={getReplyComments} cursor="pointer" _hover={{ opacity: 0.8 }}>
              <Text fontWeight="bold" fontSize="0.9rem" role="button">
                View reply comments
              </Text>
            </Box>
          </Box>
        )}
        <Box ml={['0', '5rem', '5rem']}>
          {replyComments.map((replyComment) => {
            return (
              <ReplyComment
                deleteReplyComment={deleteReplyComment}
                key={replyComment.id}
                replyComment={replyComment}
                ownerId={ownerId}
              />
            );
          })}
        </Box>
      </>
    </>
  );
};

export default Comment;
