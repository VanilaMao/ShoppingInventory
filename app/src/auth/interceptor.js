// https://stackoverflow.com/questions/44982412/how-do-i-check-for-token-expiration-and-logout-user > midle ware maybe better

import axios from 'axios'

axios.interceptors.response.use(response=>{
    return response;
},err=>{
    return new Promise((resolve,reject)=>{
        const originalReq = err.config;
        if(err.response.status == 401 && originalReq && !originalReq.__isRetryRequest ){
            originalReq._retry = true;
            const urlencoded = new URLSearchParams();
            urlencoded.append('grant_type','refresh_token');
            urlencoded.append('refresh_token',localStorage.getItem("refresh_token"));
            const request = new Request(`${process.env.REACT_APP_AUTH_URL}/oauth/token`,{
                method: 'POST',
                // mode: 'cors',
                cache: 'no-cache',
                credentials: 'same-origin',
                headers:{
                    'Content-Type': 'application/x-www-form-urlencoded',
                    'Authorization': 'Basic ' + new Buffer(process.env.REACT_APP_CLIENT_ID + ':' + process.env.REACT_APP_CLIENT_SECRET).toString('base64')
                },
                redirect: 'follow',
                referrer: 'no-referrer',
                body: urlencoded
            });
            let res = fetch(request)
                .then(res=>res.json()).then(res=>{
                    console.log(res);
                    localStorage.setItem("access_token",res.access_token);
                    localStorage.setItem("refresh_token",res.refresh_token);
                    return axios(originalReq)
            })
            resolve(res)
        }
        return Promise.reject(err)
    })
}) 