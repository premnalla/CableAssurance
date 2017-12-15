function myInit() 
{
	var startLeft = 300;
	var anchor;

	if(document.all) {
		aWidth = document.body.clientWidth;
	} else {
		aWidth = document.documentElement.clientWidth;
	}
	
	anchor = document.getElementById('menuBar');
	anchor.style.width = aWidth-startLeft-1;
	anchor.style.left = startLeft;
	
	/*
	var menuItem = document.getElementById('menuItem');
	*/
	
	anchor = document.getElementById('aHome');
	aHomeWidth = 55;
	anchor.style.left = startLeft;
	anchor.style.width = aHomeWidth;
	startLeft += aHomeWidth;
	
	anchor = document.getElementById('aAlarms');
	aAlarmsWidth = 60;
	anchor.style.left = startLeft;
	anchor.style.width = aAlarmsWidth;
	startLeft += aAlarmsWidth;
	
	anchor = document.getElementById('aReports');
	aReportsWidth = 65;
	anchor.style.left = startLeft;
	anchor.style.width = aReportsWidth;
	startLeft += aReportsWidth;
	
	anchor = document.getElementById('aCsrPortal');
	aCsrPortalWidth = 85;
	anchor.style.left = startLeft;
	anchor.style.width = aCsrPortalWidth;
	startLeft += aCsrPortalWidth;
	
	anchor = document.getElementById('aUserPref');
	aUserPrefWidth = 107;
	anchor.style.left = startLeft;
	anchor.style.width = aUserPrefWidth;
	startLeft += aUserPrefWidth;
	
	anchor = document.getElementById('aLogout');
	aLogoutWidth = 70;
	anchor.style.left = startLeft;
	anchor.style.width = aLogoutWidth;
	startLeft += aLogoutWidth;
	
}
