
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
	
	itemWidth = 57;
	anchor = document.getElementById('aGeneral');
	anchor.style.left = startLeft;
	anchor.style.width = itemWidth;
	anchor.style.top = top;
	startLeft += itemWidth;
	
	itemWidth = 50;
	anchor = document.getElementById('aAlarms');
	anchor.style.left = startLeft;
	anchor.style.width = itemWidth;
	anchor.style.top = top;
	startLeft += itemWidth;
	
	itemWidth = 85;
	anchor = document.getElementById('aPerf');
	anchor.style.left = startLeft;
	anchor.style.width = itemWidth;
	anchor.style.top = top;
	startLeft += itemWidth;
	
	itemWidth = 66;
	anchor = document.getElementById('aTopo');
	anchor.style.left = startLeft;
	anchor.style.width = itemWidth;
	anchor.style.top = top;
	startLeft += itemWidth;
	
	itemWidth = 45;
	anchor = document.getElementById('aCmts');
	anchor.style.left = startLeft;
	anchor.style.width = itemWidth;
	anchor.style.top = top;
	startLeft += itemWidth;
	
	itemWidth = 40;
	anchor = document.getElementById('aCms');
	anchor.style.left = startLeft;
	anchor.style.width = itemWidth;
	anchor.style.top = top;
	startLeft += itemWidth;
	
	itemWidth = 45;
	anchor = document.getElementById('aUser');
	anchor.style.left = startLeft;
	anchor.style.width = itemWidth;
	anchor.style.top = top;
	startLeft += itemWidth;
	
}
