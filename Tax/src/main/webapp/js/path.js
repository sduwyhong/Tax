var path = '/Tax/api/v1/';
function getUserIdFromCookie(){
	var cookies = document.cookie;
	var cookieArray = cookies.split(';');
	for(var i in cookieArray){
		if(cookieArray[i].split('=')[0].trim() == '_user'){
			var value = cookieArray[i].split('=')[1].split('%3B');
			return value[0];
		}
	}
}
function isLogin(){
	var cookies = document.cookie;
	var cookieArray = cookies.split(';');
	for(var i in cookieArray){
		if(cookieArray[i].split('=')[0].trim() == '_user'){
			return true;
		}
	}
	return false;
}
function getIdFromURL(){
	return location.search.substr(1,location.search.length-1).split('=')[1];
}
