import {Card,Button,TextField,CardActions} from '@material-ui/core'
import {Field, withTypes} from 'react-final-form'
import {Notification} from 'react-admin'
import User from '../domains/user'
import { useTranslate, useLogin, useNotify } from 'ra-core';
import React, { useState } from 'react'
import './login.css'

const {Form} = withTypes(User)

const RenderInput = ({
    meta: { touched, error } = { touched: false, error: undefined },
    input: { ...inputProps },
    ...props
}) => {
    return (
        <TextField
            error={!!(touched && error)}
            helperText={touched && error}
            {...inputProps}
            {...props}
            fullWidth
        />
    );
}
    



const RenderComponent = ({handleSubmit,translate, switchToNewUser, newUser})=>(
    <form onSubmit={handleSubmit} noValidate >
        <div className="main">
            <Card className="card">
                {!newUser?<div> Please Login </div>:<div>Create a new user</div>}
                <div className="form">
                    <div >
                        <Field 
                            className="input"
                            autoFocus 
                            name="username"
                            component={RenderInput}
                            label={translate('ra.auth.username')}
                        />
                    </div>
                    <div>
                        <Field 
                            className="input"
                            name="password"
                            component={RenderInput}
                            label={translate('ra.auth.password')}
                            type="password"                           
                            />
                    </div>    
                    {newUser&&  <div>
                        <Field 
                            className="input"
                            name="repeatPassword"
                            component={RenderInput}
                            label={translate('ra.auth.password')}
                            type="password"                           
                            />
                    </div>    }               
                </div>
                <CardActions>
                    <Button type="submit" variant="contained" color="primary" fullWidth>
                        {newUser?"SIGN UP":translate('ra.auth.sign_in')}
                    </Button>
                </CardActions>
                {newUser?<div>
                    Go Back to
                    <span onClick={()=>switchToNewUser(false)} className="switch"> Log In</span>
                </div>
                :<div>
                    Need a new user? Please 
                    <span onClick={()=>switchToNewUser(true)} className="switch"> Sign Up</span>
                </div>}
            </Card>
            <Notification />
        </div>

    </form>
)



const Login = ()=>{
    const translate = useTranslate()
    const notify = useNotify()
    const login = useLogin()
    const [newUser, setNewUser] = useState(false)

    const validate = (user) => {
        console.log(user)
        const errors = {};
        if (!user.username) {
            errors.username = translate('ra.validation.required');
        }
        if (!user.password) {
            errors.password = translate('ra.validation.required');
        }
        return errors;
    };


    return (
        <Form 
            onSubmit = {()=>{}}
            validate = {validate}
            render = {(props)=><RenderComponent 
                {...props}                
                translate={translate}
                switchToNewUser ={(newUser)=>setNewUser(newUser)}
                newUser ={newUser}
                />}
        >
        </Form>
    )
}

export default Login;