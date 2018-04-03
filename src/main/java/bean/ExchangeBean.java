package bean;

public class ExchangeBean {
	private String t_id;
	private String u_id;
	private String t_time;
	private String t_type;
	private String t_status;
	private String t_amount;
	private String t_exchange_rate;
	private String t_total;

	public ExchangeBean() {
	}

	public ExchangeBean(String t_id, String u_id, String t_time, String t_type, String t_status, String t_amount, String t_exchange_rate,
			String t_total) {
		super();
		this.t_id = t_id;
		this.u_id = u_id;
		this.t_time = t_time;
		this.t_type = t_type;
		this.t_status = t_status;
		this.t_amount = t_amount;
		this.t_exchange_rate = t_exchange_rate;
		this.t_total = t_total;
	}

	public ExchangeBean(String u_id, String t_time, String t_type, String t_status, String t_amount, String t_exchange_rate,
			String t_total) {
		super();
		this.u_id = u_id;
		this.t_time = t_time;
		this.t_type = t_type;
		this.t_status = t_status;
		this.t_amount = t_amount;
		this.t_exchange_rate = t_exchange_rate;
		this.t_total = t_total;
	}

	public String getT_status() {
		return t_status;
	}

	public void setT_status(String t_status) {
		this.t_status = t_status;
	}

	public String getT_id() {
		return t_id;
	}

	public void setT_id(String t_id) {
		this.t_id = t_id;
	}

	public String getU_id() {
		return u_id;
	}

	public void setU_id(String u_id) {
		this.u_id = u_id;
	}

	public String getT_time() {
		return t_time;
	}

	public void setT_time(String t_time) {
		this.t_time = t_time;
	}

	public String getT_type() {
		return t_type;
	}

	public void setT_type(String t_type) {
		this.t_type = t_type;
	}

	public String getT_amount() {
		return t_amount;
	}

	public void setT_amount(String t_amount) {
		this.t_amount = t_amount;
	}

	public String getT_exchange_rate() {
		return t_exchange_rate;
	}

	public void setT_exchange_rate(String t_exchange_rate) {
		this.t_exchange_rate = t_exchange_rate;
	}

	public String getT_total() {
		return t_total;
	}

	public void setT_total(String t_total) {
		this.t_total = t_total;
	}

	@Override
	public String toString() {
		return "ExchangeBean [t_id=" + t_id + ", u_id=" + u_id + ", t_time=" + t_time + ", t_type=" + t_type
				+ ", t_amount=" + t_amount + ", t_exchange_rate=" + t_exchange_rate + ", t_total=" + t_total + "]";
	}

}
