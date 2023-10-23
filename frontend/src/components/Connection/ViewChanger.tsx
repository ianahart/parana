import { Box, Flex, Tooltip } from '@chakra-ui/react';
import { BsGrid3X2Gap } from 'react-icons/bs';
import { BiSquareRounded } from 'react-icons/bi';

interface IViewChangerProps {
  mobileView: boolean;
  setMobileView: (mobileView: boolean) => void;
}

const ViewChanger = ({ mobileView, setMobileView }: IViewChangerProps) => {
  return (
    <Flex justify="flex-end" my="1rem">
      <Tooltip label="Change view">
        <Flex
          onClick={() => setMobileView(mobileView ? false : true)}
          width="50px"
          height="50px"
          flexDir="column"
          justify="center"
          align="center"
          borderRadius={50}
          bg="primary.dark"
        >
          {mobileView ? (
            <Box fontSize="1.6rem">
              <BsGrid3X2Gap />
            </Box>
          ) : (
            <Box>
              <BiSquareRounded />
            </Box>
          )}
        </Flex>
      </Tooltip>
    </Flex>
  );
};

export default ViewChanger;
