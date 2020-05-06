import React from 'react';
import logo from './logo.svg';
import './App.css';
import {Admin,Resource} from "react-admin"
import AuthProvider from "./auth/authProvider"
import Dashboard from "./components/dashboard/dashboard"
import dataProvider from './providers/data-provider'
import Login from './auth/login'
import {Route} from 'react-router-dom'
import Profile from './components/profile'
import AppLayout from './components/layout'

const App = ()=>(
  <Admin
       dataProvider ={dataProvider }
       authProvider={AuthProvider}
       dashboard={Dashboard}
       loginPage={Login}
       appLayout = {AppLayout}
       customRoutes={[
          <Route 
            key="profile"
            path="/profile"
            component={Profile.edit}
            />
         
       ]}
  >
    <Resource name="doctor"/>
    <Resource name = "profile"/>
      
  </Admin>

);

export default App;
