import { Box } from '@chakra-ui/react';
import { useEffect, useRef, useState } from 'react';

interface IDurationBarProps {
  duration: number;
  isActive: boolean;
}

const DurationBar = ({ duration, isActive }: IDurationBarProps) => {
  const [progress, setProgress] = useState(0);
  const shouldRun = useRef(true);

  useEffect(() => {
    let interval: any;
    interval = setInterval(() => {
      if (isActive) {
        setProgress((prevState) => prevState + 60 / (duration / 1000));
      }
    }, 1000);

    return () => {
      setProgress(0);
      clearInterval(interval);
    };
  }, [isActive, shouldRun.current]);
  return (
    <Box bg="gray" m="0.25rem" borderRadius={8} width="60px" height="8px">
      <Box
        height="8px"
        transition="ease-in width 0.25s"
        borderRadius={8}
        width={`${progress}px`}
        bg="#fff"
      ></Box>
    </Box>
  );
};

export default DurationBar;
