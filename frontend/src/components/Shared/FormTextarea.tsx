import { Box, FormControl, FormLabel, Textarea, Text } from '@chakra-ui/react';

interface IFormTextareaProps {
  updateField: (name: string, value: string, attribute: string) => void;
  name: string;
  value: string;
  error: string;
  label: string;
  id: string;
  errorField: string;
  placeHolder: string;
  minChar?: number;
  maxChar?: number;
}

const FormTextarea = ({
  updateField,
  name,
  value,
  error,
  label,
  id,
  errorField,
  placeHolder,
  maxChar = 400,
  minChar = 1,
}: IFormTextareaProps) => {
  const handleOnChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    updateField(name, value, 'value');
  };

  const handleOnBlur = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    if (value.trim().length === 0 || value.length > maxChar) {
      const error = `${errorField} must be between ${minChar} and ${maxChar} characters`;
      updateField(name, error, 'error');
    }
  };

  const handleOnFocus = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    const { name } = e.target;
    updateField(name, '', 'error');
  };

  return (
    <FormControl my="1.5rem">
      <FormLabel htmlFor={name} color="text.secondary">
        {label}
      </FormLabel>
      <Box position="relative">
        <Textarea
          autoComplete="off"
          borderColor="border.primary"
          color="text.secondary"
          pl="1.5rem"
          _placeholder={{ color: 'text.secondary' }}
          placeholder={placeHolder}
          id={id}
          name={name}
          value={value}
          onChange={handleOnChange}
          onBlur={handleOnBlur}
          onFocus={handleOnFocus}
        />

        {error.length > 0 && (
          <Text fontSize="0.85rem" my="1rem" textAlign="center" color="error.primary">
            {error}
          </Text>
        )}
      </Box>
    </FormControl>
  );
};

export default FormTextarea;
