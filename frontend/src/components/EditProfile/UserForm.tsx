import {
  Flex,
  Box,
  Heading,
  RadioGroup,
  Stack,
  Radio,
  FormLabel,
  Button,
  useToast,
} from '@chakra-ui/react';
import FileUpload from '../Shared/FileUpload';
import { useContext, useEffect, useRef, useState } from 'react';
import { Client } from '../../util/client';
import { UserContext } from '../../context/user';
import { IEditUserProfileForm, IUserContext } from '../../interfaces';
import {
  editUserProfileFormState,
  statesState,
  terrainState,
} from '../../state/initialState';
import FormField from '../Shared/FormField';
import FormTextarea from '../Shared/FormTextarea';
import FormSelect from '../Shared/FormSelect';
import axios from 'axios';
import FormList from '../Shared/FormList';

interface IObject {
  [key: string]: string;
}

const UserForm = () => {
  const toast = useToast();
  const shouldRun = useRef(true);
  const { user, updateUser } = useContext(UserContext) as IUserContext;
  const [form, setForm] = useState<IEditUserProfileForm>(editUserProfileFormState);
  const [uploadError, setUploadError] = useState('');
  const [terrain, setTerrain] = useState<string[]>(terrainState);
  const [cities, setCities] = useState<string[]>([]);
  const updateTerrain = (selectedTerrain: string[]) => {
    setTerrain(
      terrain.filter((t) => {
        if (!selectedTerrain.includes(t)) {
          return t;
        }
      })
    );
  };

  const syncForm = <T extends IObject>(data: T) => {
    const exclude = ['id', 'avatarUrl', 'userId', 'role', 'tags', 'aboutLesson'];
    const transform = ['terrain'];
    console.log('test', data);
    for (const key of Object.keys(data)) {
      if (exclude.includes(key)) {
        continue;
      }
      if (transform.includes(key)) {
        updateTerrain(data['terrain'].split(','));
        updateField(key, data[key] === null ? [] : data[key].split(','), 'value');
      } else {
        const name = key as keyof IEditUserProfileForm;
        if (name !== null) {
          const value = data[key] ? data[key] : form[name].value ? form[name].value : '';
          updateField(key, value, 'value');
        }
      }
    }
  };

  const fetchProfile = () => {
    Client.getProfile(user.profileId)
      .then((res) => {
        const { data } = res.data;
        syncForm(data);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  useEffect(() => {
    if (shouldRun.current && user.profileId !== 0) {
      fetchProfile();
    }
  }, [shouldRun.current, user.profileId]);

  const populateCityField = async (state: string) => {
    const endpoint = 'https://countriesnow.space/api/v0.1/countries/state/cities';
    const response = await axios.post(endpoint, { country: 'United States', state });
    setCities(response.data.data);
  };

  useEffect(() => {
    if (cities.length > 0 && !form.city.value.length) {
      setForm((prevState) => ({
        ...prevState,
        city: { ...prevState['city' as keyof IEditUserProfileForm], value: cities[0] },
      }));
    }
  });

  const isNotANumber = (value: string) => {
    return /^-?[0-9]+$/.test(value + '');
  };

  const updateField = (name: string, value: any, attribute: string) => {
    if (name === 'state') {
      populateCityField(value);
    }

    if (name === 'perHour') {
      if (!isNotANumber(value) && value !== '') {
        return;
      }
    }
    setForm((prevState) => ({
      ...prevState,
      [name]: { ...prevState[name as keyof IEditUserProfileForm], [attribute]: value },
    }));
  };

  const uploadFile = async (file: File | null, endpoint: string, action?: string) => {
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

  const addOptionToList = (name: string, option: string) => {
    const key = name as keyof IEditUserProfileForm;
    const selectedOptions = [...form.terrain.value, option];
    setTerrain((prevState) => prevState.filter((t) => t !== option));
    setForm((prevState) => ({
      ...prevState,
      [key]: {
        ...prevState[key],
        value: selectedOptions,
      },
    }));
  };

  const removeOptionFromList = (name: string, option: string) => {
    const key = name as keyof IEditUserProfileForm;
    setTerrain((prevState) => [...prevState, option]);
    const selectedOptions = [...form.terrain.value].filter((opt) => opt !== option);
    setForm((prevState) => ({
      ...prevState,
      [key]: { ...prevState[key], value: selectedOptions },
    }));
  };

  const packageFormData = (form: IEditUserProfileForm) => {
    return {
      bio: form.bio.value,
      city: form.city.value,
      homeMountain: form.homeMountain.value,
      stance: form.stance.value,
      state: form.state.value,
      terrain: form.terrain.value.join(','),
      travelUpTo: form.travelUpTo.value,
      yearsSnowboarding: parseInt(form.yearsSnowboarding.value),
      formType: 'USER',
    };
  };

  const applyServerErrors = <T extends IObject>(data: T) => {
    for (const key of Object.keys(data)) {
      updateField(key, data[key], 'error');
    }
  };

  const handleOnSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const formData = packageFormData(form);
    Client.updateProfile(formData, user.profileId)
      .then(() => {
        toast({
          title: 'Profile updated',
          description: 'Your profile has been saved successfully',
          status: 'success',
          duration: 6000,
          isClosable: true,
        });
      })
      .catch((err) => {
        applyServerErrors(err.response.data);
      });
  };

  return (
    <Box width={['95%', '95%', '750px']} minH="100vh">
      <Box as="header">
        <Heading fontSize="1.75rem" color="text.secondary">
          Edit Profile
        </Heading>
      </Box>
      <Flex p="0.25rem" mx="0.25rem" flexDir="column" justify="center" align="center">
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
      <Flex minH="100vh" color="text.secondary" my="1rem" p="0.25rem" flexDir="column">
        <form onSubmit={handleOnSubmit}>
          <FormTextarea
            updateField={updateField}
            name={form.bio.name}
            value={form.bio.value}
            error={form.bio.error}
            label="Bio"
            id="bio"
            errorField="Bio"
            placeHolder="Please tell us about yourself..."
          />
          <FormField
            updateField={updateField}
            name={form.yearsSnowboarding.name}
            value={form.yearsSnowboarding.value}
            error={form.yearsSnowboarding.error}
            type={form.yearsSnowboarding.type}
            label="Years Snowboarding"
            id="yearsSnowbarding"
            errorField="Years snowboarding"
            placeHolder="How long have you been snowboarding?"
          />
          <Flex
            flexDir={['column', 'column', 'row']}
            my="1.5rem"
            justify="space-between"
            width="100%"
          >
            <FormSelect
              minWidth={['100%', '250px', '250px']}
              updateField={updateField}
              options={statesState}
              name={form.state.name}
              value={form.state.value}
              height="300px"
              label="State"
            />
            {cities.length > 0 && (
              <FormSelect
                minWidth={['100%', ' 250px', '250px']}
                updateField={updateField}
                options={cities}
                name={form.city.name}
                value={form.city.value}
                height="300px"
                label="City"
              />
            )}
          </Flex>
          <Box my="1.5rem">
            <FormLabel htmlFor="stance">Stance</FormLabel>
            <RadioGroup
              onChange={(value) => updateField(form.stance.name, value, 'value')}
              colorScheme="blackAlpha"
              name={form.stance.name}
              id="stance"
              defaultValue="REGULAR"
            >
              <Stack spacing={4} direction="row">
                <Radio value="REGULAR">Regular</Radio>
                <Radio value="GOOFY">Goofy</Radio>
              </Stack>
            </RadioGroup>
          </Box>
          <FormField
            updateField={updateField}
            name={form.homeMountain.name}
            value={form.homeMountain.value}
            error={form.homeMountain.error}
            type={form.homeMountain.type}
            label="Home Mountain"
            id="homeMountain"
            errorField="Home mountain"
            placeHolder="Where do you ride the most?"
          />
          <Box my="1.5rem">
            <FormList
              label="Terrain Ridden"
              name="terrain"
              options={terrain}
              selectedOptions={form.terrain.value}
              addOptionToList={addOptionToList}
              removeOptionFromList={removeOptionFromList}
            />
          </Box>
          <FormField
            updateField={updateField}
            name={form.travelUpTo.name}
            value={form.travelUpTo.value}
            error={form.travelUpTo.error}
            type={form.travelUpTo.type}
            label="Travel Up To"
            id="travelUpTo"
            errorField="Travel up to"
            placeHolder="How far are you willing to travel?"
          />
          <Flex mt="3rem" mb="1rem">
            <Button type="submit" width="100%" colorScheme="blackAlpha">
              Save Profile
            </Button>
          </Flex>
        </form>
      </Flex>
    </Box>
  );
};

export default UserForm;
