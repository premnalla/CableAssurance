function myInit() 
{
	if(document.all) {
		aWidth = document.body.clientWidth;
		document.getElementById('menuBar').style.width = aWidth;
	} else {
		aWidth = document.documentElement.clientWidth;
		document.getElementById('menuBar').style.width = aWidth;
	}
	
	var menuItem = document.getElementById('menuItem');
	
	var startLeft = 300;

	var anchor;
	anchor = document.getElementById('aHome');
	anchor.style.left = startLeft;
	anchor.style.width = 40;
	startLeft += 40;
	anchor = document.getElementById('aUserPref');
	anchor.style.left = startLeft;
	anchor.style.width = 95;
	startLeft += 95;
	anchor = document.getElementById('aLogout');
	anchor.style.left = startLeft;
	anchor.style.width = 60;
	startLeft += 60;
	
}
