import { Grid, GridItem } from '@chakra-ui/react';
import { IPost } from '../../../interfaces';
import Post from './Post';

interface IPostProps {
  posts: IPost[];
  postView: string;
  ownerProfileId: number;
  removePost: (postId: number) => void;
  updatePost: (postId: number, postText: string, file: File | null, gif: string) => void;
  ownerId: number;
  ownerFirstName: string;
  handleLikePost: (userId: number, postId: number, action: string) => void;
}

const Posts = ({
  posts,
  postView,
  ownerProfileId,
  removePost,
  ownerId,
  ownerFirstName,
  updatePost,
  handleLikePost,
}: IPostProps) => {
  const gridTemplateColumns =
    postView === 'list' ? ['1fr', '1fr', '1fr'] : ['1fr', '1fr 1fr', '1fr 1fr'];

  return (
    <Grid
      gap={postView === 'grid' ? 5 : 0}
      gridTemplateColumns={gridTemplateColumns}
      my="1.5rem"
    >
      {posts.map((post) => {
        return (
          <GridItem key={post.id}>
            <Post
              updatePost={updatePost}
              ownerId={ownerId}
              ownerFirstName={ownerFirstName}
              removePost={removePost}
              post={post}
              ownerProfileId={ownerProfileId}
              handleLikePost={handleLikePost}
            />
          </GridItem>
        );
      })}
    </Grid>
  );
};

export default Posts;
