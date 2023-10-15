import { Flex, Box, Heading } from '@chakra-ui/react';
import FileUpload from '../Shared/FileUpload';
import { useContext, useState } from 'react';
import { Client } from '../../util/client';
import { UserContext } from '../../context/user';
import { IUserContext } from '../../interfaces';

const TeacherForm = () => {
  const { user, updateUser } = useContext(UserContext) as IUserContext;
  const [uploadError, setUploadError] = useState('');
  const uploadFile = async(file: File | null, endpoint: string, action?: string) => {
    setUploadError('');
    return Client.uploadProfilePhoto(file, endpoint, action)
      .then((res) => {
        updateUser({
          ...user,
          avatarUrl: res.data.avatarUrl,
        });
      })
      .catch((err) => {
        setUploadError(err.response.data.message);
      });
  };

  return (
    <Box width={['95%', '95%', '750px']} border="1px solid blue">
      <Box as="header">
        <Heading fontSize="1.75rem" color="text.secondary">
          Edit Profile
        </Heading>
      </Box>
      <Flex
        my="3rem"
        p="0.25rem"
        mx="0.25rem"
        flexDir="column"
        justify="center"
        align="center"
        border="1px solid gray"
      >
        <Flex my="2rem">
          <FileUpload
            error={uploadError}
            endpoint={`/profiles/${user.profileId}/file-upload`}
            limit={2 * 1024 * 1024}
            icon={true}
            width="200px"
            height="200px"
            borderRadius="50%"
            uploadFile={uploadFile}
          />
        </Flex>
      </Flex>
    </Box>
  );
};

export default TeacherForm;
