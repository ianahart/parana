import { ButtonGroup, Button, Box, Flex, Text } from '@chakra-ui/react';
import { IConnectionFavorite } from '../../interfaces';
import { ImProfile } from 'react-icons/im';
import { useNavigate } from 'react-router-dom';
import ConnectionAvatar from './ConnectionAvatar';
import { BsFillHeartbreakFill } from 'react-icons/bs';

interface IFavoriteProps {
  favorite: IConnectionFavorite;
  removeFavorite: (favoriteId: number) => void;
}

const Favorite = ({ favorite, removeFavorite }: IFavoriteProps) => {
  const navigate = useNavigate();

  const navigateToProfile = () => {
    navigate(`/profiles/${favorite.teacherId}`);
  };

  return (
    <Box
      my="1rem"
      width="200px"
      color="text.secondary"
      borderRadius={8}
      mx="auto"
      bg="blackAlpha.500"
      minH="275px"
    >
      <Flex flexDir="column" justify="space-between" minH="275px">
        <Box>
          <ConnectionAvatar
            width="200px"
            height="160px"
            avatarUrl={favorite.avatarUrl}
            fullName={favorite.fullName}
          />
          <Flex mt="0.5rem" flexDir="column" align="center">
            <Text fontWeight="bold">{favorite.fullName}</Text>
          </Flex>
        </Box>
        <Box>
          <ButtonGroup display="flex" justifyContent="space-between" p="0.5rem">
            <Button
              size="sm"
              onClick={() => removeFavorite(favorite.id)}
              colorScheme="gray"
            >
              <Box color="red">
                <BsFillHeartbreakFill />
              </Box>
              Remove
            </Button>
            <Button onClick={navigateToProfile} size="sm" colorScheme="telegram">
              <Box>
                <ImProfile />
              </Box>
              Profile
            </Button>
          </ButtonGroup>
        </Box>
      </Flex>
    </Box>
  );
};

export default Favorite;
