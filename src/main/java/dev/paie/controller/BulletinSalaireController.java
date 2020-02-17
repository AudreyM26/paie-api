package dev.paie.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.paie.entite.BulletinSalaireRequest;
import dev.paie.service.BulletinSalaireService;

@RestController
@RequestMapping(value = "/bulletins")
public class BulletinSalaireController {

	private BulletinSalaireService bulletinSalaireService;

	public BulletinSalaireController(BulletinSalaireService bulletinSalaireService) {
		super();
		this.bulletinSalaireService = bulletinSalaireService;
	}

	// Cette méthode est exécutée lorsqu'une requête GET /bulletins est reçue
	@GetMapping
	public List<BulletinSalaireRequest> listerBulletinsSalaire() {
		return this.bulletinSalaireService.listerBulletinsSalaire();
	}

	// GET /bulletins avec id en parametre
	@GetMapping(params = "id")
	public BulletinSalaireRequest rechercherBulletinSalaireParId(@RequestParam("id") Integer idRequeteHttp) {
		return this.bulletinSalaireService.rechercherBulletinSalaireParId(idRequeteHttp);
	}

}
