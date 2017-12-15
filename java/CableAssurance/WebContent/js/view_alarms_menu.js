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

/**
function init(){
	if(document.all){
		aWidth = document.body.clientWidth;
		document.getElementById('menuBar').style.width = aWidth;
	}else{
		aWidth = innerWidth;
		document.getElementById('menuBar').style.width = aWidth-7;
	}
	document.getElementById('About').style.left = parseInt((aWidth/2 - 200));
	document.getElementById('Services').style.left = parseInt((aWidth/2 - 100));
	document.getElementById('Samples').style.left = parseInt(aWidth/2);
	document.getElementById('Contact').style.left = parseInt((aWidth/2 + 100));
	document.getElementById('mnuAbout').style.left = document.getElementById('About').style.left;
	document.getElementById('mnuServices').style.left = document.getElementById('Services').style.left;
	document.getElementById('mnuSamples').style.left = document.getElementById('Samples').style.left;
	document.getElementById('mnuContact').style.left = document.getElementById('Contact').style.left;
	document.getElementById('mnuAboutSub1').style.left = parseInt(document.getElementById('About').style.left) + 100;
}
**/

function myInit() 
{
	var startLeft = 0;
	var anchor;
	var anchor2;
	
	if(document.all) {
		aWidth = document.body.clientWidth;
	} else {
		aWidth = document.documentElement.clientWidth;
	}
	
	anchor = document.getElementById('menuBar');
	anchor.style.width = aWidth-startLeft-8;
	anchor.style.left = startLeft;
	
	/*
	var menuItem = document.getElementById('menuItem');
	*/
	
	anchor = document.getElementById('Refresh');
	aHomeWidth = 55;
	anchor.style.left = startLeft;
	anchor.style.width = aHomeWidth;
	startLeft += aHomeWidth;
	
	anchor = document.getElementById('Alarms');
	aAlarmsWidth = 65;
	anchor.style.left = startLeft;
	anchor.style.width = aAlarmsWidth;	
	anchor2 = document.getElementById('mnuAlarms');
	anchor2.style.left = anchor.style.left;
	anchor2.style.width = aAlarmsWidth;	
	anchor2.style.top = 23;	
	startLeft += aAlarmsWidth;
	
	/*
	anchor = document.getElementById('TCAs');
	aTCAsWidth = 50;
	anchor.style.left = startLeft;
	anchor.style.width = aTCAsWidth;
	startLeft += aTCAsWidth;
	*/
	
}
