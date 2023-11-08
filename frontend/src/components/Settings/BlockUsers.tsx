import { Box, Flex } from '@chakra-ui/react';
import Block from './Block';
import BlockList from './BlockList';

const BlockUsers = () => {
  return (
    <Box>
      <Flex justify="flex-end">
        <BlockList />
      </Flex>
      <Box>
        <Block
          heading="Block Messages"
          text="When you block a particular user from sending messages to you, you will not be able to send messages to them. They will not see you in their messages and you will not see them in your messages."
          blockType="messages"
          block={{ messages: true, comments: false, posts: false }}
        />
        <Block
          heading="Block Posts"
          text="Block a particular user or teacher from writing posts on your profile wall."
          blockType="posts"
          block={{ messages: false, comments: false, posts: true }}
        />
        <Block
          heading="Block Comments"
          text="Block a particular user or teacher from writing comments on posts, on your profile wall."
          blockType="comments"
          block={{ messages: false, comments: true, posts: false }}
        />
      </Box>
    </Box>
  );
};

export default BlockUsers;
