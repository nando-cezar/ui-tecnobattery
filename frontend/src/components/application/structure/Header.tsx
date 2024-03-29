import { AddIcon, EditIcon, ExternalLinkIcon, HamburgerIcon, RepeatIcon } from '@chakra-ui/icons';
import { Center, Flex, Grid, IconButton, Menu, MenuButton, MenuItem, MenuList, Spacer } from '@chakra-ui/react';
import React from 'react';
import styled from 'styled-components'
import ModalConfig from '../modal/ModalConfig'
import ModalHelp from '../modal/ModalHelp'

import router from 'next/router'
import ColorMode from '../actions/ColorMode'
import { ExitToApp } from '@material-ui/icons';

const NavBar = styled.nav`      
    background-color:transparent;
    width: 100%;
    height: 60px;
    padding: 8px;
`;

const Header: React.FC = () => {
  
  const handleClose = () => {
    router.push('/application/user/authentication')
    localStorage.removeItem('Authorization')
  }

  return (
    <NavBar>
      <Flex>
        <Center>
          <Menu>
            <MenuButton
              as={IconButton}
              aria-label="Options"
              icon={<HamburgerIcon />}
              size="md"
              variant="outline"
            />
            <MenuList>
              <MenuItem icon={<AddIcon />} command="⌘T">
                New Tab
              </MenuItem>
              <MenuItem icon={<ExternalLinkIcon />} command="⌘N">
                New Window
              </MenuItem>
              <MenuItem icon={<RepeatIcon />} command="⌘⇧N">
                Open Closed Tab
              </MenuItem>
              <MenuItem icon={<EditIcon />} command="⌘O">
                Open File...
              </MenuItem>
            </MenuList>
          </Menu>
        </Center>
        <Spacer />
        <Grid templateColumns="repeat(4, 1fr)" gap={2}>
          <ColorMode />
          <ModalHelp />
          <ModalConfig />
          <IconButton
            size="md"
            variant="outline"
            aria-label="Options"
            icon={<ExitToApp />}
            onClick={handleClose}
          />
        </Grid>
      </Flex>
    </NavBar>
  );
}

export default Header




