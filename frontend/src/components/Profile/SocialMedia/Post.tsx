import { Box, Divider, Fade, Flex, Image, Text, useOutsideClick } from '@chakra-ui/react';
import { IPost, IUserContext } from '../../../interfaces';
import UserAvatar from '../../Shared/UserAvatar';
import { BsThreeDots, BsTrash } from 'react-icons/bs';
import { useContext, useRef, useState } from 'react';
import { UserContext } from '../../../context/user';

interface IPostProps {
  post: IPost;
  ownerProfileId: number;
  removePost: (postId: number) => void;
}

const Post = ({ post, ownerProfileId, removePost }: IPostProps) => {
  const ref = useRef<HTMLDivElement>(null);
  const { user } = useContext(UserContext) as IUserContext;
  const [optionsOpen, setOptionsOpen] = useState(false);

  useOutsideClick({
    ref,
    handler: () => setOptionsOpen(false),
  });

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
              ref={ref}
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
    </Box>
  );
};

export default Post;
