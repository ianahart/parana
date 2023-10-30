import {
  AbsoluteCenter,
  Box,
  Button,
  Divider,
  Flex,
  Text,
  Modal,
  ModalOverlay,
  ModalContent,
  ModalHeader,
  ModalFooter,
  ModalBody,
  ModalCloseButton,
  useDisclosure,
  Textarea,
  Input,
  Image,
  Tooltip,
} from '@chakra-ui/react';
import { useContext, useEffect, useRef, useState } from 'react';
import { UserContext } from '../../../context/user';
import { IUserContext } from '../../../interfaces';
import BasicSpinner from '../../Shared/BasicSpinner';
import UserAvatar from '../../Shared/UserAvatar';
import { BsEmojiSmile, BsImages } from 'react-icons/bs';
import { AiOutlineClose, AiOutlineEdit, AiOutlineGif } from 'react-icons/ai';
import EmojiPicker, { Theme } from 'emoji-picker-react';
import { Grid } from '@giphy/react-components';
import { GiphyFetch } from '@giphy/js-fetch-api';
import { Client } from '../../../util/client';

interface IWritePostProps {
  postId?: number;
  method: string;
  ownerFirstName: string;
  ownerId: number;
  ownerProfileId: number;
  createPost?: (
    ownerId: number,
    authorId: number,
    postText: string,
    file?: File | null,
    gif?: string
  ) => void;
  updatePost?: (postId: number, postText: string, file: File | null, gif: string) => void;
  isLoading: boolean;
  postError: string;
}

const WritePost = ({
  postId = undefined,
  ownerFirstName,
  ownerId,
  ownerProfileId,
  createPost = undefined,
  updatePost = undefined,
  isLoading,
  postError,
  method,
}: IWritePostProps) => {
  const { user } = useContext(UserContext) as IUserContext;
  const shouldRun = useRef(true);
  const { isOpen, onOpen, onClose } = useDisclosure();
  const [postText, setPostText] = useState('');
  const [file, setFile] = useState<File | null>(null);
  const [gifURL, setGifURL] = useState('');
  const [dataURL, setDataURL] = useState('');
  const [emojiPickerOpen, setEmojiPickerOpen] = useState(false);
  const [error, setError] = useState('');
  const [isGifOpen, setIsGifOpen] = useState(false);
  const MAX_BYTES = 3000000;

  const getPost = (postId: number) => {
    Client.getPost(postId)
      .then((res) => {
        const { fileUrl, gif, text } = res.data.data;
        setDataURL(fileUrl == null ? '' : fileUrl);
        setGifURL(gif);
        setPostText(text);
      })
      .catch((err) => {
        throw new Error(err);
      });
  };

  useEffect(() => {
    if (shouldRun.current && postId !== undefined) {
      getPost(postId);
    }
  }, [shouldRun.current, postId]);

  const gf = new GiphyFetch(import.meta.env.VITE_GIPHY_KEY);
  const fetchGifs = (offset: number) => gf.trending({ offset, limit: 10 });

  const handleOnGifClick = (gif: any) => {
    if (file !== null) {
      setError('You cannot combine a GIF and a photo/video');
      return;
    }
    setGifURL(gif.images.fixed_height_small.webp);
    setIsGifOpen(false);
  };

  const handleOnClose = () => {
    setEmojiPickerOpen(false);
    onClose();
    setFile(null);
    setDataURL('');
    setPostText('');
    setGifURL('');
  };

  const handlePostAction = () => {
    setError('');
    if (postText.trim().length === 0 || postText.length > 600) {
      setError('Post must be between 1 and 600 characters');
      return;
    }

    if (createPost !== undefined && method === 'post') {
      createPost(ownerId, user.id, postText, file, gifURL);

      if (postError == undefined || postError.length > 0) {
        return;
      }
    } else {
      if (postId !== undefined && updatePost !== undefined) {
        updatePost(postId, postText, file, gifURL);
      }
    }
    handleOnClose();
  };

  const statusText =
    user.profileId === ownerProfileId
      ? `What's on your mind, ${user.firstName}?`
      : `Write something to ${ownerFirstName}...`;

  const handleOnTextareaChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setPostText(e.target.value);
  };

  const validateFile = (file: File) => {
    if (file.size > MAX_BYTES) {
      setError('The maximum file size is 3MB');
      return false;
    }
    return true;
  };

  const readFile = (file: File) => {
    const fileReader = new FileReader();
    fileReader.onload = (e) => {
      setDataURL(e?.target?.result as string);
    };
    fileReader.readAsDataURL(file);
  };

  const handleOnInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setError('');
    if (!e.target.files) return;

    const file = e.target.files[0];
    if (!validateFile(file)) return;

    if (gifURL.length) {
      setError('You cannot combine a photo/video with a GIF');
      return;
    }

    setFile(file);
    readFile(file);
  };

  const removeFilePreview = () => {
    setFile(null);
    setDataURL('');
  };

  const handleOnEmojiClick = ({ emoji }: { emoji: string }) => {
    setPostText((prevState) => `${prevState} ${emoji}`);
    setEmojiPickerOpen(false);
  };

  return (
    <Box
      p={method === 'update' ? '0' : '1rem'}
      boxShadow="sm"
      borderRadius={8}
      border={method === 'update' ? 'none' : '1px solid'}
      borderColor="border.primary"
    >
      {method === 'post' && (
        <>
          <Flex align="center">
            <UserAvatar
              height="45px"
              width="45px"
              avatarUrl={user.avatarUrl}
              fullName={user.fullName}
            />
            <Box
              onClick={onOpen}
              cursor="pointer"
              flexGrow={2}
              p="0.5rem"
              borderRadius={20}
              ml="0.5rem"
              border="1px solid"
              borderColor="border.primary"
            >
              {statusText}
            </Box>
          </Flex>
          <Box position="relative" padding="10">
            <Divider borderColor="border.primary" />
            <AbsoluteCenter bg="primary.dark" px="4">
              <Flex onClick={onOpen} cursor="pointer" align="center">
                <Box color="primary.light" mr="0.25rem">
                  <BsImages />
                </Box>
                <Text fontSize="0.9rem" fontWeight="bold">
                  Photo/video
                </Text>
              </Flex>
            </AbsoluteCenter>
          </Box>
        </>
      )}
      {method === 'update' && (
        <Flex
          onClick={onOpen}
          _hover={{ bg: 'black.tertiary' }}
          borderRadius={8}
          p="0.25rem"
          align="center"
        >
          <Box mr="0.25rem">
            <AiOutlineEdit />
          </Box>
          <Text>Edit</Text>
        </Flex>
      )}
      <Modal isOpen={isOpen} onClose={handleOnClose}>
        <ModalOverlay />
        <ModalContent color="text.secondary" bg="black.tertiary">
          <ModalHeader>{method === 'post' ? 'Create a post' : 'Update post'}</ModalHeader>
          <Divider borderColor="border.primary" />
          <ModalCloseButton />
          <ModalBody>
            <Flex>
              <UserAvatar
                height="45px"
                width="45px"
                avatarUrl={user.avatarUrl}
                fullName={user.fullName}
              />
              <Text ml="0.5rem" fontWeight="bold" fontSize="0.9rem">
                {user.fullName}
              </Text>
            </Flex>
            <Box my="1rem">
              <Textarea
                fontSize="0.85rem"
                value={postText}
                onChange={handleOnTextareaChange}
                placeholder={statusText}
                border="none"
                resize="none"
              />
            </Box>
            {error.length > 0 && (
              <Flex justify="center" my="0.5rem">
                <Text fontSize="0.85rem" color="error.primary">
                  {error}
                </Text>
              </Flex>
            )}
            {postError !== undefined && postError.length > 0 && (
              <Flex justify="center" my="0.5rem">
                <Text fontSize="0.85rem" color="error.primary">
                  {postError}
                </Text>
              </Flex>
            )}
            {dataURL.length <= 0 && gifURL.length > 0 && (
              <Flex my="1rem" justify="center">
                <Box position="relative">
                  <Image src={gifURL} borderRadius={8} />
                  <Box pos="absolute" borderRadius={8} top="0" right="5px">
                    <Tooltip label="remove">
                      <Flex
                        onClick={() => setGifURL('')}
                        flexDir="column"
                        align="center"
                        justify="center"
                        borderRadius="50%"
                        bg="black.tertiary"
                        height="25px"
                        width="25px"
                      >
                        <AiOutlineClose />
                      </Flex>
                    </Tooltip>
                  </Box>
                </Box>
              </Flex>
            )}

            {dataURL.length > 0 && gifURL.length <= 0 && (
              <Flex align="center" flexDir="column" my="1rem">
                <Text
                  fontWeight="bold"
                  color="text.primary"
                  mb="0.25rem"
                  fontSize="0.8rem"
                >
                  {file?.name}
                </Text>
                <Flex pos="relative" justify="center">
                  {file && file.type.includes('video') ? (
                    <video width={180} height={120} controls>
                      <source src={dataURL} type={file?.type}></source>
                    </video>
                  ) : (
                    <Image
                      borderRadius={8}
                      width="180px"
                      height="120px"
                      src={dataURL}
                      alt="image preview"
                    />
                  )}
                  <Box pos="absolute" borderRadius={8} top="0" right="5px">
                    <Tooltip label="remove">
                      <Flex
                        onClick={removeFilePreview}
                        flexDir="column"
                        align="center"
                        justify="center"
                        borderRadius="50%"
                        bg="black.tertiary"
                        height="25px"
                        width="25px"
                      >
                        <AiOutlineClose />
                      </Flex>
                    </Tooltip>
                  </Box>
                </Flex>
              </Flex>
            )}
            <Box
              my="1rem"
              p="1rem"
              borderRadius={8}
              border="1px solid"
              borderColor="border.primary"
            >
              <Flex justify="space-between">
                <Text fontSize="0.85rem" fontWeight="bold">
                  Add to your post
                </Text>
                <Flex align="center">
                  <Modal isOpen={isGifOpen} onClose={() => setIsGifOpen(false)}>
                    <ModalContent overflowY="auto" height="450px" bg="black.tertiary">
                      <ModalCloseButton color="text.secondary" />
                      <ModalBody>
                        <Box className="overflow-scroll" height="400px" mr="0.5rem">
                          <Grid
                            width={300}
                            noLink
                            columns={3}
                            gutter={6}
                            onGifClick={handleOnGifClick}
                            fetchGifs={fetchGifs}
                          />
                        </Box>
                      </ModalBody>
                    </ModalContent>
                  </Modal>
                  <Box onClick={() => setIsGifOpen(true)} mr="0.25rem">
                    <AiOutlineGif />
                  </Box>
                  <Box onClick={() => setEmojiPickerOpen(true)} mr="0.5rem">
                    <BsEmojiSmile />
                    {emojiPickerOpen && (
                      <EmojiPicker theme={Theme.DARK} onEmojiClick={handleOnEmojiClick} />
                    )}
                  </Box>
                  <Box cursor="pointer" position="relative">
                    <Input
                      onChange={handleOnInputChange}
                      accept="image/*,video/*"
                      cursor="pointer"
                      position="absolute"
                      opacity="0"
                      zIndex={3}
                      type="file"
                    />
                    <BsImages />
                  </Box>
                </Flex>
              </Flex>
            </Box>
          </ModalBody>
          <ModalFooter display="flex" justifyContent="center">
            {isLoading && (
              <Flex justify="center" my="1rem">
                <BasicSpinner
                  color="text.secondary"
                  message={method === 'update' ? 'Saving post...' : 'Creating post...'}
                />
              </Flex>
            )}
            {!isLoading && (
              <Button
                onClick={handlePostAction}
                width="100%"
                colorScheme="blackAlpha"
                mr={3}
              >
                {method === 'post' ? 'Post' : 'Save'}
              </Button>
            )}
          </ModalFooter>
        </ModalContent>
      </Modal>
    </Box>
  );
};

export default WritePost;
