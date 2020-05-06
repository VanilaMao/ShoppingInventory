import React from 'react';
import { UserMenu, MenuItemLink,AppBar} from 'react-admin';
import SettingsIcon from '@material-ui/icons/Settings';

const MenuView = ({...props})=>{
    return (
        <UserMenu label="Configuration" {...props}>
            <MenuItemLink
                to="/profile"
                primaryText="My profile"
                leftIcon={<SettingsIcon />}
            />
        </UserMenu>
    )

}


const CustomAppBar = props => <AppBar {...props} userMenu={<MenuView />} />;

export default CustomAppBar;