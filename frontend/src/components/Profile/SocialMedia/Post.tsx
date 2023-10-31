import { Box, Divider, Flex, Image, Text } from '@chakra-ui/react';
import { IPost, IUserContext } from '../../../interfaces';
import UserAvatar from '../../Shared/UserAvatar';
import { BsHandThumbsUpFill, BsThreeDots, BsTrash } from 'react-icons/bs';
import { useContext, useState } from 'react';
import { UserContext } from '../../../context/user';
import WritePost from './WritePost';
import { AiOutlineClose } from 'react-icons/ai';
import Actions from './Actions';

interface IPostProps {
  post: IPost;
  ownerProfileId: number;
  removePost: (postId: number) => void;
  ownerId: number;
  ownerFirstName: string;
  updatePost: (postId: number, postText: string, file: File | null, gif: string) => void;
  handleLikePost: (userId: number, postId: number, action: string) => void;
}

const Post = ({
  post,
  ownerProfileId,
  removePost,
  ownerId,
  ownerFirstName,
  updatePost,
  handleLikePost,
}: IPostProps) => {
  const { user } = useContext(UserContext) as IUserContext;
  const [optionsOpen, setOptionsOpen] = useState(false);

  const handleOptions = () => {
    if (user.id === ownerProfileId || user.id === post.authorId) {
      setOptionsOpen(true);
    }
  };

  const handleRemovePost = (e: React.MouseEvent<HTMLDivElement>) => {
    e.stopPropagation();
    setOptionsOpen(false);
    removePost(post.id);
  };

  return (
    <Box
      my="1rem"
      borderRadius={8}
      border="1px solid"
      borderColor="border.primary"
      p="1rem"
    >
      <Flex justify="space-between" align="center">
        <Flex>
          <UserAvatar
            height="45px"
            width="45px"
            avatarUrl={post.authorAvatarUrl}
            fullName={post.authorFullName}
          />
          <Box ml="0.5rem">
            <Text fontWeight="bold"> {post.authorFullName}</Text>
            <Text fontSize="0.75rem" color="text.primary">
              {post.readableDate}
            </Text>
          </Box>
        </Flex>
        <Box cursor="pointer" position="relative" onClick={handleOptions}>
          <BsThreeDots />
          {optionsOpen && (
            <Box
              p="0.5rem"
              className="fade-in"
              zIndex={5}
              borderRadius={8}
              top="15px"
              right="0"
              pos="absolute"
              bg="blackAlpha.900"
              width="180px"
              minH="240px"
            >
              <Flex
                m="0.25rem"
                justify="flex-end"
                onClick={(e) => {
                  e.stopPropagation();
                  setOptionsOpen(false);
                }}
              >
                <Box>
                  <AiOutlineClose />
                </Box>
              </Flex>
              <Flex
                onClick={handleRemovePost}
                _hover={{ bg: 'black.tertiary' }}
                borderRadius={8}
                p="0.25rem"
                align="center"
              >
                <Box mr="0.25rem">
                  <BsTrash />
                </Box>
                <Box>
                  <Text fontSize="0.85rem">Delete</Text>
                </Box>
              </Flex>
              <Divider my="0.25rem" borderColor="border.primary" />
              {user.id === post.authorId && (
                <WritePost
                  postId={post.id}
                  method="update"
                  ownerId={ownerId}
                  ownerFirstName={ownerFirstName}
                  ownerProfileId={ownerProfileId}
                  updatePost={updatePost}
                  isLoading={false}
                  postError=""
                />
              )}
            </Box>
          )}
        </Box>
      </Flex>
      <Box>
        <Text lineHeight="1.8" m="0.5rem">
          {post.text}
        </Text>
        {post.fileUrl && !post.fileUrl.endsWith('.mp4') && (
          <Flex>
            <Image borderRadius={8} width="100%" src={post.fileUrl} alt="post image" />
          </Flex>
        )}
        {post.fileUrl && post.fileUrl.endsWith('.mp4') && (
          <video style={{ borderRadius: '8px' }} controls width="100%">
            <source src={post.fileUrl}></source>
          </video>
        )}
        {post.gif && (
          <Flex justify="center">
            <Image borderRadius={8} src={post.gif} alt="a gif" />
          </Flex>
        )}
      </Box>
      <Flex my="1rem" align="center">
        <Flex
          mr="0.25rem"
          height="20px"
          width="20px"
          flexDir="column"
          align="center"
          justify="center"
          borderRadius={50}
          bg="blue.500"
          fontSize="0.9rem"
        >
          <BsHandThumbsUpFill />
        </Flex>
        <Text fontSize="0.9rem" color="text.secondary">
          {post.likesCount > 0 ? post.likesCount : ''}
        </Text>
      </Flex>
      <Divider my="1rem" borderColor="border.primary" />
      <Actions
        currentUserHasLikedPost={post.currentUserHasLikedPost}
        handleLikePost={handleLikePost}
        postId={post.id}
      />
    </Box>
  );
};

export default Post;
