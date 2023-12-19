package pack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import dao.BicyclePartsDao;
import dto.BicyclePartsDto;


@WebServlet("/CsvImport")
@MultipartConfig
public class CsvImport extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public CsvImport() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		BicyclePartsDao dao = new BicyclePartsDao();
		List<BicyclePartsDto> list = new ArrayList<>();
		Connection conn = null;
		Part csv = request.getPart("csvFile");
		BufferedReader br = null;
		InputStream stream = csv.getInputStream();           
		Reader reader = new InputStreamReader(stream);
		
		try {
			conn = dao.getConnection();
			
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
                
                list.add(dto);
            }
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
                br.close();
            } catch (Exception e) {
            	e.printStackTrace();
            }
		}
		
		if(list != null) {
			request.setAttribute("dbdata", list);
		}
		
		ServletContext context = this.getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher("/jsp/BicyclePartsSearch.jsp");
        dispatcher.forward(request, response);
	}

}
