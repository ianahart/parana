import { Box, Flex, Text, Tooltip } from '@chakra-ui/react';
import { useContext } from 'react';
import { AiOutlineComment } from 'react-icons/ai';
import { BsHandThumbsUp } from 'react-icons/bs';
import { UserContext } from '../../../context/user';
import { IUserContext } from '../../../interfaces';

interface IActionsProps {
  handleLikePost: (userId: number, postId: number, action: string) => void;
  postId: number;
  currentUserHasLikedPost: boolean;
}

const Actions = ({ handleLikePost, postId, currentUserHasLikedPost }: IActionsProps) => {
  const { user } = useContext(UserContext) as IUserContext;

  const handleOnClick = (action: string) => {
    handleLikePost(user.id, postId, action);
  };

  return (
    <Flex justify="space-around">
      {!currentUserHasLikedPost && (
        <Flex
          align="center"
          cursor="pointer"
          fontSize="0.9rem"
          onClick={() => handleOnClick('like')}
        >
          <Box mr="0.25rem">
            <BsHandThumbsUp />
          </Box>
          <Text>Like</Text>
        </Flex>
      )}
      {currentUserHasLikedPost && (
        <Tooltip label="Unlike">
          <Flex
            color="blue.500"
            align="center"
            cursor="pointer"
            fontSize="0.9rem"
            onClick={() => handleOnClick('unlike')}
          >
            <Box transform={'rotate(-15deg)'} mr="0.25rem">
              <BsHandThumbsUp />
            </Box>
            <Text fontWeight="bold">Like</Text>
          </Flex>
        </Tooltip>
      )}
      <Flex align="center" cursor="pointer" fontSize="0.9rem">
        <Box mr="0.25rem">
          <AiOutlineComment />
        </Box>
        <Text>Comment</Text>
      </Flex>
    </Flex>
  );
};

export default Actions;
