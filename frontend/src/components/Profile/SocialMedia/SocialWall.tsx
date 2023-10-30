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
        Client.removePost(postId).then(() => {
    setPosts([]);
    setPagination(postPaginationState);
    getPosts(false);

        }).catch((err) => {
                throw new Error(err);
            })
  };

  return (
    <Box width="100%">
      <WritePost
        ownerId={ownerId}
        ownerFirstName={ownerFirstName}
        ownerProfileId={ownerProfileId}
        createPost={createPost}
        createPostLoading={createPostLoading}
        createPostError={createPostError}
      />
      <ManagePosts postView={postView} setPostView={setPostView} />
      <Posts
        removePost={removePost}
        posts={posts}
        postView={postView}
        ownerProfileId={ownerProfileId}
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
