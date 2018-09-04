package database.entities;

import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class Billet  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int _idAirPlane;
	private String _idAirLine;
	private Date _departure;
	private String _destination;
	private int _id;
	private String _idPassenger;
	private int _nbAccompagnant;
	
	public Billet() {
	}
	
	
	public Billet(int _idAirPlane, String _idAirLine, Date _departure, String _destination, int _id,
			String _idPassenger, int _nbAccompagnant) {
		super();
		this._idAirPlane = _idAirPlane;
		this._idAirLine = _idAirLine;
		this._departure = _departure;
		this._destination = _destination;
		this._id = _id;
		this._idPassenger = _idPassenger;
		this._nbAccompagnant = _nbAccompagnant;
	}
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
        
        stringBuilder.append(String.format("%04d",this.getId()));
        stringBuilder.append("-");
        stringBuilder.append(String.format("%11d",getIdAirPlane()));
        stringBuilder.append("-");
        stringBuilder.append(getIdAirLine());
        stringBuilder.append("-");
        stringBuilder.append(this.getDestination());        
        stringBuilder.append("-");
        stringBuilder.append(new SimpleDateFormat("ddMMyyyy").format(this.getDeparture()));
        stringBuilder.append("-");
        stringBuilder.append(this.getIdPassenger());
        stringBuilder.append("-");
        stringBuilder.append(String.format("%11d",this.getIdPassenger()));
        
        return stringBuilder.toString();
	}
	public int getIdAirPlane() {
		return _idAirPlane;
	}
	public void setIdAirPlane(int _idAirPlane) {
		this._idAirPlane = _idAirPlane;
	}
	public String getIdAirLine() {
		return _idAirLine;
	}
	public void setIdAirLine(String _idAirLine) {
		this._idAirLine = _idAirLine;
	}
	public Date getDeparture() {
		return _departure;
	}
	public void setDeparture(Date _departure) {
		this._departure = _departure;
	}
	public String getDestination() {
		return _destination;
	}
	public void setDestination(String _destination) {
		this._destination = _destination;
	}
	public int getId() {
		return _id;
	}
	public void setId(int _id) {
		this._id = _id;
	}
	public String getIdPassenger() {
		return _idPassenger;
	}
	public void setIdPassenger(String _idPassenger) {
		this._idPassenger = _idPassenger;
	}
	public int getNbAccompagnant() {
		return _nbAccompagnant;
	}
	public void setNbAccompagnant(int _nbAccompagnant) {
		this._nbAccompagnant = _nbAccompagnant;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
