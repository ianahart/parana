import { Box, Button, Flex } from '@chakra-ui/react';
import { useEffect, useRef, useState } from 'react';
import { Client } from '../util/client';
import Teachers from '../components/Explorer/Teachers';
import { ITeacher } from '../interfaces';
import BasicSpinner from '../components/Shared/BasicSpinner';

const ExplorerRoute = () => {
  const shouldRun = useRef(true);
  const [teachers, setTeachers] = useState<ITeacher[]>([]);
  const [isLoading, setIsLoading] = useState(false);
  const [pagination, setPagination] = useState({
    page: 0,
    direction: 'next',
    totalPages: 0,
    pageSize: 3,
  });
  const ROLE = 'TEACHER';

  const retrieveTeachers = (paginate: boolean) => {
    const pageNum = paginate ? pagination.page : -1;
    const { direction, pageSize } = pagination;
    setIsLoading(true);
    Client.getTeachers(ROLE, pageNum, direction, pageSize)
      .then((res) => {
        const { users, page, pageSize, totalPages, direction } = res.data.data;
        setTeachers((prevState) => [...prevState, ...users]);
        setPagination((prevState) => ({
          ...prevState,
          direction,
          totalPages,
          pageSize,
          page,
        }));
        setIsLoading(false);
      })
      .catch((err) => {
        setIsLoading(false);
        throw new Error(err.response.data.message);
      });
  };
  useEffect(() => {
    if (shouldRun.current) {
      shouldRun.current = false;
      retrieveTeachers(false);
    }
  }, [shouldRun.current]);

  return (
    <Box color="#fff" minH="100vh">
      <Box className="explorer-container" mx="auto" mt="3rem">
        {isLoading && (
          <Flex justify="center" my="3rem">
            <BasicSpinner
              color="text.secondary"
              message="Loading Teachers. Please Wait."
            />
          </Flex>
        )}
        <Teachers teachers={teachers} />
        {pagination.page < pagination.totalPages - 1 && (
          <Flex my="3rem" justify="center">
            <Button onClick={() => retrieveTeachers(true)} colorScheme="blackAlpha">
              See more teachers
            </Button>
          </Flex>
        )}
      </Box>
    </Box>
  );
};

export default ExplorerRoute;
