import React from 'react'

import { Heading, Grid, Flex, Link, Button, Text } from '@chakra-ui/react' 
import Divider from '../../components/Divider'
import Input from '../../components/Input'

import TecnobatteryLogo from '../../assets/tecnobattery.svg'

import InputPassword from '../../components/InputPassword'

import router from 'next/router'

import axios from "axios"

export default function Authentication() {

  const URL = "https://app-tecnobattery.herokuapp.com"
  

  const handleFormSubmit = (event) => {
    event.preventDefault();

    const endpoint = URL + "/login";

    const body = {
      username: "teste1",
      password: "123456"
    };

    const options = {
      headers: {
        "Content-Type": "application/json"
      }
    };
    axios.post(endpoint, body, options).then(function (response) {
      localStorage.setItem("Authorization", response.data) 
      return handleDashboard()
    }).catch(function (error) {
      alert(error.response.data.message + " " + error.response.data.timestamp)
    })
  }

  const handleDashboard = () => {
    axios.get(URL + "/management/api/v1/clients/7", { headers: { Authorization: localStorage.getItem('Authorization') } }).then(res => {
      if (res.status === 200 ) {
        router.push('/dashboard')
      } else {
        alert("Authentication failure");
      }
    });
  }

  return (
    <Grid
      as="main"
      height="100vh"
      templateColumns="1fr 480px 480px 1fr"
      templateRows="1fr 480px 1fr"
      templateAreas="
        '. . . .'
        '. logo form .'
        '. . . .'
      "
      justifyContent="center"
      alignItems="center"
    >
      <Flex gridArea="logo" flexDir="column" alignItems="flex-start">
        <Link
          href="/"
          _focus={{ border: 'none' }}
        >
          <TecnobatteryLogo />
        </Link>

        <Heading size="2xl" lineHeight="shorter" marginTop={16}>
          Faça seu login na plataforma
        </Heading>
      </Flex>

      <Flex
        gridArea="form"
        height="100%"
        backgroundColor="gray.700"
        borderRadius="md"
        flexDir="column"
        alignItems="stretch"
        padding={16}
      >
        <Input
          placeholder="E-mail"
          type="email"
        />

        <InputPassword />

        <Link
          alignSelf="flex-start"
          marginTop={2}
          fontSize="sm"
          color="red.600"
          fontWeight="bold"
          _hover={{ color: 'red.500' }}
        >
          Esqueci minha senha
        </Link>

        <Button
          backgroundColor="red.500"
          height="50px"
          borderRadius="sm"
          marginTop={6}
          _hover={{ backgroundColor: 'red.600' }}
          onClick={handleFormSubmit}
        >
          ENTRAR
        </Button>

        <Text
          textAlign="center"
          fontSize="sm"
          color="gray.300"
          marginTop={6}
        >
          Não tem uma conta? {" "}
          <Link
            color="red.600"
            fontWeight="bold"
            _hover={{ color: 'red.50  0' }}
            href="/user/register"
            _focus={{ border: 'none' }}
          >
            Registre-se
          </Link>
        </Text>

        <Divider />

        <Flex alignItems="center">
          <Text fontSize="sm">Ou entre com</Text>
          <Button
            height="50px"
            flex="1"
            backgroundColor="gray.600"
            marginLeft={6}
            borderRadius="sm"
            _hover={{ backgroundColor: 'red.500' }}
          >
            FACEBOOK
          </Button>
        </Flex>
      </Flex>
    </Grid>
  )
}