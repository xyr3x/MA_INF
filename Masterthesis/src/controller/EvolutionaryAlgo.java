/** Author: Moritz Wiemker
 * 	Masterthesis
 *
 *
 * Ablauf Evolution�rer Algorithmus
 *	1. Initialisierung: Die erste Generation von L�sungskandidaten wird (meist zuf�llig) erzeugt.
 *	2. Evaluation: Jedem L�sungskandidaten der Generation wird entsprechend seiner G�te ein Wert der Fitnessfunktion zugewiesen.
 *	3. Durchlaufe die folgenden Schritte, bis ein Abbruchkriterium erf�llt ist:
 *		3.1. Selektion: Auswahl von Individuen f�r die Rekombination
 *		3.2. Rekombination: Kombination der ausgew�hlten Individuen
 *		3.3. Mutation: Zuf�llige Ver�nderung der Nachfahren
 *		3.4. Evaluation: Jedem L�sungskandidaten der Generation wird entsprechend seiner G�te ein Wert der Fitnessfunktion zugewiesen.
 *		3.5. Selektion: Bestimmung einer neuen Generation
 *
 *
 */


package controller;

import java.util.ArrayList;
import java.util.List;

import model.*;

public class EvolutionaryAlgo {
	private List<FireFighterCrew> population = new ArrayList<FireFighterCrew>();



}
