import { Box, Flex, Heading } from '@chakra-ui/react';
import { steps } from '../../state/initialState';
import SingleStep from './SingleStep';

const Steps = () => {
  return (
    <Box p="0.5rem">
      <Flex color="text.secondary">
        <Heading width={['95%', '95%', '500px']}>Learning has never been so easy</Heading>
      </Flex>
      {steps.map((step) => {
        return <SingleStep key={step.id} step={step} />;
      })}
    </Box>
  );
};

export default Steps;
