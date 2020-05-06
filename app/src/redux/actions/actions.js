import ActionTypes from './action-types'
export const userLoadded = (userInfo)=>(
    {
        type: ActionTypes.USER_LOADED,
        playload:{
            userInfo
        }
    }
)