package database.entities;

import java.io.Serializable;

public class Caddie implements Serializable {

	private static final long serialVersionUID = 1L;
	private int _idCaddie;
	private int fk_idPassenger;
	
	public Caddie() {
	}
	public Caddie(int _idCaddie, int fk_idPassenger) {
		this._idCaddie = _idCaddie;
		this.fk_idPassenger = fk_idPassenger;
	}
	public int getIdCaddie() {
		return _idCaddie;
	}
	public void setIdCaddie(int _idCaddie) {
		this._idCaddie = _idCaddie;
	}
	public int getIdPassenger() {
		return fk_idPassenger;
	}
	public void setIdPassenger(int fk_idPassenger) {
		this.fk_idPassenger = fk_idPassenger;
	}
	@Override
	public String toString() {
		return "Caddie [_idCaddie=" + _idCaddie + ", fk_idPassenger=" + fk_idPassenger + "]";
	}

}
