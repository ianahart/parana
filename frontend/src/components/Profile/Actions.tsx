import { Box, Tooltip } from '@chakra-ui/react';
import { AiOutlineHeart } from 'react-icons/ai';
import { BsShare } from 'react-icons/bs';

const Actions = () => {
  return (
    <Box>
      <Tooltip label="Add to favorites">
        <Box cursor="pointer">
          <AiOutlineHeart />
        </Box>
      </Tooltip>
      <Tooltip label="Share">
        <Box cursor="pointer" my="1.5rem">
          <BsShare />
        </Box>
      </Tooltip>
    </Box>
  );
};

export default Actions;
