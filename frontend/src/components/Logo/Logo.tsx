import logoImg from '../../assets/logo.png';
import { Box, Image } from '@chakra-ui/react';

interface ILogoProps {
  width: string;
  height: string;
}

const Logo = ({ width, height }: ILogoProps) => {
  return (
    <Box>
      <Image width={width} height={height} src={logoImg} alt="A snowboard" />
    </Box>
  );
};

export default Logo;
