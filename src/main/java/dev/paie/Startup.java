package dev.paie;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import dev.paie.entite.BulletinSalaire;
import dev.paie.entite.Entreprise;
import dev.paie.entite.Grade;
import dev.paie.entite.Periode;
import dev.paie.entite.ProfilRemuneration;
import dev.paie.entite.RemunerationEmploye;
import dev.paie.repository.BulletinSalaireRepository;
import dev.paie.repository.EntrepriseRepository;
import dev.paie.repository.GradeRepository;
import dev.paie.repository.PeriodeRepository;
import dev.paie.repository.ProfilRemunerationRepository;
import dev.paie.repository.RemunerationEmployeRepository;

/***
 * initialiser un jeu de données de remuneration employe et bulletins de salaire
 * @author audrey
 *
 */
@Component
public class Startup {

	private static final Logger LOG = LoggerFactory.getLogger(Startup.class);

	private RemunerationEmployeRepository remunerationEmployeRepository;
	private GradeRepository gradeRepository;
	private EntrepriseRepository entrepriseRepository;
	private ProfilRemunerationRepository profilRemunerationRepository;
	private BulletinSalaireRepository bulletinSalaireRepository;
	private PeriodeRepository periodeRepository;
	
	
	
	public Startup(RemunerationEmployeRepository remunerationEmployeRepository, GradeRepository gradeRepository,
			EntrepriseRepository entrepriseRepository, ProfilRemunerationRepository profilRemunerationRepository,
			BulletinSalaireRepository bulletinSalaireRepository, PeriodeRepository periodeRepository) {
		super();
		this.remunerationEmployeRepository = remunerationEmployeRepository;
		this.gradeRepository = gradeRepository;
		this.entrepriseRepository = entrepriseRepository;
		this.profilRemunerationRepository = profilRemunerationRepository;
		this.bulletinSalaireRepository = bulletinSalaireRepository;
		this.periodeRepository = periodeRepository;
	}


	@EventListener(ContextRefreshedEvent.class)
	public void init() {

		LOG.info("Démarrage de l'application");

		if (this.remunerationEmployeRepository.count() == 0) {
			try {

				Grade grade = this.gradeRepository.findById(4)
						.orElseThrow(() -> new EntityNotFoundException("Grade non trouvé"));

				Entreprise entreprise = this.entrepriseRepository.findById(3)
						.orElseThrow(() -> new EntityNotFoundException("Entreprise non trouvée"));

				ProfilRemuneration profil = this.profilRemunerationRepository.findById(2)
						.orElseThrow(() -> new EntityNotFoundException("Profil non trouvé"));

				RemunerationEmploye employe = new RemunerationEmploye();
				employe.setMatricule("M01");
				employe.setGrade(grade);
				employe.setEntreprise(entreprise);
				employe.setProfilRemuneration(profil);
				this.remunerationEmployeRepository.save(employe);

				LOG.info("Rémunération insérée");

				Grade grade2 = this.gradeRepository.findById(2)
						.orElseThrow(() -> new EntityNotFoundException("Grade non trouvé"));

				Entreprise entreprise2 = this.entrepriseRepository.findById(3)
						.orElseThrow(() -> new EntityNotFoundException("Entreprise non trouvée"));

				ProfilRemuneration profil2 = this.profilRemunerationRepository.findById(1)
						.orElseThrow(() -> new EntityNotFoundException("Profil non trouvé"));

				RemunerationEmploye employe2 = new RemunerationEmploye();
				employe2.setMatricule("M02");
				employe2.setGrade(grade2);
				employe2.setEntreprise(entreprise2);
				employe2.setProfilRemuneration(profil2);
				this.remunerationEmployeRepository.save(employe2);

				LOG.info("Rémunération 2 insérée");

				Grade grade3 = this.gradeRepository.findById(1)
						.orElseThrow(() -> new EntityNotFoundException("Grade non trouvé"));

				Entreprise entreprise3 = this.entrepriseRepository.findById(2)
						.orElseThrow(() -> new EntityNotFoundException("Entreprise non trouvée"));

				ProfilRemuneration profil3 = this.profilRemunerationRepository.findById(4)
						.orElseThrow(() -> new EntityNotFoundException("Profil non trouvé"));

				RemunerationEmploye employe3 = new RemunerationEmploye();
				employe3.setMatricule("M03");
				employe3.setGrade(grade3);
				employe3.setEntreprise(entreprise3);
				employe3.setProfilRemuneration(profil3);
				this.remunerationEmployeRepository.save(employe3);

				LOG.info("Rémunération 3 insérée");

			} catch (EntityNotFoundException e) {
				LOG.error("Problème d accès à une donnée: " + e.getMessage());
			}
		}
		
		if (this.bulletinSalaireRepository.count() == 0){
			
			try {
				Periode period = this.periodeRepository.findById(1)
						.orElseThrow(() -> new EntityNotFoundException("Période non trouvée"));

				RemunerationEmploye remEmploye = this.remunerationEmployeRepository.findById(1)
						.orElseThrow(() -> new EntityNotFoundException("Rémunération employé 1 non trouvée"));

				BulletinSalaire bulletin = new BulletinSalaire();
				bulletin.setPeriode(period);
				bulletin.setRemunerationEmploye(remEmploye);
				bulletin.setPrimeExceptionnelle(new BigDecimal("0"));
				bulletin.setDateCreation(LocalDateTime.now());
				this.bulletinSalaireRepository.save(bulletin);
				LOG.info("Bulletin inséré");

				Periode period2 = this.periodeRepository.findById(12)
						.orElseThrow(() -> new EntityNotFoundException("Période non trouvée"));
				
				BulletinSalaire bulletin2 = new BulletinSalaire();
				bulletin2.setPeriode(period2);
				bulletin2.setRemunerationEmploye(remEmploye);
				bulletin2.setPrimeExceptionnelle(new BigDecimal("250.35"));
				bulletin2.setDateCreation(LocalDateTime.now());
				this.bulletinSalaireRepository.save(bulletin2);
				LOG.info("Bulletin 2 inséré");
				
				RemunerationEmploye remEmploye3 = this.remunerationEmployeRepository.findById(2)
						.orElseThrow(() -> new EntityNotFoundException("Rémunération employé 2 non trouvée"));
				
				BulletinSalaire bulletin3 = new BulletinSalaire();
				bulletin3.setPeriode(period2);
				bulletin3.setRemunerationEmploye(remEmploye3);
				bulletin3.setPrimeExceptionnelle(new BigDecimal("150.15"));
				bulletin3.setDateCreation(LocalDateTime.now());
				this.bulletinSalaireRepository.save(bulletin3);
				LOG.info("Bulletin 3 inséré");
				
				
				RemunerationEmploye remEmploye4 = this.remunerationEmployeRepository.findById(3)
						.orElseThrow(() -> new EntityNotFoundException("Rémunération employé 3 non trouvée"));
				
				BulletinSalaire bulletin4 = new BulletinSalaire();
				bulletin4.setPeriode(period);
				bulletin4.setRemunerationEmploye(remEmploye4);
				bulletin4.setPrimeExceptionnelle(new BigDecimal("0"));
				bulletin4.setDateCreation(LocalDateTime.now());
				this.bulletinSalaireRepository.save(bulletin4);
				LOG.info("Bulletin 4 inséré");
				
				
				
			} catch (EntityNotFoundException e) {
				LOG.error("Problème d accès à une donnée: " + e.getMessage());
			}
		}

	}
}
