
// https://stackoverflow.com/questions/45591594/fetch-does-not-send-headers
// https://stackoverflow.com/questions/42874351/spring-boot-enabling-cors-by-application-properties
// https://stackoverflow.com/questions/37077487/enable-cors-for-health-endpoint-in-spring-boot-actuator
// http://zetcode.com/springboot/cors/
// https://spring.io/guides/gs/rest-service-cors/
// jwt token decode to get user id, and make it as key, future changing
export default {
    // called when the user attempts to log in
    login: async ({ username,password }) => {
        localStorage.setItem('username', username);
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
                    localStorage.removeItem('username');
                    localStorage.removeItem('access_token');
                    localStorage.removeItem('refresh_token');
                    throw new Error(response.statusText);
                }
                return response.json();
            })
            .then(({ access_token,refresh_token }) => {
                console.log(access_token)
                localStorage.setItem('access_token', access_token);
                localStorage.setItem('refresh_token', refresh_token);
            });
    },
    // called when the user clicks on the logout button
    logout: () => {
        localStorage.removeItem('username');
        localStorage.removeItem('access_token');
        localStorage.removeItem('refresh_token');
        return Promise.resolve();
    },
    // called when the API returns an error
    checkError: ({ status }) => {
        if (status === 401 || status === 403) {
            localStorage.removeItem('username');
            return Promise.reject();
        }
        return Promise.resolve();
    },
    // called when the user navigates to a new location, to check for authentication
    checkAuth: () => {
        // if logged, or using a boolean variable
        return localStorage.getItem('username')
            ? Promise.resolve()
            : Promise.reject();
    },
    // called when the user navigates to a new location, to check for permissions / roles
    getPermissions: () => Promise.resolve(),

};
