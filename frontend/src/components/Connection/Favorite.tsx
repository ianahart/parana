import { ButtonGroup, Button, Box } from '@chakra-ui/react';
import { IConnectionFavorite } from '../../interfaces';
import { ImProfile } from 'react-icons/im';
import { useNavigate } from 'react-router-dom';
import { BsFillHeartbreakFill } from 'react-icons/bs';
import ConnectionContainer from './ConnectionContainer';

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
    <ConnectionContainer data={favorite}>
      <ButtonGroup display="flex" justifyContent="space-between" p="0.5rem">
        <Button size="sm" onClick={() => removeFavorite(favorite.id)} colorScheme="gray">
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
    </ConnectionContainer>
  );
};

export default Favorite;
