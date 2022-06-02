package beans.models;

public class ContentOfTheFacility {
	private double id;
	private String name;
	
	public ContentOfTheFacility() {}

	public ContentOfTheFacility(double id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public double getId() {
		return id;
	}

	public void setId(double id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
