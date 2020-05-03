import axios from 'axios'
export default {
    createNewUser: async ({username,password})=>{
        return await axios.post(
            `${process.env.REACT_APP_USER_URL}/api/create`,{
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
    }
}