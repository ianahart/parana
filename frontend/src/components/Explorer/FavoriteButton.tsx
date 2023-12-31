import { Box, Tooltip } from '@chakra-ui/react';
import { useContext, useEffect, useRef, useState } from 'react';
import { AiFillHeart } from 'react-icons/ai';
import { UserContext } from '../../context/user';
import { IFavorite, IUserContext } from '../../interfaces';
import { Client } from '../../util/client';
import { favoriteState } from '../../state/initialState';

interface IFavoriteButtonProps {
  teacherId: number;
  requestType: string;
  existingFavorite?: IFavorite;
}

const FavoriteButton = ({
  teacherId,
  requestType,
  existingFavorite = favoriteState,
}: IFavoriteButtonProps) => {
  const shouldRun = useRef(true);
  const { user } = useContext(UserContext) as IUserContext;
  const [favorite, setFavorite] = useState(favoriteState);

  const getFavorite = () => {
    Client.getFavorite(user.id, teacherId)
      .then((res) => {
        const { data } = res.data;
        if (data !== null) {
          setFavorite(data);
        }
      })
      .catch((err) => {
        throw new Error(err);
      });
  };

  useEffect(() => {
    if (shouldRun.current && requestType === 'single') {
      shouldRun.current = false;
      getFavorite();
    }
    if (shouldRun.current && requestType === 'multiple') {
      shouldRun.current = false;
      setFavorite({ ...existingFavorite });
    }
  }, [shouldRun.current]);

  const addFavorite = (userId: number, teacherId: number) => {
    Client.addTeacherFavorite(userId, teacherId)
      .then((res) => {
        const { data } = res.data;
        setFavorite((prevState) => ({
          ...prevState,
          ...data,
        }));
      })
      .catch((err) => {
        throw new Error(err);
      });
  };

  const removeFavorite = (favoriteId: number) => {
    Client.removeTeacherFavorite(favoriteId)
      .then(() => {
        setFavorite((prevState) => ({
          ...prevState,
          isFavorited: false,
        }));
      })
      .catch((err) => {
        throw new Error(err);
      });
  };

  const determineFavoriteAction = (
    e: React.MouseEvent<HTMLDivElement>,
    userId: number,
    teacherId: number
  ) => {
    e.stopPropagation();
    favorite.isFavorited ? removeFavorite(favorite.id) : addFavorite(userId, teacherId);
  };

  return (
    <Box pos="absolute" top="5" right="30px">
      <Tooltip label={favorite.isFavorited ? 'Unfavorite' : 'Add to favorites'}>
        <Box
          cursor="pointer"
          onClick={(e) => determineFavoriteAction(e, user.id, teacherId)}
          _hover={{ transform: 'scale(1.2)' }}
          transition="all 0.25s ease-in-out"
          zIndex={5}
          position="relative"
          color="#fff"
          fontSize="25px"
        >
          <AiFillHeart />
          <Box
            transition="all 0.25s ease-in-out"
            _hover={{ transform: 'scale(1.1)' }}
            pos="absolute"
            zIndex={10}
            top="2px"
            right="2.5px"
            color={favorite.isFavorited ? 'red' : 'rgba(0, 0, 0, 0.75)'}
            fontSize="20px"
          >
            <AiFillHeart />
          </Box>
        </Box>
      </Tooltip>
    </Box>
  );
};

export default FavoriteButton;
