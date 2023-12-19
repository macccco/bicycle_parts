package pack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BicyclePartsDao;
import dto.BicyclePartsDto;

@WebServlet("/DspBicyclePartsSearch")
public class DspBicyclePartsSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public DspBicyclePartsSearch() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		BicyclePartsDao dao = new BicyclePartsDao();
		//Beanインスタンスを格納するリスト
		List<BicyclePartsDto> list = new ArrayList<>();
		
		
		try {
			list = dao.selectAll(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		request.setAttribute("dbdata", list);

		//検索画面に遷移
		ServletContext context = this.getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher("/jsp/BicyclePartsSearch.jsp");
        dispatcher.forward(request, response);
	}

}
