window.onload= function(){
    var personaldata = window.PersonalData.getPersonalData();
    if(personaldata)
    {
        var Personaldata = document.getElementById("Personaldata");
	pnode = document.createElement("p");
        tnode = document.createTextNode("ID:" + personaldata.getID());
        pnode.appendChild(tnode);
        Personaldata.appendChild(pnode);
	pnode = document.createElement("p");
        tnode = document.createTextNode("Name:" + personaldata.getName());
        pnode.appendChild(tnode);
        Personaldata.appendChild(pnode);
	pnode = document.createElement("p");
        tnode = document.createTextNode("Age:" + personaldata.getAge());
        pnode.appendChild(tnode);
        Personaldata.appendChild(pnode);
	pnode = document.createElement("p");
        tnode = document.createTextNode("Blog:" + personaldata.getBlog());
        pnode.appendChild(tnode);
        Personaldata.appendChild(pnode);
    }    
}