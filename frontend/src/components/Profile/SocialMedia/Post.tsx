import { Box, Flex, Image, Text } from '@chakra-ui/react';
import { IPost } from '../../../interfaces';
import UserAvatar from '../../Shared/UserAvatar';
import { BsThreeDots } from 'react-icons/bs';

interface IPostProps {
  post: IPost;
}

const Post = ({ post }: IPostProps) => {
  console.log(post);
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
        <Box>
          <BsThreeDots />
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
