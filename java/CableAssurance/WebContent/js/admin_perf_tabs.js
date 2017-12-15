
function myInit() 
{
	
	/**
	if(document.all) {
		aWidth = document.body.clientWidth;
		document.getElementById('menuBar').style.width = aWidth;
	} else {
		aWidth = document.documentElement.clientWidth;
		document.getElementById('menuBar').style.width = aWidth;
	}
	
	var menuItem = document.getElementById('menuItem');
	**/

	var startLeft = 50;
	var top = 10;

	var anchor;
	var itemWidth;
	
	itemWidth = 40;
	anchor = document.getElementById('aCm');
	anchor.style.left = startLeft;
	anchor.style.width = itemWidth;
	anchor.style.top = top;
	startLeft += itemWidth;
		
}
