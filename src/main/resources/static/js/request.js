(function (win) {
	axios.default.headers["Content-Type"] = "application/json;charset=utf-8"
	const service = axios.creat({
		baseURL: "/",
		timeout: 10000
	})
	
	service.interceptors.response.use(
                function (response) {
                    // 如果响应状态码是 200，则直接返回响应数据
                    if (response.data.code === 0 && response.data.msg === "NOTLOGIN") {
                    	location.href = "http://localhost:8080/login.html";
                    } 
                    	return response.data;
                    
                },
                function(error) {
                	return Promise.reject(error);
                }
                
            )
})