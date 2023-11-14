import { Box, Button, Flex, FormLabel, Select, Text, Textarea } from '@chakra-ui/react';
import GoBackButton from './GoBackButton';
import { RxTextAlignCenter, RxTextAlignTop, RxTextAlignBottom } from 'react-icons/rx';
import { useState } from 'react';
import { backgroundColors, storyFormState, textColors } from '../../state/initialState';
import { IStoryForm } from '../../interfaces';
import Alignment from './Alignment';
import { AiOutlineCheck } from 'react-icons/ai';

interface ITextFormProps {
  resetStoryType: () => void;
  handleCreateStory: (form: IStoryForm) => void;
}

const TextForm = ({ resetStoryType, handleCreateStory }: ITextFormProps) => {
  const [form, setForm] = useState<IStoryForm>(storyFormState);

  const checkForText = () => {
    return !(form.text.value.trim().length === 0 || form.text.value.length > 150);
  };

  const handleOnSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (!checkForText()) {
      return;
    }
    handleCreateStory(form);
    goBackAndReset();
  };

  const updateField = (name: string, value: string, attribute: string) => {
    setForm((prevState) => ({
      ...prevState,
      [name]: { ...prevState[name as keyof IStoryForm], [attribute]: value },
    }));
  };

  const goBackAndReset = () => {
    resetStoryType();
    resetForm();
  };

  const resetForm = () => {
    setForm((prevState) => ({
      ...prevState,
      ...storyFormState,
    }));
  };

  const handleOnTextChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    updateField(name, value, 'value');
  };

  const textAlignment =
    form.alignment.value === 'center'
      ? 'center'
      : form.alignment.value === 'bottom'
      ? 'flex-end'
      : 'flex-start';

  const handleOnSelectChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    const { name, value } = e.target;
    updateField(name, value, 'value');
  };

  const generateBackgrounds = () => {
    return backgroundColors.map((bgc, index) => {
      return (
        <Box
          cursor="pointer"
          onClick={() => updateField('background', bgc, 'value')}
          m="0.5rem"
          bg={bgc}
          key={index}
          width="35px"
          height="35px"
          borderRadius={8}
        ></Box>
      );
    });
  };

  return (
    <Box width="100%" color="text.secondary">
      <Flex m="2rem" justify="flex-start">
        <GoBackButton resetForm={resetForm} resetStoryType={resetStoryType} />
      </Flex>
      <Flex className="text-story-container">
        <Box my="1rem" width={['95%', '95%', '500px']} p="1rem">
          <form onSubmit={handleOnSubmit}>
            <Text>Alignment</Text>
            <Flex justify="space-around" my="1.5rem">
              <Alignment
                updateField={updateField}
                value="top"
                icon={<RxTextAlignTop />}
              />
              <Alignment
                updateField={updateField}
                value="center"
                icon={<RxTextAlignCenter />}
              />
              <Alignment
                updateField={updateField}
                value="bottom"
                icon={<RxTextAlignBottom />}
              />
            </Flex>
            <Flex justify="space-around" my="1rem">
              <Box>
                <FormLabel htmlFor={form.duration.name}>Duration</FormLabel>
                <Select
                  onChange={handleOnSelectChange}
                  width="120px"
                  value={form.duration.value}
                  borderColor="border.primary"
                  name={form.duration.name}
                  id={form.duration.name}
                >
                  <option value="5000">5s</option>
                  <option value="10000">10s</option>
                  <option value="15000">15s</option>
                </Select>
              </Box>
              <Box>
                <FormLabel htmlFor={form.fontSize.name}>Font Size</FormLabel>
                <Select
                  onChange={handleOnSelectChange}
                  width="120px"
                  value={form.fontSize.value}
                  borderColor="border.primary"
                  name={form.fontSize.name}
                  id={form.fontSize.name}
                >
                  <option value="12px">12px</option>
                  <option value="18px">18px</option>
                  <option value="24px">24px</option>
                  <option value="36px">36px</option>
                </Select>
              </Box>
            </Flex>

            <Text>Text Color</Text>
            <Flex flexWrap="wrap">
              {textColors.map((textColor) => {
                return (
                  <Flex
                    dir="column"
                    align="center"
                    justify="center"
                    cursor="pointer"
                    onClick={() => updateField('color', textColor.value, 'value')}
                    m="0.5rem"
                    key={textColor.id}
                    width="35px"
                    height="35px"
                    borderRadius={8}
                    border="1px solid"
                    borderColor="border.primary"
                    bg={textColor.value}
                  >
                    {form.color.value === textColor.value && (
                      <Box color="text.primary">
                        <AiOutlineCheck />
                      </Box>
                    )}
                  </Flex>
                );
              })}
            </Flex>
            <FormLabel htmlFor="text">Text</FormLabel>
            <Textarea
              id="text"
              _hover={{ borderColor: 'border.primary' }}
              onChange={handleOnTextChange}
              name={form.text.name}
              value={form.text.value}
              fontSize="0.9rem"
              _placeholder={{ fontSize: '0.9rem' }}
              placeholder="Enter text for your story..."
              borderColor="border.primary"
              resize="none"
            />

            <Text my="1.5rem">Background Color</Text>
            <Flex flexWrap="wrap">{generateBackgrounds()}</Flex>
            <Flex my="2rem" justify="space-around">
              <Button onClick={resetForm}>Clear</Button>
              <Button type="submit" colorScheme="blackAlpha">
                Share
              </Button>
            </Flex>
          </form>
        </Box>
        <Flex
          dir="column"
          justify="center"
          align={textAlignment}
          my="1rem"
          minH="600px"
          width={['95%', '95%', '400px']}
          borderRadius={8}
          bg={form.background.value}
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
          <Box>
            <Text
              fontWeight="bold"
              color={form.color.value}
              fontSize={form.fontSize.value}
              p="0.25rem"
              wordBreak="break-all"
            >
              {form.text.value}
            </Text>
          </Box>
        </Flex>
      </Flex>
    </Box>
  );
};

export default TextForm;
