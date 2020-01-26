package dev.paie.controller;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dev.paie.entite.BulletinSalaire;
import dev.paie.entite.BulletinSalaireRequest;
import dev.paie.entite.Cotisation;
import dev.paie.entite.Periode;
import dev.paie.repository.BulletinSalaireRepository;
import dev.paie.repository.CotisationRepository;
import dev.paie.repository.PeriodeRepository;

@RestController
@RequestMapping(value = "/bulletins")
public class BulletinSalaireController {

	private static final Logger LOG = LoggerFactory.getLogger(BulletinSalaireController.class);

	private BulletinSalaireRepository bulletinSalaireRepository;
	private PeriodeRepository periodeRepository;
	private CotisationRepository cotisationRepository;

	public BulletinSalaireController(BulletinSalaireRepository bulletinSalaireRepository,
			PeriodeRepository periodeRepository, CotisationRepository cotisationRepository) {
		super();
		this.bulletinSalaireRepository = bulletinSalaireRepository;
		this.periodeRepository = periodeRepository;
		this.cotisationRepository = cotisationRepository;
	}

	// Cette méthode est exécutée lorsqu'une requête GET /bulletins est reçue
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody // parser l'objet BulletinSalaire
	public List<BulletinSalaireRequest> listeBulletinsSalaire() {

		List<BulletinSalaire> listeBulletins = new ArrayList<>();

		if (this.bulletinSalaireRepository.findAll().size() > 0) {
			listeBulletins = this.bulletinSalaireRepository.findAll();
		}

		List<BulletinSalaireRequest> listeBulletinsPageIndex = new ArrayList<>();
		for (BulletinSalaire b : listeBulletins) {

			BulletinSalaireRequest bulletinReq = new BulletinSalaireRequest();

			BigDecimal salaireBase = b.getRemunerationEmploye().getGrade().getNbHeuresBase()
					.multiply(b.getRemunerationEmploye().getGrade().getTauxBase());
			BigDecimal salaireBrut = salaireBase.add(b.getPrimeExceptionnelle());
			// BigDecimal totalRetenueSalariale = 0;

			List<Cotisation> listeCotisations = b.getRemunerationEmploye().getProfilRemuneration().getCotisations();

			List<Integer> idCotisationsList = new ArrayList<>();

			for (Cotisation c : listeCotisations) {

				if (this.cotisationRepository.existsById(c.getId())) {

					idCotisationsList.add(c.getId());
				}
			}

			LOG.info("cotisat" + idCotisationsList);
			BigDecimal totalRetenuSalariale = this.cotisationRepository.sumTauxSalarial(false, idCotisationsList)
					.multiply(salaireBrut);
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

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

			String dateCreation = b.getDateCreation().format(formatter);

			bulletinReq.setDateCreation(dateCreation);

			listeBulletinsPageIndex.add(bulletinReq);
		}

		return listeBulletinsPageIndex;
	}

	// Cette méthode est exécutée lorsqu'une requête GET /bulletins est reçue
	@RequestMapping(method = RequestMethod.GET, params ="id")
	@ResponseBody // parser l'objet BulletinSalaire
	public BulletinSalaireRequest bulletinSalaire(@RequestParam("id") Integer idRequeteHttp) {

		BulletinSalaire bulletin = new BulletinSalaire();

		if (this.bulletinSalaireRepository.existsById(idRequeteHttp)) {
			bulletin = this.bulletinSalaireRepository.findById(idRequeteHttp)
					.orElseThrow(() -> new EntityNotFoundException("Bulletin non trouvé"));
		}
		LOG.info("bulletin" + bulletin.getId());
		BulletinSalaireRequest bulletinPage = new BulletinSalaireRequest();

		BigDecimal salaireBase = bulletin.getRemunerationEmploye().getGrade().getNbHeuresBase()
				.multiply(bulletin.getRemunerationEmploye().getGrade().getTauxBase());
		BigDecimal salaireBrut = salaireBase.add(bulletin.getPrimeExceptionnelle());
		// BigDecimal totalRetenueSalariale = 0;

		List<Cotisation> listeCotisations = bulletin.getRemunerationEmploye().getProfilRemuneration().getCotisations();

		List<Integer> idCotisationsList = new ArrayList<>();

		for (Cotisation c : listeCotisations) {

			if (this.cotisationRepository.existsById(c.getId())) {

				idCotisationsList.add(c.getId());
			}
		}

		LOG.info("cotisat" + idCotisationsList);
		BigDecimal totalRetenuSalariale = this.cotisationRepository.sumTauxSalarial(false, idCotisationsList)
				.multiply(salaireBrut);
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

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

		String dateCreation = bulletin.getDateCreation().format(formatter);

		bulletinPage.setDateCreation(dateCreation);

		return bulletinPage;
	}

}
