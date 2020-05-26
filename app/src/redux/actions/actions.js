import ActionTypes from './action-types'
export const userLoadded = (userInfo)=>(
    {
        type: ActionTypes.USER_LOADED,
        payload:{
            userInfo
        }
    }
)

export const createNewGroup = (name) =>({
    type: ActionTypes.CREATE_GROUP,
    payload:{
        name
    }
})

export const loadUser =(name) =>({
    type: ActionTypes.LOAD_USER,
    payload:{
        name
    }
})

export const applyToBeDoctor =()=>({
    type:ActionTypes.APPLY_DOCTOR
})

export const joinGroup=(name)=>({
    type:ActionTypes.APPLY_JOIN_GROUP,
    payload:{name}
})