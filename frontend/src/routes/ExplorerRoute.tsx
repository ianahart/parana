import { Box, Button, Flex } from '@chakra-ui/react';
import { useEffect, useRef, useState } from 'react';
import { Client } from '../util/client';
import Teachers from '../components/Explorer/Teachers';
import { ITeacher } from '../interfaces';
import BasicSpinner from '../components/Shared/BasicSpinner';
import RateFilter from '../components/Explorer/RateFilter';

const ExplorerRoute = () => {
  const shouldRun = useRef(true);
  const [teachers, setTeachers] = useState<ITeacher[]>([]);
  const [isLoading, setIsLoading] = useState(false);
  const [rate, setRate] = useState(1);
  const [pagination, setPagination] = useState({
    page: 0,
    direction: 'next',
    totalPages: 0,
    pageSize: 6,
    totalElements: 0,
  });
  const ROLE = 'TEACHER';

  const getTeachers = (
    role: string,
    pageNum: number,
    direction: string,
    pageSize: number,
    rate: number
  ) => {
    Client.getTeachers(role, pageNum, direction, pageSize, rate)
      .then((res) => {
        const { users, page, pageSize, totalPages, direction, totalElements } =
          res.data.data;
        setTeachers((prevState) => [...prevState, ...users]);
        setPagination((prevState) => ({
          ...prevState,
          direction,
          totalPages,
          totalElements,
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

  const resetTeachers = () => {
    setTeachers([]);
    setPagination({
      page: 0,
      direction: 'next',
      totalPages: 0,
      pageSize: 6,
      totalElements: 0,
    });
  };

  const retrieveTeachers = (paginate: boolean) => {
    if (!paginate) {
      resetTeachers();
    }
    const pageNum = paginate ? pagination.page : -1;
    const { direction, pageSize } = pagination;
    setIsLoading(true);
    getTeachers(ROLE, pageNum, direction, pageSize, rate);
  };
  useEffect(() => {
    if (shouldRun.current) {
      shouldRun.current = false;
      retrieveTeachers(false);
    }
  }, [shouldRun.current]);

  useEffect(() => {
    if (rate > 1) {
      retrieveTeachers(false);
    }
  }, [rate]);

  const resetRateFilter = () => {
    resetTeachers();
    const { direction, pageSize } = pagination;
    setIsLoading(true);
    getTeachers(ROLE, -1, direction, pageSize, 1);
  };

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
        <Flex justify="flex-start" align="center" m="1.5rem">
          <RateFilter rate={rate} setRate={setRate} resetRateFilter={resetRateFilter} />
        </Flex>
        <Box fontSize="0.9rem" color="text.secondary" fontWeight="bold" mx="1.5rem">
          {pagination.totalElements} teachers available
        </Box>
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
