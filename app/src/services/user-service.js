import axios from 'axios' // general axios without interceptor
import axiosWrapper from '../providers/axios-wrapper'
import { HttpError } from 'react-admin';
export default {
    getUserInfo: async ()=>{
        return axiosWrapper({
            method:'get',
            url: `${process.env.REACT_APP_USER_URL}/api/user-info`,
        }).then(res=>res.data)
    },

    joinGroup: async ({name})=>{
        return axiosWrapper({
            method:'post',
            url: `${process.env.REACT_APP_USER_URL}/api/group/join`,
            headers: {
                'Content-Type': 'application/json'
            },
            data: name
        }).then(res=>res.data)
    },

    applyToBeDoctor: async ()=>{
        return axiosWrapper({
            method:'post',
            url: `${process.env.REACT_APP_USER_URL}/api/user/apply-doctor`
        });
    },

    createNewGroup: async ({name})=>{
        console.log(name)
        return axiosWrapper({
            method:'post',
            url: `${process.env.REACT_APP_USER_URL}/api/group/create`,
            headers: {
                'Content-Type': 'application/json'
            },
            data: name
        }).then(res=>res.data)
    },

    createNewUser: async ({username,password})=>{
        return await axios.post(
            `${process.env.REACT_APP_USER_URL}/api/user/create`,{
                username:username,
                password: password,
                zipcode: "48197"
            }).then(response=>{
                console.log(response);
                return true;
            }).catch(error=>{
                console.log(error);
                return false;
            });
    },
    getList: async (params)=>{
        const { page, perPage } = params.pagination;
        const { field, order } = params.sort;
        const query = {
            sort: JSON.stringify([field, order]),
            range: JSON.stringify([(page - 1) * perPage, page * perPage - 1]),
            filter: JSON.stringify(params.filter),
        };
        return axiosWrapper({
            method: 'post',
            url: `${process.env.REACT_APP_USER_URL}/api/list`,
            data: query
        }).then(response=>response.data).catch(err=>{
            return new Promise.reject(new HttpError(err.message,err.response.status,err.response.data))
        })
    },
    getOne: (params)=>{
        console.log('user get one')
        return axiosWrapper(
            {
                method:'get',
                url: `${process.env.REACT_APP_USER_URL}/api/user`
            }
        ).then(response=>{
            console.log(response.data)
            return Promise.resolve({
                data:response.data
            })

        }).catch(err=>{
            console.log(err.toJSON())
            return  Promise.resolve({
                data:{
                   
                }
            })
        });
    },
    delete: ()=>{
        return axiosWrapper({
                method:'post',
                url: `${process.env.REACT_APP_USER_URL}/api/user/delete`
        }).then(res=>({
            data:{}
        })).catch(err=>{
            console.log(err.message)
        })
    },
    update: (params)=>{
        return axiosWrapper({
                method:'post',
                url: `${process.env.REACT_APP_USER_URL}/api/user/update`,
                data: params.data
        }).catch(err=>{
            console.log(err.message)
            return Promise.resolve({
                data: params.previousData
            })
        })
    }
}