package pack;

import java.io.IOException;
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

import cnst.Constant;
import dao.BicyclePartsDao;

@WebServlet("/BicyclePartsInsert")
@MultipartConfig
public class BicyclePartsInsert extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public BicyclePartsInsert() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		BicyclePartsDao dao = new BicyclePartsDao();
		//formに入力した値を格納するリスト
		List<String> columns = new ArrayList<>();
		String[] cols = { "parts_id", "parts_name", "parts_maker", "category", "type", "price" };
		//formで入力したパラメータを取得してリストに格納
		for (String col : cols) {
			columns.add(request.getParameter(col));
		}
		//画像のパラメータを取得
		Part part=request.getPart("parts_image");
		//画像のファイル名を取得
		String filename = part.getSubmittedFileName();
		
		//もし画像が設定されていたら、、、
		if(!filename.equals("")) {
			//画像のファイル名を保存
			columns.add(filename);
			//保存する場所の絶対パス + ファイル名
			String path = Constant.UPLOAD_PATH + filename;
			//書き込み
			part.write(path);
		}
		
		//インサート実行
		 try {
			dao.insert(request, columns);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		//検索画面に遷移
		ServletContext context = this.getServletContext();
		RequestDispatcher dispatcher = context.getRequestDispatcher("/DspBicyclePartsSearch");
		dispatcher.forward(request, response);
	}

}
