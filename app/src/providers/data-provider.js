import offerService from '../services/offer-service'
import userService from '../services/user-service'
// https://stackoverflow.com/questions/50414523/react-admin-how-to-call-dataprovider-with-nested-path-like-abc-def
export default {
    getList: (resource, params)=>{
        switch(resource){
            case 'offer':
                return offerService.getList(params)
        }
    },
    getOne: (resource, params) =>{
        switch(resource){
            case 'profile':
                return userService.getOne(params)
        }
    },
    delete:(resource, params)=>{
        switch(resource){
            case 'profile':
                return userService.delete()
        }
    },
    update:(resource,params)=>{
        switch(resource){
            case 'profile':
                return userService.update(params)
        }
    }
}