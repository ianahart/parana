import { Box, Button, Flex } from '@chakra-ui/react';
import { useContext, useEffect, useRef, useState } from 'react';
import { UserContext } from '../context/user';
import { IPost, IUserContext } from '../interfaces';
import { postPaginationState } from '../state/initialState';
import { Client } from '../util/client';
import FeedPost from '../components/Profile/SocialMedia/FeedPost';

const FeedRoute = () => {
  const shouldRun = useRef(true);
  const { user } = useContext(UserContext) as IUserContext;
  const [pagination, setPagination] = useState(postPaginationState);
  const [posts, setPosts] = useState<IPost[]>([]);

  const getFeed = (paginate: boolean) => {
    const pageNum = paginate ? pagination.page : -1;

    Client.getFeedPosts(user.id, pageNum, pagination.pageSize, pagination.direction)
      .then((res) => {
        const { page, pageSize, posts, totalElements, totalPages, direction } =
          res.data.data;
        setPagination((prevState) => ({
          ...prevState,
          page,
          pageSize,
          totalElements,
          totalPages,
          direction,
        }));
        setPosts((prevState) => [...prevState, ...posts]);
      })
      .catch((err) => {
        throw new Error(err);
      });
  };

  useEffect(() => {
    if (shouldRun.current && user.id !== 0) {
      getFeed(false);
    }
  }, [shouldRun.current, user.id]);

  const handleLikePost = (userId: number, postId: number, action: string) => {
    action === 'like' ? likePost(userId, postId) : unLikePost(userId, postId);
  };

  const likePost = (userId: number, postId: number) => {
    Client.likePost(userId, postId)
      .then((res) => {
        const { currentUserHasLikedPost } = res.data.data;
        updateLikedPost(postId, currentUserHasLikedPost);
      })
      .catch((err) => {
        throw new Error(err);
      });
  };

  const updateLikedPost = (postId: number, isLiked: boolean) => {
    const updatedPosts = posts.map((post) => {
      if (post.id === postId) {
        post.currentUserHasLikedPost = isLiked;
        post.likesCount = isLiked ? post.likesCount + 1 : post.likesCount - 1;
      }
      return post;
    });
    setPosts(updatedPosts);
  };

  const unLikePost = (userId: number, postId: number) => {
    Client.removeLikePost(userId, postId)
      .then(() => {
        updateLikedPost(postId, false);
      })
      .catch((err) => {
        throw new Error(err);
      });
  };

  const updateLatestCommentLike = (postId: number, isLiked: boolean) => {
    const updatedPosts = posts.map((post) => {
      if (post.id === postId) {
        return {
          ...post,
          comment: {
            ...post.comment,
            currentUserHasLikedComment: isLiked,
            likesCount: isLiked
              ? post.comment.likesCount + 1
              : post.comment.likesCount - 1,
          },
        };
      }
      return post;
    });
    setPosts(updatedPosts);
  };

  return (
    <Box
      display="flex"
      flexDirection="column"
      alignItems="center"
      color="text.secondary"
      mx="auto"
      minH="100vh"
    >
      <Flex flexDir="column" width={['95%', '740px', '740px']}>
        {posts.map((post) => {
          return (
            <FeedPost
              handleLikePost={handleLikePost}
              updateLatestCommentLike={updateLatestCommentLike}
              ownerId={post.ownerId}
              post={post}
              key={post.id}
            />
          );
        })}
      </Flex>
      {pagination.page < pagination.totalPages - 1 && (
        <Flex justify="center">
          <Button onClick={() => getFeed(true)} colorScheme="blackAlpha">
            See more posts
          </Button>
        </Flex>
      )}
    </Box>
  );
};

export default FeedRoute;
