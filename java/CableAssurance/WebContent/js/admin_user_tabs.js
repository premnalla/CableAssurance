
function myInit() 
{
	
	var startLeft = 50;
	var top = 10;

	var anchor;
	var itemWidth;
	
	itemWidth = 40;
	anchor = document.getElementById('aRole');
	anchor.style.left = startLeft;
	anchor.style.width = itemWidth;
	anchor.style.top = top;
	startLeft += itemWidth;
	
	itemWidth = 40;
	anchor = document.getElementById('aUser');
	anchor.style.left = startLeft;
	anchor.style.width = itemWidth;
	anchor.style.top = top;
	startLeft += itemWidth;
	
}
