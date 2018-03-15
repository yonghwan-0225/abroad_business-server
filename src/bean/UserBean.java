package bean;

public class UserBean {
	private String u_id;
	private String u_pw;
	private String u_name;
	private String u_birth;
	private String u_phone;
	private String u_address;
	private String u_bank;
	private String u_account;
	private String u_email;

	public UserBean() {
	}

	public UserBean(String u_id, String u_pw, String u_name, String u_birth, String u_phone, String u_address,
			String u_bank, String u_account, String u_email) {
		super();
		this.u_id = u_id;
		this.u_pw = u_pw;
		this.u_name = u_name;
		this.u_birth = u_birth;
		this.u_phone = u_phone;
		this.u_address = u_address;
		this.u_bank = u_bank;
		this.u_account = u_account;
		this.u_email = u_email;
	}

	public String getU_email() {
		return u_email;
	}

	public void setU_email(String u_email) {
		this.u_email = u_email;
	}

	public String getU_id() {
		return u_id;
	}

	public void setU_id(String u_id) {
		this.u_id = u_id;
	}

	public String getU_pw() {
		return u_pw;
	}

	public void setU_pw(String u_pw) {
		this.u_pw = u_pw;
	}

	public String getU_name() {
		return u_name;
	}

	public void setU_name(String u_name) {
		this.u_name = u_name;
	}

	public String getU_birth() {
		return u_birth;
	}

	public void setU_birth(String u_birth) {
		this.u_birth = u_birth;
	}

	public String getU_phone() {
		return u_phone;
	}

	public void setU_phone(String u_phone) {
		this.u_phone = u_phone;
	}

	public String getU_address() {
		return u_address;
	}

	public void setU_address(String u_address) {
		this.u_address = u_address;
	}

	public String getU_bank() {
		return u_bank;
	}

	public void setU_bank(String u_bank) {
		this.u_bank = u_bank;
	}

	public String getU_account() {
		return u_account;
	}

	public void setU_account(String u_account) {
		this.u_account = u_account;
	}

	@Override
	public String toString() {
		return "UserBean [u_id=" + u_id + ", u_pw=" + u_pw + ", u_name=" + u_name + ", u_birth=" + u_birth
				+ ", u_phone=" + u_phone + ", u_address=" + u_address + ", u_bank=" + u_bank + ", u_account="
				+ u_account + ", u_email=" + u_email + "]";
	}

}
