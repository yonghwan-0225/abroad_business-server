package bean;

public class TravelBean {
	private String u_id;
	private String date_depart;
	private String date_arrive;
	private String destination;
	public TravelBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TravelBean(String u_id, String date_depart, String date_arrive, String destination) {
		super();
		this.u_id = u_id;
		this.date_depart = date_depart;
		this.date_arrive = date_arrive;
		this.destination = destination;
	}
	public String getU_id() {
		return u_id;
	}
	public void setU_id(String u_id) {
		this.u_id = u_id;
	}
	public String getDate_depart() {
		return date_depart;
	}
	public void setDate_depart(String date_depart) {
		this.date_depart = date_depart;
	}
	public String getDate_arrive() {
		return date_arrive;
	}
	public void setDate_arrive(String date_arrive) {
		this.date_arrive = date_arrive;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	@Override
	public String toString() {
		return "TravelBean [u_id=" + u_id + ", date_depart=" + date_depart + ", date_arrive=" + date_arrive
				+ ", destination=" + destination + "]";
	}

}
