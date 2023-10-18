import { Box, Flex } from '@chakra-ui/react';
import { useContext } from 'react';
import { UserContext } from '../context/user';
import { IUserContext } from '../interfaces';
import TeacherForm from '../components/EditProfile/TeacherForm';
import UserForm from '../components/EditProfile/UserForm';
const EditProfileRoute = () => {
  const { user } = useContext(UserContext) as IUserContext;
  return (
    <Box minH="100vh" pos="relative">
      <Flex minH="100vh" justify="center" mt="5rem" align="center" flexDir="column">
        {user.role === 'TEACHER' ? <TeacherForm /> : <UserForm />}
      </Flex>
    </Box>
  );
};

export default EditProfileRoute;
