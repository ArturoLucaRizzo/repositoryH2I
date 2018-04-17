package it.h2i.idservice.accountablemodel.DTO;

public class ActionDTO {

	String success;
	String parameter;
	public ActionDTO() {
		

		this.success = "1";
	}

	public ActionDTO(String success,String Parameter) {
		super();
		this.success = success;
		this.parameter=parameter;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}
	

}
