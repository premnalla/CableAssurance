
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
	var itemWidth;
	
	itemWidth = 60;
	anchor = document.getElementById('aRefresh');
	anchor.style.left = startLeft;
	anchor.style.width = itemWidth;
	startLeft += itemWidth;
	
	itemWidth = 80;
	anchor = document.getElementById('aChannels');
	anchor.style.left = startLeft;
	anchor.style.width = itemWidth;
	startLeft += itemWidth;
	
	itemWidth = 50;
	anchor = document.getElementById('aHfcs');
	anchor.style.left = startLeft;
	anchor.style.width = itemWidth;
	startLeft += itemWidth;
	
	itemWidth = 55;
	anchor = document.getElementById('aPlots');
	anchor.style.left = startLeft;
	anchor.style.width = itemWidth;
	startLeft += itemWidth;
	
	itemWidth = 55;
	anchor = document.getElementById('aAlarms');
	anchor.style.left = startLeft;
	anchor.style.width = itemWidth;
	startLeft += itemWidth;
	
	itemWidth = 65;
	anchor = document.getElementById('aReports');
	anchor.style.left = startLeft;
	anchor.style.width = itemWidth;
	startLeft += itemWidth;
	
}
