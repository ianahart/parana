import { Grid, GridItem } from '@chakra-ui/react';
import { IPost } from '../../../interfaces';
import Post from './Post';

interface IPostProps {
  posts: IPost[];
  postView: string;
  ownerProfileId: number;
  removePost: (postId: number) => void;
}

const Posts = ({ posts, postView, ownerProfileId, removePost }: IPostProps) => {
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
            <Post removePost={removePost} post={post} ownerProfileId={ownerProfileId} />
          </GridItem>
        );
      })}
    </Grid>
  );
};

export default Posts;
