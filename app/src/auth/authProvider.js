// jwt token decode to get user id, and make it as key, future changing
import jwtDecode from 'jwt-decode'
export default {
    // called when the user attempts to log in
    login: async ({ username,password }) => {
        const urlencoded = new URLSearchParams();
        urlencoded.append("username",username)
        urlencoded.append("password",password)
        urlencoded.append("grant_type","password")
        const requestOptions = {
            method: 'POST',
            body: urlencoded,
            headers: {
                 'Content-Type': 'application/x-www-form-urlencoded',
                 'Authorization': 'Basic ' + new Buffer(process.env.REACT_APP_CLIENT_ID + ':' + process.env.REACT_APP_CLIENT_SECRET).toString('base64')
                },
            redirect:'follow'
        };
        console.log(requestOptions);
        return fetch(`${process.env.REACT_APP_AUTH_URL}/oauth/token`,requestOptions)
            .then(response => {
                if (response.status < 200 || response.status >= 300) {
                    localStorage.removeItem('user_id');
                    localStorage.removeItem('access_token');
                    localStorage.removeItem('refresh_token');
                    throw new Error(response.statusText);
                }
                return response.json();
            })
            .then(({ access_token,refresh_token }) => {
                console.log(access_token)
                const decoded = jwtDecode(access_token);
                console.log(decoded);
                localStorage.setItem('user_id',decoded.user_name)
                console.log(localStorage.getItem('user_id'))
                localStorage.setItem('access_token', access_token);
                localStorage.setItem('refresh_token', refresh_token);
            });
    },
    // called when the user clicks on the logout button
    logout: () => {
        localStorage.removeItem('user_id')
        localStorage.removeItem('access_token');
        localStorage.removeItem('refresh_token');
        return Promise.resolve();
    },
    // called when the API returns an error
    checkError: ({ status }) => {
        if (status === 401 || status === 403) {
            console.log("check error")
            localStorage.removeItem('user_id');
            return Promise.reject();
        }
        return Promise.resolve();
    },
    // called when the user navigates to a new location, to check for authentication
    checkAuth: () => {
        console.log("check auth: "+!!localStorage.getItem('user_id'))
        return !!localStorage.getItem('user_id')
            ? Promise.resolve()
            : Promise.reject();
    },
    // called when the user navigates to a new location, to check for permissions / roles
    getPermissions: () => Promise.resolve(),

};
