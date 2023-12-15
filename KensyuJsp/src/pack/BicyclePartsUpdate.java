package pack;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

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


@WebServlet("/BicyclePartsUpdate")
@MultipartConfig
public class BicyclePartsUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public BicyclePartsUpdate() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		BicyclePartsDao dao = new BicyclePartsDao();
		//formに入力した値を格納するマップ
		Map<String, String> map = new HashMap<>();
		//更新画面で初期表示したparts_idを取得
		String partsId = request.getParameter("parts_id");
		//カラム名の配列
		String[] cols = { "parts_name", "parts_maker", "category", "type", "price" };
		//フォームの入力値をマップに格納
		for (String col : cols) {
			String param = request.getParameter(col);
			map.put(col, param);
		}

		//新しい画像ファイルのパラメータを取得
		Part part = request.getPart("parts_image");
		//新しい画像のファイル名を取得
		String nextFileName = part.getSubmittedFileName();
		
		//ファイルのアップロードがあるときはmapに追加する
		if(!nextFileName.equals("")) {
			map.put("pict", nextFileName);
			//元画像のファイル名を取得
			String lastFileName = request.getParameter("file_name");
			//元画像を削除する
			Path p = Paths.get(Constant.UPLOAD_PATH + lastFileName);
				try{
					  Files.deleteIfExists(p);
					}catch(IOException e){
						e.printStackTrace();
					}
			//新しい画像の絶対パスを作成
			String path = Constant.UPLOAD_PATH +nextFileName;
			//書き込み
			part.write(path);
		}
		
		//アップデート実行
		try {
			dao.update(request, partsId, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		//検索画面に遷移
		ServletContext context = this.getServletContext();
		RequestDispatcher dispatcher = context.getRequestDispatcher("/DspBicyclePartsSearch");
		dispatcher.forward(request, response);
	}

}
