import React from 'react';
import { Layout,Sidebar,Notification } from 'react-admin';
import CustomAppbar from './custom-appbar';


const CustomSidebar = props => <Sidebar {...props} size={200} />;
const AppLayout = props => <div>
                                <Layout 
                                {...props} 
                                appBar={CustomAppbar} 
                                sidebar = {CustomSidebar}/>
                                <Notification/> 
                            </div>;              
export default AppLayout;
