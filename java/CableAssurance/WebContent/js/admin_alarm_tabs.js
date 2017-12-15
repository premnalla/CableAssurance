
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
	
	itemWidth = 45;
	anchor = document.getElementById('aCmts');
	anchor.style.left = startLeft;
	anchor.style.width = itemWidth;
	anchor.style.top = top;
	startLeft += itemWidth;
	
	itemWidth = 35;
	anchor = document.getElementById('aCms');
	anchor.style.left = startLeft;
	anchor.style.width = itemWidth;
	anchor.style.top = top;
	startLeft += itemWidth;
	
	itemWidth = 35;
	anchor = document.getElementById('aHfc');
	anchor.style.left = startLeft;
	anchor.style.width = itemWidth;
	anchor.style.top = top;
	startLeft += itemWidth;
	
	itemWidth = 35;
	anchor = document.getElementById('aMta');
	anchor.style.left = startLeft;
	anchor.style.width = itemWidth;
	anchor.style.top = top;
	startLeft += itemWidth;
	
}
