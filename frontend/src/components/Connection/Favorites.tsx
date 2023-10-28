import { Box, Flex, Text, Grid, GridItem, Heading } from '@chakra-ui/react';
import { useContext, useEffect, useRef, useState } from 'react';
import { UserContext } from '../../context/user';
import { IConnectionFavorite, IUserContext } from '../../interfaces';
import { Client } from '../../util/client';
import Favorite from './Favorite';
import ViewChanger from './ViewChanger';

const Favorites = () => {
  const initialPaginationState = {
    page: 0,
    direction: 'next',
    totalPages: 0,
    pageSize: 6,
    totalElements: 0,
  };

  const shouldRun = useRef(true);
  const { user } = useContext(UserContext) as IUserContext;
  const [favorites, setFavorites] = useState<IConnectionFavorite[]>([]);
  const [pagination, setPagination] = useState(initialPaginationState);
  const [mobileView, setMobileView] = useState(false);

  const getFavorites = (paginate: boolean) => {
    const pageNum = paginate ? pagination.page : -1;
    Client.getFavorites(user.id, pageNum, pagination.pageSize, pagination.direction)
      .then((res) => {
        const {
          direction,
          favorites: data,
          page,
          pageSize,
          totalElements,
          totalPages,
        } = res.data.data;
        console.log(res.data.data);
        setPagination((prevState) => ({
          ...prevState,
          direction,
          page,
          pageSize,
          totalElements,
          totalPages,
        }));
        setFavorites((prevState) => [...prevState, ...data]);
      })

      .catch((err) => {
        throw new Error(err);
      });
  };

  const resetState = () => {
    setPagination(initialPaginationState);
    setFavorites([]);
  };

  const removeFavorite = (favoriteId: number) => {
    Client.removeTeacherFavorite(favoriteId)
      .then(() => {
        resetState();
        getFavorites(false);
      })
      .catch((err) => {
        throw new Error(err);
      });
  };

  useEffect(() => {
    if (shouldRun.current && user.id !== 0) {
      shouldRun.current = false;
      getFavorites(false);
    }
  }, [shouldRun.current, user.id]);

  return (
    <>
      {' '}
      <Flex color="text.secondary" align="center" justify="space-between">
        <Box as="header">
          <Heading fontSize="1.2rem">Favorites</Heading>
          <Text fontWeight="bold" mt="1.5rem">
            {pagination.totalElements} favorites available
          </Text>
        </Box>
        <ViewChanger mobileView={mobileView} setMobileView={setMobileView} />
      </Flex>
      <Grid
        mt="4rem"
        mb="2rem"
        gridTemplateColumns={
          !mobileView
            ? ['repeat(1,1fr)', 'repeat(2,1fr)', 'repeat(3, 1fr)']
            : 'repeat(1, 1fr)'
        }
      >
        {favorites.map((favorite) => {
          return (
            <GridItem key={favorite.id}>
              <Favorite favorite={favorite} removeFavorite={removeFavorite} />
            </GridItem>
          );
        })}
      </Grid>
    </>
  );
};
export default Favorites;
