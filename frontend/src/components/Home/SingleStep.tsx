import { Box, Flex, Image, SlideFade, Text } from '@chakra-ui/react';
import { IStep } from '../../interfaces';
import { useInView } from 'react-intersection-observer';

interface ISingleStepProps {
  step: IStep;
}

const SingleStep = ({ step }: ISingleStepProps) => {
  const { ref, inView } = useInView({
    trackVisibility: true,
    delay: 600,
    threshold: 0.5,
    triggerOnce: true,
  });

  return (
    <SlideFade in={inView} offsetY={step.id % 2 === 0 ? '200px' : '-200px'}>
      <Flex
        ref={ref}
        flexDir={
          step.id % 2 === 0
            ? ['column', 'row-reverse', 'row-reverse']
            : ['column', 'row', 'row']
        }
        justifyContent="space-between"
        my="10rem"
      >
        <Box color="text.secondary">
          <Box width="200px">
            <Text fontSize="1.5rem">
              {step.id}. {step.name}
            </Text>
            <Text lineHeight="1.6">{step.text}</Text>
          </Box>
        </Box>
        <Flex
          flexDir="column"
          align="center"
          justify="center"
          borderRadius={12}
          bg="purple.300"
          width={['95%', '400px', '400px']}
          height="300px"
        >
          <Image width="100px" src={step.image} alt={step.name} />
        </Flex>
      </Flex>
    </SlideFade>
  );
};

export default SingleStep;
