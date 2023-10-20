import { Box } from '@chakra-ui/react';
import { AiFillHeart } from 'react-icons/ai';

const FavoriteButton = () => {
  return (
    <Box pos="absolute" top="5" right="30px">
      <Box
        _hover={{ transform: 'scale(1.2)' }}
        transition="all 0.25s ease-in-out"
        position="relative"
        color="#fff"
        fontSize="25px"
      >
        <AiFillHeart />
        <Box
          transition="all 0.25s ease-in-out"
          _hover={{ transform: 'scale(1.1)' }}
          pos="absolute"
          top="2px"
          right="2.5px"
          color="rgba(0, 0, 0, 0.75)"
          fontSize="20px"
        >
          <AiFillHeart />
        </Box>
      </Box>
    </Box>
  );
};

export default FavoriteButton;
