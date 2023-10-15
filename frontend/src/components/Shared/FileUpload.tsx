import { Box, Button, Flex, Image, Input, Text, Tooltip } from '@chakra-ui/react';
import { useContext, useState } from 'react';
import { BsPencil, BsUpload } from 'react-icons/bs';
import { UserContext } from '../../context/user';
import { IUserContext } from '../../interfaces';
import BasicSpinner from './BasicSpinner';

interface IFileUploadProps {
  error?: string;
  endpoint: string;
  limit?: number;
  icon?: boolean;
  width?: string;
  height?: string;
  borderRadius?: string;
  uploadFile: (file: File | null, endpoint: string, action?: string) => Promise<void>;
}

const FileUpload = ({
  error = '',
  endpoint,
  limit = 2 * 1024 * 1024,
  icon = false,
  width = '150px',
  height = '150px',
  borderRadius = '8px',
  uploadFile,
}: IFileUploadProps) => {
  const { user } = useContext(UserContext) as IUserContext;
  const [file, setFile] = useState<File | null>(null);
  const [isLoading, setIsLoading] = useState(false);

  const validateFileSize = (newFile: File, limit: number) => {
    return !(newFile.size > limit);
  };

  const handleOnFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (!e.target.files) return;
    const newFile = e.target.files[0];
    if (!validateFileSize(newFile, limit)) {
      return;
    }
    setFile(newFile);
    setIsLoading(true);
    uploadFile(newFile, endpoint, 'upload').then(() => setIsLoading(false));
  };

  const removeFile = () => {
    setFile(null);
    setIsLoading(true);
    uploadFile(file, endpoint, 'remove').then(() => setIsLoading(false));
  };

  return (
    <Box>
      <Flex
        pos="relative"
        border="1px solid"
        borderColor="border.primary"
        height={height}
        width={width}
        borderRadius={borderRadius}
      >
        {icon && user.avatarUrl && (
          <Image
            height="100%"
            width="100%"
            pos="absolute"
            borderRadius={borderRadius}
            src={user.avatarUrl}
            alt={user.fullName}
          />
        )}

        <Flex
          flexDir="column"
          justify="center"
          mx="auto"
          align="center"
          color="text.secondary"
        >
          <Box>
            <BsUpload />
          </Box>
          <Text>Upload a photo</Text>
        </Flex>
        <Input
          onChange={handleOnFileChange}
          accept="image/*"
          cursor="pointer"
          opacity={0}
          zIndex={8}
          borderRadius={borderRadius}
          height="100%"
          width="100%"
          pos="absolute"
          type="file"
        />
        {icon && (
          <Tooltip
            label="Edit Profile Picture"
            color="text.secondary"
            bg="blackAlpha.500"
          >
            <Flex
              align="center"
              justify="center"
              flexDir="column"
              right="30px"
              bottom="0px"
              width="40px"
              height="40px"
              color="text.secondary"
              borderRadius={50}
              bg="primary.light"
              pos="absolute"
              fontSize="1.5rem"
            >
              <BsPencil />
            </Flex>
          </Tooltip>
        )}
      </Flex>
      {(file || user.avatarUrl) && (
        <Flex my="0.5rem" justify="flex-end">
          <Button onClick={removeFile} colorScheme="blackAlpha" size="sm">
            Remove
          </Button>
        </Flex>
      )}
      {error.length > 0 && (
        <Flex my="0.5rem" justify="center">
          <Text color="error.primary" fontSize="0.85rem">
            {error}
          </Text>
        </Flex>
      )}
      {isLoading && (
        <Flex justify="center" my="0.5rem">
          <BasicSpinner
            color="text.secondary"
            message="Uploading photo... Please wait."
          />
        </Flex>
      )}
    </Box>
  );
};

export default FileUpload;
