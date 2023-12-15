package dto;

public class BicyclePartsDto {
	private String partsId;
	private String partsName;
	private String partsMaker;
	private String category;
	private String type;
	private String price;
	private String dateCreate;
	private String dateUpdate;
	private String partsImage;


	public BicyclePartsDto() {
		partsId = "";
		partsName = "";
		partsMaker = "";
		category = "";
		type = "";
		price = "";
		partsImage = "";
	}

	public BicyclePartsDto(String partsId, String partsName, String partsMaker, String category, String type, String price, String dateCreate, String dateUpdate, String partsImage) {
		super();
		this.partsId = partsId;
		this.partsName = partsName;
		this.partsMaker = partsMaker;
		this.category = category;
		this.type = type;
		this.price = price;
		this.dateCreate = dateCreate;
		this.dateUpdate = dateUpdate;
		this.partsImage = partsImage;
	}

	
	public String getPartsId() {
		return partsId;
	}

	public void setPartsId(String partsId) {
		this.partsId = partsId;
	}

	public String getPartsName() {
		return partsName;
	}

	public void setPartsName(String partsName) {
		this.partsName = partsName;
	}

	public String getPartsMaker() {
		return partsMaker;
	}

	public void setPartsMaker(String partsMaker) {
		this.partsMaker = partsMaker;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(String dateCreate) {
		this.dateCreate = dateCreate;
	}

	public String getDateUpdate() {
		return dateUpdate;
	}

	public void setDateUpdate(String dateUpdate) {
		this.dateUpdate = dateUpdate;
	}
	
	public String getPartsImage() {
		return partsImage;
	}

	public void setPartsImage(String partsImage) {
		this.partsImage = partsImage;
	}
}
