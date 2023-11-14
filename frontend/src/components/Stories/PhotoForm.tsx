import {
  Box,
  Flex,
  Text,
  Select,
  FormLabel,
  FormControl,
  Input,
  Tooltip,
  ButtonGroup,
  Button,
  Fade,
  Heading,
} from '@chakra-ui/react';
import GoBackButton from './GoBackButton';
import { useState } from 'react';
import { storyFormState } from '../../state/initialState';
import { IStoryForm } from '../../interfaces';
import { BsImage } from 'react-icons/bs';

interface IPhotoFormProps {
  resetStoryType: () => void;
  handleCreateStory: (form: IStoryForm) => void;
}

type TFile = File | null;

const PhotoForm = ({ resetStoryType, handleCreateStory }: IPhotoFormProps) => {
  const MAX_BYTES = 2000000;
  const [form, setForm] = useState<IStoryForm>(storyFormState);
  const [dataURL, setDataURL] = useState('');
  const [error, setError] = useState('');

  const updateField = <T,>(name: string, value: T, attribute: string) => {
    setForm((prevState) => ({
      ...prevState,
      [name]: { ...prevState[name as keyof IStoryForm], [attribute]: value },
    }));
  };

  const handleOnSelectChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    const { name, value } = e.target;
    updateField(name, value, 'value');
  };

  const validateFileSize = (file: TFile) => {
    const result = { validated: true, error: '' };
    if (file !== null && file.size > MAX_BYTES) {
      result.validated = false;
      result.error = 'This file exceeds the 2MB limit';
    }
    return result;
  };

  const updateFiles = (file: TFile, name: string) => {
    const fileReader = new FileReader();

    fileReader.onloadend = (e) => {
      setDataURL(e.target?.result as string);
    };
    if (file !== null) {
      fileReader.readAsDataURL(file);
      updateField(name, file, 'value');
    }
  };

  const handleOnFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { files, name } = e.target;
    if (files === null) return;
    const { validated, error } = validateFileSize(files[0]);
    if (!validated) {
      setError(error);
      return;
    }
    updateFiles(files[0], name);
  };

  const resetForm = () => {
    updateField('duration', '10000', 'value');
    updateField('file', null, 'value');
    setDataURL('');
  };

  const goBackAndReset = () => {
    resetStoryType();
    resetForm();
  };

  const handleClearForm = (e: React.MouseEvent<HTMLButtonElement>) => {
    e.stopPropagation();
    resetForm();
  };

  const handleOnSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    setError('');
    e.preventDefault();
    handleCreateStory(form);
    goBackAndReset();
  };

  const handleOnDrop = (e: React.DragEvent<HTMLDivElement>, name: string) => {
    e.preventDefault();
    e.stopPropagation();
    if (!e.dataTransfer.files) {
      return;
    }
    const files = e.dataTransfer.files;
    if (files === null) return;
    const { validated, error } = validateFileSize(files[0]);
    if (!validated) {
      setError(error);
      return;
    }
    updateFiles(files[0], name);
  };

  return (
    <Box width="100%" color="text.secondary">
      <Flex m="2rem" justify="flex-start">
        <GoBackButton resetForm={resetForm} resetStoryType={resetStoryType} />
      </Flex>
      <Flex className="photo-story-container">
        <Box my="1rem" width={['95%', '95%', '500px']} p="1rem">
          <form onSubmit={handleOnSubmit}>
            <FormControl width="200px">
              <FormLabel htmlFor="duration">Duration</FormLabel>
              <Select
                onChange={handleOnSelectChange}
                value={form.duration.value}
                border="1px solid"
                borderColor="border.primary"
                id="duration"
                name="duration"
              >
                <option value="5000">5s</option>
                <option value="10000">10s</option>
                <option value="15000">15s</option>
              </Select>
            </FormControl>

            <Box my="2rem">
              <Text>Upload Photo</Text>
              <Tooltip label="Click to upload">
                <Flex
                  borderRadius={8}
                  width="60px"
                  height="60px"
                  dir="column"
                  align="center"
                  justify="center"
                  bg="black.tertiary"
                  my="1rem"
                  fontSize="2rem"
                  position="relative"
                  cursor="pointer"
                >
                  <Input
                    onChange={handleOnFileChange}
                    cursor="pointer"
                    zIndex={3}
                    opacity={0}
                    pos="absolute"
                    height="100%"
                    width="100%"
                    type="file"
                    name="file"
                    id="file"
                    accept="image/*"
                  />
                  <BsImage />
                </Flex>
              </Tooltip>
              {error.length > 0 && (
                <Text textAlign="center" fontSize="0.8rem" color="error.primary">
                  {error}
                </Text>
              )}
              <Flex onDrop={(e) => handleOnDrop(e, 'file')} justify="center" my="1rem">
                <Flex
                  dir="column"
                  align="center"
                  justify="center"
                  width="200px"
                  height="150px"
                  bg="blackAlpha.500"
                  pos="relative"
                  backgroundImage={dataURL.length ? dataURL : 'unset'}
                  backgroundSize="cover"
                  borderRadius={8}
                >
                  <Input
                    name="file"
                    id="file"
                    type="file"
                    opacity={0}
                    width="100%"
                    height="100%"
                    pos="absolute"
                    zIndex={3}
                  />
                  {!dataURL.length && <Text>Drop a photo</Text>}
                </Flex>
              </Flex>
            </Box>
            {dataURL.length > 0 && (
              <Fade in={dataURL.length > 0}>
                <ButtonGroup display="flex" justifyContent="space-around">
                  <Button onClick={(e) => handleClearForm(e)}>Clear</Button>
                  <Button type="submit" colorScheme="blackAlpha">
                    Share
                  </Button>
                </ButtonGroup>
              </Fade>
            )}
          </form>
        </Box>
        <Flex
          dir="column"
          align="center"
          justify="center"
          my="1rem"
          minH="600px"
          width={['95%', '95%', '400px']}
          borderRadius={8}
          bg="blackAlpha.500"
          backgroundImage={dataURL.length ? dataURL : 'unset'}
          backgroundSize="cover"
          pos="relative"
        >
          <Box
            cursor="pointer"
            onClick={goBackAndReset}
            bg="blue.500"
            pos="absolute"
            top="10px"
            p="0.5rem"
            borderRadius={8}
            right="5px"
          >
            <Text color="#fff" fontWeight="bold">
              Exit preview
            </Text>
          </Box>
          {dataURL.length <= 0 && (
            <Box>
              <Flex
                mx="auto"
                width="45px"
                fontSize="1.2rem"
                height="45px"
                borderRadius={50}
                bg="primary.light"
                my="0.5rem"
                justify="center"
                align="center"
                dir="column"
              >
                <BsImage />
              </Flex>
              <Heading fontSize="1.2rem">Upload to preview</Heading>
            </Box>
          )}
        </Flex>
      </Flex>
    </Box>
  );
};

export default PhotoForm;
