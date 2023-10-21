import {
  Box,
  Button,
  ButtonGroup,
  Divider,
  Flex,
  Slider,
  SliderFilledTrack,
  SliderThumb,
  SliderTrack,
  Text,
  Tooltip,
  useDisclosure,
} from '@chakra-ui/react';
import FilterContainer from './FilterContainer';
import { useState } from 'react';
import { AiOutlineClose } from 'react-icons/ai';

interface IDistanceFilterProps {
  resetFilter: () => void;
  setDistance: (rate: number) => void;
  distance: number;
}

const DistanceFilter = ({ distance, setDistance, resetFilter }: IDistanceFilterProps) => {
  const MIN_VALUE = 1;
  const MAX_VALUE = 200;
  const { isOpen, onOpen, onClose } = useDisclosure();
  const [sliderValue, setSliderValue] = useState(MIN_VALUE);

  const handleOnClose = (e: React.MouseEvent<HTMLButtonElement>) => {
    e.stopPropagation();
    onClose();
    setSliderValue(MIN_VALUE);
    setDistance(MIN_VALUE);
  };

  const handleOnApplyDistance = (e: React.MouseEvent<HTMLButtonElement>) => {
    e.stopPropagation();
    onClose();
    setDistance(sliderValue);
  };

  return (
    <>
      <Flex
        mx="1rem"
        cursor="pointer"
        py="0.4rem"
        px="1.5rem"
        height="40px"
        _hover={{ background: 'primary.light', opacity: 0.7 }}
        color="#fff"
        borderRadius={20}
        background="primary.light"
        align="center"
      >
        <Text onClick={onOpen}>Distance</Text>
        {distance > MIN_VALUE && (
          <Box
            onClick={() => {
              setSliderValue(MIN_VALUE);
              setDistance(MIN_VALUE);
              resetFilter();
            }}
            cursor="pointer"
            ml="0.25rem"
          >
            <AiOutlineClose />
          </Box>
        )}
      </Flex>
      <FilterContainer isOpen={isOpen} onClose={onClose}>
        <Box color="text.secondary">
          <Text fontWeight="bold" mt="0.5rem" fontSize="0.9rem">
            maximum distance: {sliderValue ? `${sliderValue}mi` : '200mi'}
          </Text>
          <Slider
            mt="4rem"
            mb="0.5rem"
            id="slider"
            defaultValue={distance}
            min={MIN_VALUE}
            max={MAX_VALUE}
            colorScheme="blackAlpha"
            onChange={(value) => setSliderValue(value)}
          >
            <SliderTrack>
              <SliderFilledTrack />
            </SliderTrack>
            <Tooltip
              hasArrow
              colorScheme="blackAlpha"
              color="white"
              borderRadius={20}
              fontWeight="bold"
              placement="top"
              isOpen={true}
              label={`${sliderValue}mi`}
            >
              <SliderThumb />
            </Tooltip>
          </Slider>
          <Flex fontWeight="bold" justify="space-between" mb="3rem">
            <Text>{MIN_VALUE}</Text>
            <Text>{MAX_VALUE}</Text>
          </Flex>
          <Divider borderColor="border.primary" />
          <ButtonGroup display="flex" justifyContent="space-around" mt="2rem" mb="0.5rem">
            <Button onClick={handleOnClose} minW="120px" borderRadius={20}>
              Cancel
            </Button>
            <Button
              onClick={handleOnApplyDistance}
              minW="120px"
              borderRadius={20}
              colorScheme="blackAlpha"
            >
              Apply
            </Button>
          </ButtonGroup>
        </Box>
      </FilterContainer>
    </>
  );
};

export default DistanceFilter;
