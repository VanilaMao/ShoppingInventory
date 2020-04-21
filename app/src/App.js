import React from 'react';
import logo from './logo.svg';
import './App.css';
import {Admin,Resource} from "react-admin"
import AuthProvider from "./auth/authProvider"
import Dashboard from "./components/dashboard"
import simpleRestProvider from 'ra-data-simple-rest';
import Login from './auth/login'

const App = ()=>(
  <Admin
       dataProvider ={simpleRestProvider('http://path.to.my.api/') }
       authProvider={AuthProvider}
       dashboard={Dashboard}
       loginPage={Login}
  >
    <Resource name="Doctor">

    </Resource>
      
  </Admin>

);

export default App;
