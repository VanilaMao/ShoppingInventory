import React from 'react'
import {Edit, TextInput, SimpleForm, required } from 'react-admin'

const ProfileEdit =({ staticContext, ...props })=>{
    console.log(props)
    const user_id = localStorage.getItem('user_id');
    return (
        <Edit 
            resource="profile"
            redirect={false}
            basePath="/profile"
            title="User Settings"
            id={user_id}
            {...props}>
                <SimpleForm>
                    <TextInput source='zipcode' validate={required()} />
                </SimpleForm>
        </Edit>
    );
}
export default ProfileEdit;