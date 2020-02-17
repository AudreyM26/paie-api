package dev.paie.service;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import dev.paie.controller.BulletinSalaireController;
import dev.paie.entite.BulletinSalaire;
import dev.paie.entite.BulletinSalaireRequest;
import dev.paie.entite.Cotisation;
import dev.paie.repository.BulletinSalaireRepository;
import dev.paie.repository.CotisationRepository;

@Service
public class BulletinSalaireService {

	private static final Logger LOG = LoggerFactory.getLogger(BulletinSalaireController.class);

	private BulletinSalaireRepository bulletinSalaireRepository;
	private CotisationRepository cotisationRepository;

	public BulletinSalaireService(BulletinSalaireRepository bulletinSalaireRepository, CotisationRepository cotisationRepository) {
		super();
		this.bulletinSalaireRepository = bulletinSalaireRepository;
		this.cotisationRepository = cotisationRepository;
	}

	public List<BulletinSalaireRequest> listerBulletinsSalaire() {

		List<BulletinSalaire> listeBulletins = this.bulletinSalaireRepository.findAll();

		List<BulletinSalaireRequest> listeBulletinsPageIndex = new ArrayList<>();
		
		for (BulletinSalaire b : listeBulletins) {

			BulletinSalaireRequest bulletinReq = new BulletinSalaireRequest();

			BigDecimal salaireBase = b.getRemunerationEmploye().getGrade().getNbHeuresBase().multiply(b.getRemunerationEmploye().getGrade().getTauxBase());
			BigDecimal salaireBrut = salaireBase.add(b.getPrimeExceptionnelle());

			List<Integer> idCotisationsList = b.getRemunerationEmploye().getProfilRemuneration().getCotisations().stream()
												.filter(idCotisations->this.cotisationRepository.existsById(idCotisations.getId()))
												.map(c->c.getId())
												.collect(Collectors.toList());
			
			LOG.info("cotisat" + idCotisationsList);
			
			BigDecimal totalRetenuSalariale = this.cotisationRepository.sumTauxSalarial(false, idCotisationsList).multiply(salaireBrut);
			
			LOG.info("sum " + totalRetenuSalariale);

			BigDecimal netImposable = salaireBrut.subtract(totalRetenuSalariale);
			BigDecimal sumCotisImposable = this.cotisationRepository.sumTauxSalarial(true, idCotisationsList);
			BigDecimal netApayer = netImposable.subtract(sumCotisImposable.multiply(salaireBrut));

			bulletinReq.setId(b.getId());
			bulletinReq.setSalaireBase(salaireBase);
			bulletinReq.setSalaireBrut(salaireBrut);
			bulletinReq.setTotalRetenueSalariale(totalRetenuSalariale);
			bulletinReq.setNetImposable(netImposable);
			bulletinReq.setNetAPayer(netApayer);
			bulletinReq.setRemunerationEmploye(b.getRemunerationEmploye());
			bulletinReq.setPeriode(b.getPeriode());
			bulletinReq.setDateCreation(b.getDateCreation().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));

			listeBulletinsPageIndex.add(bulletinReq);
		}

		return listeBulletinsPageIndex;
	}
	
	
	public BulletinSalaireRequest rechercherBulletinSalaireParId(@RequestParam("id") Integer idRequeteHttp) {

		BulletinSalaire bulletin = this.bulletinSalaireRepository.findById(idRequeteHttp).orElseThrow(() -> new EntityNotFoundException("Bulletin non trouvé"));

		LOG.info("bulletin" + bulletin.getId());
		
		BulletinSalaireRequest bulletinPage = new BulletinSalaireRequest();

		BigDecimal salaireBase = bulletin.getRemunerationEmploye().getGrade().getNbHeuresBase().multiply(bulletin.getRemunerationEmploye().getGrade().getTauxBase());
		BigDecimal salaireBrut = salaireBase.add(bulletin.getPrimeExceptionnelle());
		
		List<Integer> idCotisationsList = bulletin.getRemunerationEmploye().getProfilRemuneration().getCotisations().stream()
				.filter(idCotisations->this.cotisationRepository.existsById(idCotisations.getId()))
				.map(c->c.getId())
				.collect(Collectors.toList());

		LOG.info("cotisat" + idCotisationsList);
		
		BigDecimal totalRetenuSalariale = this.cotisationRepository.sumTauxSalarial(false, idCotisationsList).multiply(salaireBrut);
		
		LOG.info("sum " + totalRetenuSalariale);

		BigDecimal netImposable = salaireBrut.subtract(totalRetenuSalariale);
		BigDecimal sumCotisImposable = this.cotisationRepository.sumTauxSalarial(true, idCotisationsList);
		BigDecimal netApayer = netImposable.subtract(sumCotisImposable.multiply(salaireBrut));

		bulletinPage.setId(bulletin.getId());
		bulletinPage.setSalaireBase(salaireBase);
		bulletinPage.setSalaireBrut(salaireBrut);
		bulletinPage.setTotalRetenueSalariale(totalRetenuSalariale);
		bulletinPage.setNetImposable(netImposable);
		bulletinPage.setNetAPayer(netApayer);
		bulletinPage.setRemunerationEmploye(bulletin.getRemunerationEmploye());
		bulletinPage.setPeriode(bulletin.getPeriode());

		bulletinPage.setDateCreation(bulletin.getDateCreation().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));

		return bulletinPage;
	}
}
