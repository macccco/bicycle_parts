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


@WebServlet("/BicyclePartsSelect")
public class BicyclePartsSelect extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public BicyclePartsSelect() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		BicyclePartsDao dao = new BicyclePartsDao();
		//Beanインスタンスを格納するリスト
		List<BicyclePartsDto> list = new ArrayList<>();
		//formに入力した値を格納するリスト
		List<String> columns = new ArrayList<>();
		//検索フォームのパラメータ名の配列
		String[] cols = { "parts_id", "parts_name", "parts_maker", "category", "type", "lower_price", "upper_price", "lower_create", "upper_create", "lower_update", "upper_update"};
		//商品名を全一致検索するか、あいまい検索するかの値
		String likeSet = request.getParameter("like");
		//全入力欄に対してAND検索するかOR検索するかの値
		String ao = request.getParameter("AorO");
		//リストに入力した値を格納
		for(String col : cols) {
			columns.add(request.getParameter(col));
		}
		
		//検索実行
		try {
			list = dao.search(request, columns, ao, likeSet);
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
