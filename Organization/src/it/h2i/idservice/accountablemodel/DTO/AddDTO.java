package it.h2i.idservice.accountablemodel.DTO;

public class AddDTO {

	
	public String first_parameter;
	public String second_parameter;
	public String third_parameter;
	
	
	
	
	public AddDTO() {}
	
	
	
	
	public AddDTO(String f, String s, String t) {
		
		first_parameter = f;
		second_parameter = s;
		third_parameter = t;
		
		
	}
	
	
	
	
	
	
	
	public String getFirst_parameter() {
		return first_parameter;
	}
	public void setFirst_parameter(String first_parameter) {
		this.first_parameter = first_parameter;
	}
	public String getSecond_parameter() {
		return second_parameter;
	}
	public void setSecond_parameter(String second_parameter) {
		this.second_parameter = second_parameter;
	}
	public String getThird_parameter() {
		return third_parameter;
	}
	public void setThird_parameter(String third_parameter) {
		this.third_parameter = third_parameter;
	}
	
	
	
	
	
	
	
	
	
	
}
