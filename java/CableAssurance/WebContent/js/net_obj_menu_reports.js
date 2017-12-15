
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
	
	width = 95;
	anchor = document.getElementById('aReports');
	anchor.style.left = startLeft;
	anchor.style.width = width;
	startLeft += width;
	
}