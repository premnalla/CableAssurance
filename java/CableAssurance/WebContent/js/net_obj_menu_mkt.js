
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

	var startLeft = 3;

	var anchor;
	var width;
	
	width = 60;
	anchor = document.getElementById('aRefresh');
	anchor.style.left = startLeft;
	anchor.style.width = width;
	startLeft += width;
	
	width = 50;
	anchor = document.getElementById('aCmts');
	anchor.style.left = startLeft;
	anchor.style.width = width;
	startLeft += width;	
	
	width = 45;
	anchor = document.getElementById('aCms');
	anchor.style.left = startLeft;
	anchor.style.width = width;
	startLeft += width;	
	
	width = 65;
	anchor = document.getElementById('aReports');
	anchor.style.left = startLeft;
	anchor.style.width = width;
	startLeft += width;	
	
}
