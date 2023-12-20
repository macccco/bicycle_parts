package spring.app.bicycle;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cnst.BicycleConstant;
import dto.BicyclePartsDto;


@Controller
public class BicycleController {
	
	@Autowired
	private BicycleService bicycleService;
	
	
	//全件表示
	@RequestMapping(value = "/dspSearch", method = RequestMethod.GET)
	public String dspBicyclePartsSearch(Model model) {
		// 画面表示
        Object msg = model.getAttribute("msg");
        if (msg != null) {
            model.addAttribute("msg", String.valueOf(msg));
        }
		//Beanインスタンスを格納するリスト
		List<BicyclePartsDto> list = new ArrayList<>();
		
		//全件検索実行
		try {
			list = bicycleService.selectAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(!(list == null)) {
			
			model.addAttribute("dbdata", list);
			
			//遷移先画面へ
			return "../jsp/BicyclePartsSearch";
		} else {
			//リストがnullだった場合の処理
			return "../jsp/BicyclePartsSearch";
		}
	}
	
	
	//検索表示
	@RequestMapping(value = "/BicycleSearchAction", method = RequestMethod.POST)
	public String registerSearch(Model model, @ModelAttribute("bicycleSearchForm")BicycleSearchForm searchForm) {
		
		//Beanインスタンスを格納するリスト
		List<BicyclePartsDto> list = new ArrayList<>();
		//formに入力した値を格納するリスト
		List<String> columns = new ArrayList<>();
		
		//リストに入力した値を格納
		for(Field field : searchForm.getClass().getDeclaredFields()) {		//searchFormの全てのフィールドにアクセス
			try {
				field.setAccessible(true);									//自分の親クラス以外のprivateなフィールドにアクセスする場合は必要
				String name = field.getName();								//searchFormのフィールドの変数名を取得
				if(name.equals("like") || name.equals("AorO")) {			//「全一致/あいまい」と「AND/OR」は個別で取得したいのでリストに加えない
					continue;
				}
				columns.add(field.get(searchForm).toString());				//それ以外のフィールドの値はリストに加える
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		
		//「全一致/あいまい」検索の値
		String like = searchForm.getLike();
		//「AND/OR」検索の値
		String AorO = searchForm.getAorO();
		
		//検索実行
		try {
			list = bicycleService.search(columns, AorO, like);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(!(list == null || list.size() == 0)) {
			
			model.addAttribute("dbdata", list);
			
			//遷移先画面へ
			return "../jsp/BicyclePartsSearch";
		} else {
			//リストがnullだった場合の処理
			model.addAttribute("msg", "検索条件にヒットしたデータがありません");
			return "../jsp/BicyclePartsSearch";
		}
	}
	
	
	//登録画面表示
	@RequestMapping(value = "/bicycleInsertAction", method = RequestMethod.POST)
	public String dspBicyclePartsInsert(Model model) {
		//partsIdの最大値プラス１を取得
		String nextId = "";
		try {
			nextId = bicycleService.selectNextId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("partsId", nextId);
		//遷移先画面へ
		return "../jsp/BicyclePartsInsert";
		
	}
	
	
	//登録実行
	@RequestMapping(value = "/bicycleInsertRegisterAction", method = RequestMethod.POST)
	public String registerInsert(Model model, @ModelAttribute("bicycleInsertForm")BicycleInsertForm insertForm, RedirectAttributes redirectAttributes) {
		
		//formに入力した値を格納するリスト
		List<String> columns = new ArrayList<>();
		//リストに入力した値を格納
		columns.add(insertForm.getPartsId());
		columns.add(insertForm.getPartsName());
		columns.add(insertForm.getPartsMaker());
		columns.add(insertForm.getCategory());
		columns.add(insertForm.getType());
		columns.add(insertForm.getPrice());
		//アップロードするファイル名を取得
		MultipartFile mfile = insertForm.getPartsImage();
		String filename = mfile.getOriginalFilename();
		//もし画像が設定されていたら、、、
		if(!filename.equals("")) {
			//画像のファイル名を保存
			columns.add(filename);
			//保存する場所の絶対パス + ファイル名
			Path path = Paths.get(BicycleConstant.UPLOAD_PATH + filename);
			//書き込み
			try {
	        	long ret = Files.copy(mfile.getInputStream(), path);
	        	System.out.println(ret);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
		
		//登録実行
		try {
			int ret = bicycleService.insert(columns);
			if(ret > 0) {
				model.addAttribute("msg", "ID: " + insertForm.getPartsId() + " を登録しました");
//				redirectAttributes.addFlashAttribute("msg", "ID: " + insertForm.getPartsId() + " を登録しました");
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "登録に失敗しました");
		}
		
		
		//遷移先画面へ
		return dspBicyclePartsSearch(model);
		
		
	}
	
	
	//更新画面表示
	@RequestMapping(value = "/bicycleUpdateAction", method = RequestMethod.POST)
	public String dspBicyclePartsUpdate(Model model, @RequestParam("selectId") String partsId) {
		//ラジオボタンで選択した行のDTOを取得
		try {
			BicyclePartsDto dto = bicycleService.selectDto(partsId);
			model.addAttribute("dto", dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//遷移先画面へ
		return "../jsp/BicyclePartsUpdate";
		
	}
	
	
	//更新実行
	@RequestMapping(value = "/bicycleUpdateRegisterAction", method = RequestMethod.POST)
	public String registerUpdate(Model model, @ModelAttribute("bicycleUpdateForm")BicycleInsertForm updateForm) {
		
		//formに入力した値を格納するマップ
		Map<String, String> map = new HashMap<>();
		//更新画面で初期表示したparts_idを取得
		String partsId = updateForm.getPartsId();
		//カラム名の配列
		String[] cols = { "parts_name", "parts_maker", "category", "type", "price" };
		//フォームの入力値をマップに格納
		map.put(cols[0], updateForm.getPartsName());
		map.put(cols[1], updateForm.getPartsMaker());
		map.put(cols[2], updateForm.getCategory());
		map.put(cols[3], updateForm.getType());
		map.put(cols[4], updateForm.getPrice());
		
		//元画像のファイル名を取得
		String lastFileName = updateForm.getLastFileName();
		try {
			//もし画像が存在したら、、、
			if(!lastFileName.equals("")) {
				//画像を削除
				Path p = Paths.get(BicycleConstant.UPLOAD_PATH + lastFileName);
				//元画像の削除実行
				Files.deleteIfExists(p);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//アップロードするファイル名を取得
		MultipartFile mfile = updateForm.getPartsImage();
		String filename = mfile.getOriginalFilename();
		//もし画像が設定されていたら、、、
		if(!filename.equals("")) {
			//画像のファイル名を保存
			map.put("pict", filename);
			//保存する場所の絶対パス + ファイル名
			Path path = Paths.get(BicycleConstant.UPLOAD_PATH + filename);
			//書き込み
			try {
	        	Files.copy(mfile.getInputStream(), path);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
		
		//更新実行
		try {
			int ret = bicycleService.update(partsId, map);
			if(ret > 0) {
				model.addAttribute("msg", "ID: " + partsId + " を更新しました");
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "更新に失敗しました");
		}
		
		
		//遷移先画面へ
		return dspBicyclePartsSearch(model);
		
	}
	
	
	//削除実行
	@RequestMapping(value = "/bicycleDeleteAction", method = RequestMethod.POST)
	public String registerDelete(Model model, @RequestParam("selectId") String partsId) {
		
		try {
			//元画像のファイル名を取得
			String fileName = bicycleService.selectDto(partsId).getPartsImage();
			//もし画像が存在したら、、、
			if(!fileName.equals("")) {
				//画像を削除
				Path p = Paths.get(BicycleConstant.UPLOAD_PATH + fileName);
				//元画像の削除実行
				Files.deleteIfExists(p);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//削除実行
		try {
			int ret = bicycleService.delete(partsId);
			if(ret > 0) {
				model.addAttribute("msg", "ID: " + partsId + " を削除しました");
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "削除に失敗しました");
		}
		
		//遷移先画面へ
				return dspBicyclePartsSearch(model);
		
	}
	
	
	//CSV取り込み
	@RequestMapping(value = "/csvImportAction", method = RequestMethod.POST)
	public String registerCsvImport(Model model, @RequestParam("csvFile") MultipartFile uploadFile) {
		//取り込んだデータを格納するリスト
		List<BicyclePartsDto> list = new ArrayList<>();
		//CSV取り込み実行
		try {
			list = bicycleService.csvImport(uploadFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		if(!(list == null)) {
			
			model.addAttribute("dbdata", list);
			
			//遷移先画面へ
			return "../jsp/BicyclePartsSearch";
		} else {
			//リストがnullだった場合の処理
			return "../jsp/BicyclePartsSearch";
		}
	}
	

}
