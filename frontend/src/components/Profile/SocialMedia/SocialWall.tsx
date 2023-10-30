import { Box } from '@chakra-ui/react';
import { useState } from 'react';
import { Client } from '../../../util/client';
import WritePost from './WritePost';

interface ISocialWallProps {
  ownerId: number;
  ownerFirstName: string;
  ownerProfileId: number;
}

const SocialWall = ({ ownerId, ownerFirstName, ownerProfileId }: ISocialWallProps) => {
  const [createPostLoading, setCreatePostLoading] = useState(false);
  const [createPostError, setCreatePostError] = useState('');

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
      .then((res) => {
        console.log(res);
        setCreatePostLoading(false);
      })
      .catch((err) => {
        console.log(err);
        setCreatePostError(err.response.data.message);
        setCreatePostLoading(false);
        throw new Error(err);
      });
  };

  return (
    <Box>
      <WritePost
        ownerId={ownerId}
        ownerFirstName={ownerFirstName}
        ownerProfileId={ownerProfileId}
        createPost={createPost}
        createPostLoading={createPostLoading}
                createPostError={createPostError}
      />
    </Box>
  );
};

export default SocialWall;
