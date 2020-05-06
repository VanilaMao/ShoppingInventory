// https://stackoverflow.com/questions/44982412/how-do-i-check-for-token-expiration-and-logout-user > midle ware maybe better
// https://medium.com/swlh/handling-access-and-refresh-tokens-using-axios-interceptors-3970b601a5da
import axios from 'axios'

const axiosInstance = axios.create();

axiosInstance.interceptors.request.use(config=>{
    const token = localStorage.getItem('access_token');
    if(token){
        config.headers['Authorization'] = 'Bearer ' + token;
    }
    return config;
    },
    error=>{
        Promise.reject(error)
    }   
)

// axiosInstance.interceptors.response.use(response=>{
//     return response;
// },err=>{
//     return new Promise((resolve,reject)=>{
//         const originalReq = err.config;
//         // cannot retry infinitely, need to identify unlogin user
//         if(err.response.status == 401 && originalReq && !originalReq._retry){
//             originalReq._retry = true;
//             const urlencoded = new URLSearchParams();
//             urlencoded.append('grant_type','refresh_token');
//             urlencoded.append('refresh_token',localStorage.getItem("refresh_token"));
//             const request = new Request(`${process.env.REACT_APP_AUTH_URL}/oauth/token`,{
//                 method: 'POST',
//                 cache: 'no-cache',
//                 credentials: 'same-origin',
//                 headers:{
//                     'Content-Type': 'application/x-www-form-urlencoded',
//                     'Authorization': 'Basic ' + new Buffer(process.env.REACT_APP_CLIENT_ID + ':' + process.env.REACT_APP_CLIENT_SECRET).toString('base64')
//                 },
//                 redirect: 'follow',
//                 referrer: 'no-referrer',
//                 body: urlencoded
//             });
//             let res = fetch(request)
//                 .then(res=>res.json()).then(res=>{
//                     console.log(res);
//                     localStorage.setItem("access_token",res.access_token);
//                     localStorage.setItem("refresh_token",res.refresh_token);
//                     return axiosInstance(originalReq)
//             })
//             resolve(res)
//         }
//         reject(err)
//     })
// }) 

export default axiosInstance;