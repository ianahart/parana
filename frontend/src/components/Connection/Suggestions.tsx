import { Box, Flex, Heading, Text, Grid, GridItem } from '@chakra-ui/react';
import { useEffect, useRef, useState } from 'react';
import { Client } from '../../util/client';
import { ISuggestion } from '../../interfaces';
import ViewChanger from './ViewChanger';
import Suggestion from './Suggestion';

const Suggestions = () => {
  const shouldRun = useRef(true);
  const [suggestions, setSuggestions] = useState<ISuggestion[]>([]);
  const [mobileView, setMobileView] = useState(false);

  const removeSuggestion = (teacherId: number) => {
    setSuggestions((prevState) =>
      prevState.filter((suggestion) => suggestion.teacherId !== teacherId)
    );
  };

  const getSuggestions = () => {
    Client.getSuggestions()
      .then((res) => {
        setSuggestions(res.data.data);
      })
      .catch((err) => {
        throw new Error(err);
      });
  };

  useEffect(() => {
    if (shouldRun.current) {
      shouldRun.current = false;
      getSuggestions();
    }
  }, [shouldRun.current]);

  return (
    <>
      <Flex color="text.secondary" align="center" justify="space-between">
        <Box as="header">
          <Heading fontSize="1.2rem">Suggestions</Heading>
          <Text fontWeight="bold" mt="1.5rem">
            {suggestions.length} suggestions available
          </Text>
        </Box>
        <ViewChanger mobileView={mobileView} setMobileView={setMobileView} />
      </Flex>
      <Grid
        mt="4rem"
        mb="2rem"
        gridTemplateColumns={
          !mobileView
            ? ['repeat(1,1fr)', 'repeat(2,1fr)', 'repeat(3, 1fr)']
            : 'repeat(1, 1fr)'
        }
      >
        {suggestions.map((suggestion) => {
          return (
            <GridItem key={suggestion.teacherId}>
              <Suggestion suggestion={suggestion} removeSuggestion={removeSuggestion} />
            </GridItem>
          );
        })}
      </Grid>
    </>
  );
};

export default Suggestions;
