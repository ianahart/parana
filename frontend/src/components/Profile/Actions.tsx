import { Box, Tooltip } from '@chakra-ui/react';
import { BsShare } from 'react-icons/bs';
import FavoriteButton from '../Explorer/FavoriteButton';

interface IActionsProps {
  teacherId: number;
}

const Actions = ({ teacherId }: IActionsProps) => {
  return (
    <Box position="relative">
      <FavoriteButton requestType="single" teacherId={teacherId} />
      <Tooltip label="Share">
        <Box cursor="pointer" my="1.5rem">
          <BsShare />
        </Box>
      </Tooltip>
    </Box>
  );
};

export default Actions;
