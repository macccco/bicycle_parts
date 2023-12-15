package pack;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.BicyclePartsDto;


@WebServlet("/DspBicyclePartsUpdate")
public class DspBicyclePartsUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public DspBicyclePartsUpdate() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		//選んだ行のparts_idを取得
		String selectId = request.getParameter("select_id");
		BicyclePartsDto dto;
		
		
		//選択した行が表示された順番を取得
		String index = request.getParameter(selectId);
		//検索画面で表示していたリストを取得
		List<BicyclePartsDto> list = (List<BicyclePartsDto>)request.getSession().getAttribute("dbdata");
		//リストの中から選択したインデックス番号の要素だけ取り出してdtoに格納
		dto = list.get(Integer.parseInt(index));
		
		
		//dtoをリクエストスコープにセット
		request.setAttribute("dto", dto);
		
		//更新画面に遷移
		ServletContext context = this.getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher("/jsp/BicyclePartsUpdate.jsp");
        dispatcher.forward(request, response);
	}

}
