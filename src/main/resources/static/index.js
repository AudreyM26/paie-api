/**
 * fonction pour afficher la liste des bulletins et le bulletin de salaire d un employe (selon id bulletin)
 */

function afficheListeBulletins(){
	
	fetch('http://localhost:8080/bulletins')
    .then(function (response) {
        // traiter la réponse
        if (!response.ok) {
            throw Error(response.statusText);
        }
        // lecture du corps de la réponse en tant que JSON.
        return response.json();
    })
    .then(function (responseAsJson) {
        // traitement de l'objet
        console.log(responseAsJson);
        var cpt = 0;

        var listeBulletins = responseAsJson.map(function(element){
            return element;
        });
        console.log(listeBulletins);
        
       for (var prop in listeBulletins) {
        	
           
            var classColorRow = "";
            if (cpt % 2 === 0) {
                classColorRow = " bg-light";
            }

            var divColDate = document.createElement('div');
            divColDate.textContent = listeBulletins[prop].dateCreation;
            divColDate.setAttribute('class', 'col-2 font-weight-normal');

            var divColPeriode = document.createElement('div');
            divColPeriode.textContent = listeBulletins[prop].periode.dateDebut+" "+listeBulletins[prop].periode.dateFin;
            divColPeriode.setAttribute('class', 'col-2');

            var divColMatricule = document.createElement('div');
            divColMatricule.textContent = listeBulletins[prop].remunerationEmploye.matricule;
            divColMatricule.setAttribute('class', 'col-1');

            var divColSalaireBrut = document.createElement('div');
            divColSalaireBrut.textContent = listeBulletins[prop].salaireBrut;
            divColSalaireBrut.setAttribute('class', 'col-1');
           
            var divColSalaireBase = document.createElement('div');
            divColSalaireBase.textContent = listeBulletins[prop].salaireBase;
            divColSalaireBase.setAttribute('class', 'col-2');
            
            var divColNetImposable = document.createElement('div');
            divColNetImposable.textContent = listeBulletins[prop].netImposable;
            divColNetImposable.setAttribute('class', 'col-2');
            
            var divColNetApayer = document.createElement('div');
            divColNetApayer.textContent = listeBulletins[prop].netAPayer;
            divColNetApayer.setAttribute('class', 'col-1');
            
            var divColActions = document.createElement('div');
            divColActions.innerHTML = '<a href="bulletin.html?id='+listeBulletins[prop].id+'">Visualiser</a>';
            divColActions.setAttribute('class', 'col-1');
            
            var divRow = document.createElement('div');
            divRow.setAttribute('class', 'row ml-0 small' + classColorRow);
            divRow.appendChild(divColDate);
            divRow.appendChild(divColPeriode);
            divRow.appendChild(divColMatricule);
            divRow.appendChild(divColSalaireBrut);
            divRow.appendChild(divColSalaireBase);
            divRow.appendChild(divColNetImposable);
            divRow.appendChild(divColNetApayer);
            divRow.appendChild(divColActions);
            document.getElementById('liste_bulletins').appendChild(divRow);

            cpt = cpt + 1;
        }


    })
    .catch(function (error) {
        console.log('Il semble avoir un souci avec l affichage des bulletins...', error);
    });
	
	
}

function afficheBulletinSalaire(){

	var param = location.search.substring(1).split('?');
	var idVal = 0;
	//console.log(param);
	
	if(param[0] ==""){
		window.location.href="index.html";
	}else{
		var idVal = param[0].split('=')[1];
	}
	

	if(idVal > 0){
		fetch('http://localhost:8080/bulletins?id='+idVal)
	    .then(function (response) {
	        // traiter la réponse
	        if (!response.ok) {
	            throw Error(response.statusText);
	        }
	        // lecture du corps de la réponse en tant que JSON.
	        return response.json();
	    })
	    .then(function (responseAsJson) {
	        // traitement de l'objet
	        console.log(responseAsJson);
	        var cpt = 0;

	        var bulletin = responseAsJson;
	        console.log(bulletin);
	        
	        document.getElementById("nom_entreprise").innerHTML = bulletin.remunerationEmploye.entreprise.denomination;
	        document.getElementById("siret_entreprise").innerHTML = bulletin.remunerationEmploye.entreprise.siret;
	        document.getElementById("matricule").innerHTML = bulletin.remunerationEmploye.matricule;
	        
	        var tabDateDebut = bulletin.periode.dateDebut.split("-");
	        var datDebutFormat = tabDateDebut[2]+"/"+tabDateDebut[1]+"/"+tabDateDebut[0];
	       
	        var tabDateFin = bulletin.periode.dateFin.split("-");
	        var datFinFormat = tabDateFin[2]+"/"+tabDateFin[1]+"/"+tabDateFin[0];
	        
	        document.getElementById("periode").innerHTML = "Du "+datDebutFormat+" au "+datFinFormat;
	        
	        
	       for (var prop in bulletin) {
	        	
	           
	    

	            cpt = cpt + 1;
	        }


	    })
	    .catch(function (error) {
	        console.log('Il semble avoir un souci avec l affichage du bulletin...', error);
	    });
		
		
		
	}
	
	
}