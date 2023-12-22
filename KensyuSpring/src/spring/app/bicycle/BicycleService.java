package spring.app.bicycle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import dao.BicyclePartsDao;
import dto.BicyclePartsDto;


@Service
public class BicycleService {
	
	@Autowired
	private BicyclePartsDao bicyclePartsDao;
	
	
	
	public List<BicyclePartsDto> selectAll() throws Exception {
		return bicyclePartsDao.selectAll();
	}
	
	public List<BicyclePartsDto> search(List<String> columns, String AorO, String like) throws Exception {
		return bicyclePartsDao.search(columns, AorO, like);
	}
	
	public BicyclePartsDto selectDto(String partsId) throws Exception {
		return bicyclePartsDao.selectDto(partsId);
	}
	
	public String selectNextId() throws Exception {
		return bicyclePartsDao.selectNextId();
	}
	
	public int insert(List<String> columns) throws Exception {
		return bicyclePartsDao.insert(columns);
	}
	
	public int update(String partsId, Map<String, String> map) throws Exception {
		return bicyclePartsDao.update(partsId, map);
	}
	
	public int delete(String partsId) throws Exception {
		int ret = bicyclePartsDao.delete(partsId);
		bicyclePartsDao.serialNumbering();
		return ret;
	}
	
	public List<BicyclePartsDto> csvImport(MultipartFile uploadFile) throws IOException{
		List<BicyclePartsDto> result = new ArrayList<BicyclePartsDto>();
		InputStream stream = uploadFile.getInputStream();           
        Reader reader = new InputStreamReader(stream);
        BufferedReader br= null;
		try {
            br = new BufferedReader(reader);
            String line;
            String[] data;
            br.readLine();
            while ((line = br.readLine()) != null) {
                data = line.split(",");
                BicyclePartsDto dto = new BicyclePartsDto();
                dto.setPartsId(data[0]);
                dto.setPartsImage(data[1]);
                dto.setPartsName(data[2]);
                dto.setPartsMaker(data[3]);
                dto.setCategory(data[4]);
                dto.setType(data[5]);
                dto.setPrice(data[6].replace("\\", ""));
                dto.setDateCreate(data[7]);
                dto.setDateUpdate(data[8]);
                
                result.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (Exception e) {
            	e.printStackTrace();
            }
        }
        return result;
	}
	
	
}
