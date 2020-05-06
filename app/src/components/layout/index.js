import React from 'react';
import { Layout,Sidebar } from 'react-admin';
import CustomAppbar from './custom-appbar';


const CustomSidebar = props => <Sidebar {...props} size={200} />;
const AppLayout = props => <Layout 
                            {...props} 
                            appBar={CustomAppbar} 
                            sidebar = {CustomSidebar}
                        />;

export default AppLayout;
