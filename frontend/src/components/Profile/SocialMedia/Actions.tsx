import { Box, Flex, Text, Textarea, Tooltip, ScaleFade } from '@chakra-ui/react';
import { useContext, useEffect, useState } from 'react';
import { AiOutlineComment, AiOutlineSend } from 'react-icons/ai';
import { BsEmojiSmile, BsHandThumbsUp } from 'react-icons/bs';
import { UserContext } from '../../../context/user';
import { IUserContext } from '../../../interfaces';
import UserAvatar from '../../Shared/UserAvatar';
import EmojiPicker, { EmojiClickData, Theme } from 'emoji-picker-react';
import { nanoid } from 'nanoid';
import { AxiosResponse } from 'axios';

import { over } from 'stompjs';
import SockJS from 'sockjs-client';

let stompClient: any = null;

interface IActionsProps {
  ownerId: number;
  handleLikePost: (userId: number, postId: number, action: string) => void;
  postId: number;
  currentUserHasLikedPost: boolean;
  refreshComments: () => void;
  createComment: (
    userId: number,
    postId: number,
    text: string
  ) => Promise<AxiosResponse<any, any>>;
}

interface IError {
  [key: string]: string;
}

const Actions = ({
  ownerId,
  handleLikePost,
  postId,
  currentUserHasLikedPost,
  createComment,
  refreshComments,
}: IActionsProps) => {
  const COMMENT_LIMIT = 300;
  const { user } = useContext(UserContext) as IUserContext;
  const [isCommentBoxShowing, setIsCommentBoxShowing] = useState(false);
  const [isEmojiPickerOpen, setIsEmojiPickerOpen] = useState(false);
  const [text, setText] = useState('');
  const [errors, setErrors] = useState<string[]>([]);

  useEffect(() => {});

  const connect = () => {
    let Sock = new SockJS('http://localhost:8080/ws');
    stompClient = over(Sock);
    stompClient.connect({}, onConnected, onError);
  };

  useEffect(() => {
    if (isCommentBoxShowing) {
      connect();
    } else {
      if (stompClient !== null) {
        stompClient.disconnect();
      }
    }
  }, [isCommentBoxShowing]);

  const sendNotification = (receiverId: number, senderId: number) => {
    if (stompClient) {
      stompClient.send(
        '/api/v1/private-notifications',
        {},
        JSON.stringify({ receiverId, senderId, type: 'comments' })
      );
    }
  };

  const onConnected = () => {
    console.log('connected');
  };
  const onError = () => {};

  const handleOnClick = (e: React.MouseEvent<HTMLDivElement>, action: string) => {
    e.stopPropagation();
    handleLikePost(user.id, postId, action);
  };

  const handleOnEmojiClick = (emoji: EmojiClickData) => {
    setText((prevState) => prevState + ' ' + emoji.emoji);
    setIsEmojiPickerOpen(false);
  };

  const validateComment = (text: string) => {
    return !(text.trim().length === 0 || text.length > COMMENT_LIMIT);
  };

  const applyErrors = <T extends IError>(data: T) => {
    for (const key of Object.keys(data)) {
      setErrors((prevState) => [...prevState, data[key]]);
    }
  };

  const handleCreateComment = (e: React.MouseEvent<HTMLDivElement>) => {
    e.stopPropagation();
    setErrors([]);
    if (!validateComment(text)) {
      setErrors((prevState) => [
        ...prevState,
        'Comment must be between 1 and 300 characters',
      ]);
      return;
    }
    createComment(user.id, postId, text)
      .then(() => {
        setText('');
        refreshComments();
        console.log('RUN');
        sendNotification(ownerId, user.id);
        setIsCommentBoxShowing(false);
      })
      .catch((err) => {
        applyErrors(err.response.data);
        throw new Error(err);
      });
  };

  return (
    <Box>
      <Flex justify="space-around">
        {!currentUserHasLikedPost && (
          <Flex
            align="center"
            cursor="pointer"
            fontSize="0.9rem"
            onClick={(e) => handleOnClick(e, 'like')}
          >
            <Box mr="0.25rem">
              <BsHandThumbsUp />
            </Box>
            <Text>Like</Text>
          </Flex>
        )}
        {currentUserHasLikedPost && (
          <Tooltip label="Unlike">
            <Flex
              color="blue.500"
              align="center"
              cursor="pointer"
              fontSize="0.9rem"
              onClick={(e) => handleOnClick(e, 'unlike')}
            >
              <Box transform={'rotate(-15deg)'} mr="0.25rem">
                <BsHandThumbsUp />
              </Box>
              <Text fontWeight="bold">Like</Text>
            </Flex>
          </Tooltip>
        )}
        <Flex
          onClick={() => setIsCommentBoxShowing((prevState) => !prevState)}
          align="center"
          cursor="pointer"
          fontSize="0.9rem"
        >
          <Box mr="0.25rem">
            <AiOutlineComment />
          </Box>
          <Text fontWeight={isCommentBoxShowing ? 'bold' : 'normal'}>Comment</Text>
        </Flex>
      </Flex>
      <ScaleFade hidden={!isCommentBoxShowing} in={isCommentBoxShowing}>
        <Flex mt="1rem" mb="0.25rem" align="center">
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
            width="100%"
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
              placeholder="Leave a comment..."
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
                <Box cursor="pointer" onClick={(e) => handleCreateComment(e)}>
                  <AiOutlineSend />
                </Box>
              </Tooltip>
            </Flex>
          </Box>
        </Flex>
      </ScaleFade>
    </Box>
  );
};

export default Actions;
