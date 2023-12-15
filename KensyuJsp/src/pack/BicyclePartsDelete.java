package pack;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cnst.Constant;
import dao.BicyclePartsDao;
import dto.BicyclePartsDto;


@WebServlet("/BicyclePartsDelete")
public class BicyclePartsDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public BicyclePartsDelete() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		BicyclePartsDao dao = new BicyclePartsDao();
		//検索画面で表示していたリストを取得
		List<BicyclePartsDto> list = (List<BicyclePartsDto>)request.getSession().getAttribute("dbdata");
		//選択した行のparts_idを取得
		String selectId = request.getParameter("select_id");
		//選択した行のインデックス番号を取得
		String index = request.getParameter(selectId);
		//選択したインデックス番号の要素をリストから取り出してdtoに格納
		BicyclePartsDto dto = list.get(Integer.parseInt(index));
		//dtoに格納されている画像のファイル名を取得
		String fileName = dto.getPartsImage();
		
		//もし画像が存在したら、、、
		if(!fileName.equals("")) {
			//画像を削除
			Path p = Paths.get(Constant.UPLOAD_PATH + fileName);
			try{
				  Files.deleteIfExists(p);
				}catch(IOException e){
				  e.printStackTrace();
				}
		}
		
		//削除実行
		try {
			dao.delete(request, selectId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		//検索画面に遷移
		ServletContext context = this.getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher("/DspBicyclePartsSearch");
        dispatcher.forward(request, response);
	}

}
