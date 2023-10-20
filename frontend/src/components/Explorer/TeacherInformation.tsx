import { Box, Text } from '@chakra-ui/react';

interface ITeacherInformationProps {
  firstName: string;
  city: string;
  state: string;
}

const TeacherInformation = ({ firstName, city, state }: ITeacherInformationProps) => {
  return (
    <Box fontWeight="bold" color="#fff" pos="absolute" bottom="10px" left="10px">
      <Text fontSize="1.3rem">{firstName}</Text>
      <Text fontSize="0.85rem">
        {city}, {state}
      </Text>
    </Box>
  );
};

export default TeacherInformation;
