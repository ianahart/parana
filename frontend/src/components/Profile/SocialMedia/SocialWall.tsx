import { Box, Flex, Button } from '@chakra-ui/react';
import { useEffect, useRef, useState } from 'react';
import { Client } from '../../../util/client';
import WritePost from './WritePost';
import { postPaginationState } from '../../../state/initialState';
import { IPost } from '../../../interfaces';
import ManagePosts from './ManagePosts';
import Posts from './Posts';

interface ISocialWallProps {
  ownerId: number;
  ownerFirstName: string;
  ownerProfileId: number;
}

const SocialWall = ({ ownerId, ownerFirstName, ownerProfileId }: ISocialWallProps) => {
  const shouldRun = useRef(true);
  const [pagination, setPagination] = useState(postPaginationState);
  const [posts, setPosts] = useState<IPost[]>([]);
  const [postView, setPostView] = useState('list');

  const [createPostLoading, setCreatePostLoading] = useState(false);
  const [createPostError, setCreatePostError] = useState('');

  const getPosts = (paginate: boolean) => {
    const pageNum = paginate ? pagination.page : -1;
    Client.getPosts(ownerId, pageNum, pagination.pageSize, pagination.direction)
      .then((res) => {
        const { direction, page, pageSize, posts, totalElements, totalPages } =
          res.data.data;
        setPagination((prevState) => ({
          ...prevState,
          direction,
          page,
          pageSize,
          totalElements,
          totalPages,
        }));

        setPosts((prevState) => [...prevState, ...posts]);
      })
      .catch((err) => {
        throw new Error(err.response.data);
      });
  };

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

  useEffect(() => {
    if (shouldRun.current) {
      shouldRun.current = false;
      getPosts(false);
    }
  }, [shouldRun.current]);

  const createPost = (
    ownerId: number,
    authorId: number,
    postText: string,
    file?: File | null,
    gif?: string
  ) => {
    setCreatePostError('');
    setCreatePostLoading(true);
    Client.createPost(ownerId, authorId, postText, file, gif)
      .then(() => {
        setPosts([]);
        setPagination(postPaginationState);
        getPosts(false);
        setCreatePostLoading(false);
      })
      .catch((err) => {
        setCreatePostError(err.response.data.message);
        setCreatePostLoading(false);
        throw new Error(err);
      });
  };

  const removePost = (postId: number) => {
    Client.removePost(postId)
      .then(() => {
        setPosts([]);
        setPagination(postPaginationState);
        getPosts(false);
      })
      .catch((err) => {
        throw new Error(err);
      });
  };

  const updatePost = (
    postId: number,
    postText: string,
    file: File | null,
    gif: string
  ) => {
    Client.updatePost(postId, postText, file, gif)
      .then(() => {
        setPosts([]);
        setPagination(postPaginationState);
        getPosts(false);
      })
      .catch((err) => {
        throw new Error(err);
      });
  };

  return (
    <Box width="100%">
      <WritePost
        ownerId={ownerId}
        ownerFirstName={ownerFirstName}
        ownerProfileId={ownerProfileId}
        createPost={createPost}
        updatePost={updatePost}
        isLoading={createPostLoading}
        postError={createPostError}
        method="post"
      />
      <ManagePosts postView={postView} setPostView={setPostView} />
      <Posts
        ownerId={ownerId}
        ownerFirstName={ownerFirstName}
        removePost={removePost}
        updatePost={updatePost}
        posts={posts}
        postView={postView}
        ownerProfileId={ownerProfileId}
        handleLikePost={handleLikePost}
      />
      {pagination.page < pagination.totalPages - 1 && (
        <Flex justify="center">
          <Button onClick={() => getPosts(true)} colorScheme="blackAlpha">
            See more posts
          </Button>
        </Flex>
      )}
    </Box>
  );
};

export default SocialWall;
