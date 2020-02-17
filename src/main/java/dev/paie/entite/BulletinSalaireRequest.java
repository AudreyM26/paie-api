package dev.paie.entite;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BulletinSalaireRequest {

	private Integer id;
	private RemunerationEmploye remunerationEmploye;
	private Periode periode;
	private BigDecimal primeExceptionnelle;
	private BigDecimal salaireBase;
	private BigDecimal salaireBrut;
	private BigDecimal TotalRetenueSalariale;
	private BigDecimal netImposable;
	private BigDecimal netAPayer;
	private String dateCreation;

	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the remunerationEmploye
	 */
	public RemunerationEmploye getRemunerationEmploye() {
		return remunerationEmploye;
	}
	/**
	 * @param remunerationEmploye the remunerationEmploye to set
	 */
	public void setRemunerationEmploye(RemunerationEmploye remunerationEmploye) {
		this.remunerationEmploye = remunerationEmploye;
	}
	/**
	 * @return the periode
	 */
	public Periode getPeriode() {
		return periode;
	}
	/**
	 * @param periode the periode to set
	 */
	public void setPeriode(Periode periode) {
		this.periode = periode;
	}
	/**
	 * @return the primeExceptionnelle
	 */
	public BigDecimal getPrimeExceptionnelle() {
		return primeExceptionnelle;
	}
	/**
	 * @param primeExceptionnelle the primeExceptionnelle to set
	 */
	public void setPrimeExceptionnelle(BigDecimal primeExceptionnelle) {
		this.primeExceptionnelle = primeExceptionnelle;
	}
	/**
	 * @return the salaireBase
	 */
	public BigDecimal getSalaireBase() {
		return salaireBase;
	}
	/**
	 * @param salaireBase the salaireBase to set
	 */
	public void setSalaireBase(BigDecimal salaireBase) {
		this.salaireBase = salaireBase;
	}
	/**
	 * @return the salaireBrut
	 */
	public BigDecimal getSalaireBrut() {
		return salaireBrut;
	}
	/**
	 * @param salaireBrut the salaireBrut to set
	 */
	public void setSalaireBrut(BigDecimal salaireBrut) {
		this.salaireBrut = salaireBrut;
	}
	/**
	 * @return the totalRetenueSalariale
	 */
	public BigDecimal getTotalRetenueSalariale() {
		return TotalRetenueSalariale;
	}
	/**
	 * @param totalRetenueSalariale the totalRetenueSalariale to set
	 */
	public void setTotalRetenueSalariale(BigDecimal totalRetenueSalariale) {
		TotalRetenueSalariale = totalRetenueSalariale;
	}
	/**
	 * @return the netImposable
	 */
	public BigDecimal getNetImposable() {
		return netImposable;
	}
	/**
	 * @param netImposable the netImposable to set
	 */
	public void setNetImposable(BigDecimal netImposable) {
		this.netImposable = netImposable;
	}
	/**
	 * @return the netAPayer
	 */
	public BigDecimal getNetAPayer() {
		return netAPayer;
	}
	/**
	 * @param netAPayer the netAPayer to set
	 */
	public void setNetAPayer(BigDecimal netAPayer) {
		this.netAPayer = netAPayer;
	}
	/**
	 * @return the dateCreation
	 */
	public String getDateCreation() {
		return dateCreation;
	}
	/**
	 * @param dateCreation the dateCreation to set
	 */
	public void setDateCreation(String dateCreation) {
		this.dateCreation = dateCreation;
	}
}
