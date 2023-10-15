import { useRef } from 'react';
import { Box, Flex, Input, FormLabel } from '@chakra-ui/react';

interface IOtpInputProps {
  passCode: string[];
  setPassCode: (passCode: any) => void;
  numOfInputs: number;
}

const OtpInput = ({ passCode, setPassCode, numOfInputs }: IOtpInputProps) => {
  const inputsRef = useRef<HTMLDivElement>(null);

  const goToNextPassCodeInput = (inputs: Element[], index: number) => {
    if (index + 1 > inputs.length - 1) return;
    const nextInput = inputs[index + 1] as HTMLElement;
    nextInput.focus();
  };

  const goToPrevPassCodeInput = (inputs: Element[], index: number) => {
    if (index !== 0) {
      const prevInput = inputs[index - 1] as HTMLElement;
      prevInput.focus();
    }
  };

  const validatePassCode = (value: string) => {
    const acceptedValues = '0123456789';
    return value.trim().length > 0 && acceptedValues.includes(value);
  };

  const updatePassCode = (value: string) => {
    if (validatePassCode(value)) {
      setPassCode((prevState: any) => [...prevState, value]);
    }
  };

  const handleOnPassCodeChange = (
    e: React.ChangeEvent<HTMLInputElement>,
    index: number
  ) => {
    const { value } = e.target;
    updatePassCode(value);
    if (inputsRef.current === null) return;
    const inputs = Array.from(inputsRef.current?.children);
    if (validatePassCode(value)) {
      goToNextPassCodeInput(inputs, index);
    }
  };

  const handleOnPassCodeKeyUp = (
    e: React.KeyboardEvent<HTMLInputElement>,
    index: number
  ) => {
    if (e.key === 'Backspace') {
      if (inputsRef.current === null) return;
      const inputs = Array.from(inputsRef.current?.children);
      goToPrevPassCodeInput(inputs, index);
      const filtered = passCode.filter((_, pIndex) => pIndex !== index);
      setPassCode(filtered);
    }
  };

  const handleOnPastePassCode = (e: React.ClipboardEvent<HTMLInputElement>) => {
    const pastedPassCode = e.clipboardData.getData('Text');
    setPassCode(pastedPassCode.split(''));
  };

  return (
    <Box my="1.5rem" color="text.secondary">
      <FormLabel>Pass Code</FormLabel>
      <Flex ref={inputsRef} justify="center">
        {[...Array(numOfInputs)].map((_, index) => {
          return (
            <Input
              onPaste={handleOnPastePassCode}
              mx="1rem"
              key={index}
              value={passCode[index] ? passCode[index] : ''}
              onChange={(e) => handleOnPassCodeChange(e, index)}
              onKeyUp={(e) => handleOnPassCodeKeyUp(e, index)}
              fontSize="1.5rem"
              borderColor="border.primary"
              maxLength={1}
            />
          );
        })}
      </Flex>
    </Box>
  );
};

export default OtpInput;
