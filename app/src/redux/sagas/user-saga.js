import {call, put,takeEvery} from 'redux-saga/effects'
import ActionTypes from '../actions/action-types'
import userService from '../../services/user-service'
import {showNotification} from 'react-admin'

function* loadUser(){
    try{    
        const user = yield call(userService.getUserInfo);
        console.log(user.data)
        yield put({type:ActionTypes.USER_LOADED,payload:{user}})
    }catch(err){
        console.log(err.response.data);
        yield put(showNotification('Cannot load the user, please connact the administration','error')); 
    }
}

function* createGroup(action){
    try{
        const group = yield call(userService.createNewGroup,action.payload);
        yield put({type: ActionTypes.GROUP_CREATE_SUCCEEDED,payload:{group}})
    }catch(e){
        yield put(showNotification(`${e.response.data}, Cannot create the group :${action.payload.name}, please connact the administration`,'error')); 
    }
}


function* applyToBeDoctor(){
    try{
        yield call(userService.applyToBeDoctor);
        yield put({type: ActionTypes.APPLY_DOCTOR_SUCCEEDED})
    }catch(e){
        console.log("something")
        yield put(showNotification('Can not Apply To Be a Doctor,  please contact the administration','error'));
    }
}

function* joinGroup(action){
    try{
        const group = yield call(userService.joinGroup,action.payload);
        yield put({type: ActionTypes.APPLY_JOIN_GROUP_SUCCEEDED,payload:{group}})
    }catch(e){
        yield put(showNotification(`${e.response.data}, Can not Join in the Group: ${action.payload.name},  please contact the administration`,'error'));
    }
}



export default function* userSaga(){
    yield takeEvery(ActionTypes.CREATE_GROUP, createGroup);
    yield takeEvery(ActionTypes.LOAD_USER, loadUser);
    yield takeEvery(ActionTypes.APPLY_DOCTOR, applyToBeDoctor);
    yield takeEvery(ActionTypes.APPLY_JOIN_GROUP, joinGroup);
}