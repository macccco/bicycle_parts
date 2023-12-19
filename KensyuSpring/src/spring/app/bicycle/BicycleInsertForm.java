package spring.app.bicycle;

import org.springframework.web.multipart.MultipartFile;

public class BicycleInsertForm {

	private String partsId;
	private String partsName;
	private String partsMaker;
	private String category;
	private String type;
	private String price;
	private MultipartFile partsImage;
	private String lastFileName;
	
	
	public String getLastFileName() {
		return lastFileName;
	}
	public void setLastFileName(String lastFileName) {
		this.lastFileName = lastFileName;
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
	public MultipartFile getPartsImage() {
		return partsImage;
	}
	public void setPartsImage(MultipartFile partsImage) {
		this.partsImage = partsImage;
	}
	
	
	
	
}
