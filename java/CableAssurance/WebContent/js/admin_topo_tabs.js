
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
	
	itemWidth = 43;
	anchor = document.getElementById('aLocalSys');
	anchor.style.left = startLeft;
	anchor.style.width = itemWidth;
	anchor.style.top = top;
	startLeft += itemWidth;
	
	itemWidth = 55;
	anchor = document.getElementById('aRegion');
	anchor.style.left = startLeft;
	anchor.style.width = itemWidth;
	anchor.style.top = top;
	startLeft += itemWidth;
	
	itemWidth = 55;
	anchor = document.getElementById('aMarket');
	anchor.style.left = startLeft;
	anchor.style.width = itemWidth;
	anchor.style.top = top;
	startLeft += itemWidth;
	
	itemWidth = 50;
	anchor = document.getElementById('aBlade');
	anchor.style.left = startLeft;
	anchor.style.width = itemWidth;
	anchor.style.top = top;
	startLeft += itemWidth;
		
}
