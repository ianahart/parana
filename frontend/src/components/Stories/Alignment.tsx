import { Box, Flex, Tooltip } from '@chakra-ui/react';

interface IAlignmentProps {
  value: string;
  icon: React.ReactNode;
  updateField: (name: string, value: string, attribute: string) => void;
}

const Alignment = ({ value, icon, updateField }: IAlignmentProps) => {
  return (
    <>
      <Tooltip label={value} placement="top-end">
        <Flex
          onClick={() => updateField('alignment', value, 'value')}
          dir="column"
          align="center"
          justify="center"
          bg="blackAlpha.500"
          borderRadius={8}
          height="45px"
          width="45px"
        >
          <Box cursor="pointer" color="text.secondary" fontSize="1.6rem">
            {icon}
          </Box>
        </Flex>
      </Tooltip>
    </>
  );
};

export default Alignment;
