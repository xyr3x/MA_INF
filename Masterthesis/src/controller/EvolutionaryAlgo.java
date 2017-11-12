/** Author: Moritz Wiemker
 * 	Masterthesis
 *
 *
 * Ablauf Evolutionärer Algorithmus
 *	1. Initialisierung: Die erste Generation von Lösungskandidaten wird (meist zufällig) erzeugt.
 *	2. Evaluation: Jedem Lösungskandidaten der Generation wird entsprechend seiner Güte ein Wert der Fitnessfunktion zugewiesen.
 *	3. Durchlaufe die folgenden Schritte, bis ein Abbruchkriterium erfüllt ist:
 *		3.1. Selektion: Auswahl von Individuen für die Rekombination
 *		3.2. Rekombination: Kombination der ausgewählten Individuen
 *		3.3. Mutation: Zufällige Veränderung der Nachfahren
 *		3.4. Evaluation: Jedem Lösungskandidaten der Generation wird entsprechend seiner Güte ein Wert der Fitnessfunktion zugewiesen.
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
