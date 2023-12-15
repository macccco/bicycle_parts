package dao;

import java.util.List;
import java.util.Map;

import dto.BicyclePartsDto;

public interface BicyclePartsDao {
	
	
	public List<BicyclePartsDto> selectAll() throws Exception;
	
	public List<BicyclePartsDto> search(List<String> columns, String AorO, String like) throws Exception;
	
	public BicyclePartsDto selectDto(String partsId) throws Exception;
	
	public String selectNextId() throws Exception;
	
	public int insert(List<String> columns) throws Exception;
	
	public int update(String partsId, Map<String, String> map) throws Exception;
	
	public int delete(String partsId) throws Exception;
}
