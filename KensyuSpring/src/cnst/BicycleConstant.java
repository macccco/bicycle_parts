package cnst;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BicycleConstant {
	
	public static final List<String> MAKERS = Collections.unmodifiableList( new ArrayList<String>(Arrays.asList(
			"cannondale", "Specialized", "Giant", "MKS", "CRANK BROTHERS", "VELO ORANGE", "SURLY", "Mavic")) );
	
	public static final List<String> CATEGORIES = Collections.unmodifiableList( new ArrayList<String>(Arrays.asList(
			"フレーム", "ホイール", "ハンドル", "サドル", "ペダル")) );
	
	public static final List<String> TYPES = Collections.unmodifiableList( new ArrayList<String>(Arrays.asList(
			"ロード", "グラベル", "クロス", "マウンテン")) );
	
	public static final String UPLOAD_PATH = "#";	//仮
}
