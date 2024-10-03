package test1;

public class HelloCreateDto {
	private String name;
	private int price;
	private String message;
	public HelloCreateDto(String name, int price, String message) {
		super();
		this.name = name;
		this.price = price;
		this.message = message;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
