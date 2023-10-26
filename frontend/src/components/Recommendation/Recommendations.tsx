import { Box, Button, Flex, Tooltip, Heading } from '@chakra-ui/react';
import { AiOutlineInfoCircle } from 'react-icons/ai';
import { HiOutlineThumbUp } from 'react-icons/hi';
import WriteRecomendation from './WriteRecommendation';
import { useEffect, useRef, useState } from 'react';
import { Client } from '../../util/client';
import { IRecommendation } from '../../interfaces';
import Recommendation from './Recommendation';

interface IRecommendationsProps {
  teacherId: number;
  teacherName: string;
}

const Recommendations = ({ teacherName, teacherId }: IRecommendationsProps) => {
  const shouldRun = useRef(true);
  const tooltipLabel = 'Recomendations are given by other teachers.';
  const paginationInitialState = {
    page: 0,
    totalPages: 0,
    totalElements: 0,
    direction: 'next',
    pageSize: 2,
  };
  const [recommendations, setRecommendations] = useState<IRecommendation[]>([]);
  const [pagination, setPagination] = useState(paginationInitialState);

  const getRecommendations = (paginate: boolean) => {
    const pageNum = paginate ? pagination.page : -1;
    Client.getRecommendations(
      teacherId,
      pageNum,
      pagination.pageSize,
      pagination.direction
    )
      .then((res) => {
        const { direction, page, pageSize, recommendations, totalElements, totalPages } =
          res.data.data;

        setPagination((prevState) => ({
          ...prevState,
          direction,
          page,
          pageSize,
          totalElements,
          totalPages,
        }));

        paginate
          ? setRecommendations((prevState) => [...prevState, ...recommendations])
          : setRecommendations(recommendations);
      })
      .catch((err) => {
        throw new Error(err.response.data.message);
      });
  };

  useEffect(() => {
    if (shouldRun.current) {
      shouldRun.current = false;
      getRecommendations(false);
    }
  }, [shouldRun.current, getRecommendations]);

  const resetRecommendations = () => {
    setPagination(paginationInitialState);
    setRecommendations([]);
    getRecommendations(false);
  };

  const removeRecommendation = (recommendationId: number) => {
    Client.deleteRecommendation(recommendationId)
      .then(() => {
        resetRecommendations();
        setRecommendations((prevState) =>
          prevState.filter((r) => r.id !== recommendationId)
        );
      })
      .catch((err) => {
        throw new Error(err.response.data.message);
      });
  };

  return (
    <Box mt="3rem">
      <Flex align="center" justify="space-between">
        <Box position="relative">
          <Heading
            display="flex"
            alignItems="center"
            wordBreak="break-word"
            lineHeight="1.6"
            fontSize="1.5rem"
            as="h3"
          >
            Recommendations
            <Tooltip label={tooltipLabel} hasArrow bg="primary.dark">
              <Box ml="0.25rem">
                <AiOutlineInfoCircle />
              </Box>
            </Tooltip>
          </Heading>
        </Box>
        <Flex
          borderRadius={20}
          p="0.5rem"
          bg="primary.light"
          align="center"
          fontSize="0.85rem"
        >
          <Box mr="0.25rem" color="text.secondary" fontSize="1.1rem">
            <HiOutlineThumbUp />
          </Box>
          <Box fontWeight="bold">{pagination.totalElements}</Box>
        </Flex>
      </Flex>
      <WriteRecomendation
        resetRecommendations={resetRecommendations}
        teacherName={teacherName}
        teacherId={teacherId}
      />
      <Box my="2rem">
        {recommendations.map((recommendation) => {
          return (
            <Recommendation
              removeRecommendation={removeRecommendation}
              recommendation={recommendation}
              key={recommendation.id}
            />
          );
        })}
      </Box>
      {pagination.page < pagination.totalPages - 1 && (
        <Flex my="2rem" justify="flex-end">
          <Button
            onClick={() => getRecommendations(true)}
            fontSize="0.9rem"
            _hover={{ background: 'transparent' }}
            color="text.secondary"
            variant="ghost"
          >
            See more
          </Button>
        </Flex>
      )}
    </Box>
  );
};

export default Recommendations;
