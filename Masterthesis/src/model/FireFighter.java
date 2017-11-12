/** Author: Moritz Wiemker
 * 	Masterthesis
 *
 *
 *
 */


package model;

import controller.Main;

public class FireFighter {
	private int[] chain = new int[Main.TimeInterval];
	private int ID;
	private int startVertice;
	private int currentVeterice;

	//constructor
	public FireFighter(){
		this.ID = Main.FighterID;
		Main.FighterID++;
	}

	//getter and setter
	public int[] getChain() {
		return chain;
	}
	public void setChain(int[] chain) {
		this.chain = chain;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getStartVertice() {
		return startVertice;
	}
	public void setStartVertice(int startVertice) {
		this.startVertice = startVertice;
	}
	public int getCurrentVeterice() {
		return currentVeterice;
	}
	public void setCurrentVeterice(int currentVeterice) {
		this.currentVeterice = currentVeterice;
	}


}
