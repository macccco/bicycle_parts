package pack;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BicyclePartsDao;

@WebServlet("/DspBicyclePartsInsert")
public class DspBicyclePartsInsert extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public DspBicyclePartsInsert() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		BicyclePartsDao dao = new BicyclePartsDao();
		String nextId = "";
		
		
		try {
			//テーブルの最後の行のparts_idにプラス１した値を取得
			nextId = dao.selectNextId(request);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		request.setAttribute("partsId", nextId);
		
		//登録画面に遷移
		ServletContext context = this.getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher("/jsp/BicyclePartsInsert.jsp");
        dispatcher.forward(request, response);
	}

}
