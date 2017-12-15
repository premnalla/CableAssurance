/**
var mnuSelected = '';
var subMnuSelected = '';
function showMenu(menu){
	hideMenu(mnuSelected);
	document.getElementById(menu).style.visibility = 'visible';
	mnuSelected = menu;
}
function hideMenu(menu){
	if(mnuSelected!='')
		document.getElementById(menu).style.visibility = 'hidden';
}
function showSubMenu(menu){
	hideSubMenu(subMnuSelected);
	document.getElementById(menu).style.visibility = 'visible';
	subMnuSelected = menu;
}
function hideSubMenu(menu){
	if(subMnuSelected!='')
		document.getElementById(menu).style.visibility = 'hidden';
}
**/

/**
function init() 
{
	if(document.all) {
		aWidth = document.body.clientWidth;
		document.getElementById('menuBar').style.width = aWidth;
	} else {
		aWidth = innerWidth;
		document.getElementById('menuBar').style.width = aWidth-7;
	}
	document.getElementById('Home').style.left = parseInt((aWidth/2 - 200));
	document.getElementById('View').style.left = parseInt((aWidth/2 - 100));
	document.getElementById('Alarm').style.left = parseInt(aWidth/2);
	document.getElementById('CSR-Portal').style.left = parseInt((aWidth/2 + 100));
	document.getElementById('mnuHome').style.left = document.getElementById('Home').style.left;
	document.getElementById('mnuView').style.left = document.getElementById('View').style.left;
	document.getElementById('mnuAlarm').style.left = document.getElementById('Alarm').style.left;
	document.getElementById('mnuCsrPortal').style.left = document.getElementById('CSR-Portal').style.left;
}
**/


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
	anchor = document.getElementById('aRefresh');
	anchor.style.left = startLeft;
	anchor.style.width = 50;
	startLeft += 50;
	
	/**
	anchor = document.getElementById('aAlarm');
	anchor.style.left = startLeft;
	anchor.style.width = 50;
	startLeft += 50;
	**/

}
