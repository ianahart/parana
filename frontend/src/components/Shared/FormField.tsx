import { Box, FormControl, FormLabel, Input, Text } from '@chakra-ui/react';
import { useState } from 'react';
import { AiOutlineEyeInvisible, AiOutlineEye } from 'react-icons/ai';

interface IFormFieldProps {
  updateField: (name: string, value: string, attribute: string) => void;
  name: string;
  value: string;
  error: string;
  type: string;
  label: string;
  id: string;
  errorField: string;
  icon?: React.ReactNode;
  placeHolder: string;
  minChar?: number;
  maxChar?: number;
}

const FormField = ({
  updateField,
  name,
  value,
  error,
  type,
  label,
  id,
  errorField,
  icon = undefined,
  placeHolder,
  maxChar = 200,
  minChar = 1,
}: IFormFieldProps) => {
  const [inputIconShowing, setInputIconShowing] = useState(true);

  const handleOnChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    updateField(name, value, 'value');
  };

  const handleOnBlur = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    if (value.trim().length === 0 || value.length > maxChar) {
      const error = `${errorField} must be between ${minChar} and ${maxChar} characters`;
      updateField(name, error, 'error');
    }
    setInputIconShowing(true);
  };

  const handleOnFocus = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name } = e.target;
    updateField(name, '', 'error');
    setInputIconShowing(false);
  };

  const togglePasswordVisibility = () => {
    const updatedType = type === 'password' ? 'text' : 'password';
    updateField('password', updatedType, 'type');
    updateField('confirmPassword', updatedType, 'type');
  };

  return (
    <FormControl my="1.5rem">
      <FormLabel htmlFor={name} color="text.secondary">
        {label}
      </FormLabel>
      <Box position="relative">
        <Input
          autoComplete="off"
          borderColor="border.primary"
          color="text.secondary"
          pl="1.5rem"
          _placeholder={{ color: 'text.secondary' }}
          placeholder={placeHolder}
          type={type}
          id={id}
          name={name}
          value={value}
          onChange={handleOnChange}
          onBlur={handleOnBlur}
          onFocus={handleOnFocus}
        />
        {icon !== undefined && inputIconShowing && (
          <Box position="absolute" top="9px" fontSize="1.3rem" left="3px">
            {icon}
          </Box>
        )}
        {name === 'password' && (
          <Box
            onClick={togglePasswordVisibility}
            position="absolute"
            zIndex={5}
            top="9px"
            right="5px"
            cursor="pointer"
          >
            {type === 'password' ? <AiOutlineEye /> : <AiOutlineEyeInvisible />}
          </Box>
        )}

        {error.length > 0 && (
          <Text fontSize="0.85rem" my="1rem" textAlign="center" color="error.primary">
            {error}
          </Text>
        )}
      </Box>
    </FormControl>
  );
};

export default FormField;
