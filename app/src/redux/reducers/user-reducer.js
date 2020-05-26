import ActionTypes from '../actions/action-types'
import {DoctorGroupInfo, GroupInfo} from '../../domains/group-info'
const inintialState ={
    userInfo:{
        zipcode:"48197",
        state:'',
        status:'pending', //user's status in system
        name:"Guest"
    },
    roles:['Guest'],
    doctorGroups:[], // for doctor
    userGroups:[] //for user
}

const reducer =  (initState = inintialState, action ) =>{
    if(action.type === ActionTypes.USER_LOADED){
        return {
            ...initState,
            userInfo: {
                ...initState.userInfo,
                zipcode: action.payload.user.zipcode,
                state: action.payload.user.state,               
                name:action.payload.user.name,
                status:action.payload.user.status
            },
            doctorGroups: action.payload.user.doctorGroups.map(x=>new DoctorGroupInfo(x)),
            userGroups:action.payload.user.userGroups.map(x=>new GroupInfo(x)),
            roles: action.payload.user.roles
        }
    }
    if(action.type ===ActionTypes.GROUP_CREATE_SUCCEEDED){
        return {
            ...initState,
            doctorGroups:[...initState.doctorGroups,new DoctorGroupInfo(action.payload.group)]
        }
    }
    if(action.type===ActionTypes.APPLY_DOCTOR_SUCCEEDED){
        return {
            ...initState,
            roles:[...initState.roles,'Doctor']
        }
    }
    if(action.type ===ActionTypes.APPLY_JOIN_GROUP_SUCCEEDED){
        return {
            ...initState,
            userGroups: [...initState.userGroups,new GroupInfo(action.payload.group)]
        }
    }
    return initState;
};

export default reducer;

