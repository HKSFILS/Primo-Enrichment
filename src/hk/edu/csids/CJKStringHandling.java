package hk.edu.csids;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.spreada.utils.chinese.ZHConverter;

public class CJKStringHandling {
	
	private String ori_str;
	private String con_str;
	
	public CJKStringHandling(){
		ori_str = null;
		con_str = null;
	} //end ChineseHandling
	
	public CJKStringHandling(String str){
		if(isCJKString(str)){
			ori_str = str;
		} else {
			ori_str = null;
		} //end if
		con_str = null;
	} //end ChineseHandling()
	
	public boolean isCJK(){
		if(ori_str != null){
			return isCJKString(ori_str);
		} //end if
		return false;
	} //end isChinese()
	
	public void setOriginalString(String str){
		if(isCJKString(str)){
			ori_str = str;
			con_str = null;
		} else {
			ori_str = null;
		} //end if
	}//end setOriginalString()
	
	public String getResultString(){
		return con_str;
	} //end getResultString()
	
	public String getOriginalString(){
		return ori_str;
	} //end getResultString()
	
	public String removeNonCJKChars(){
		String out = "";
		if(ori_str != null){
			if(con_str == null){
				con_str = ori_str;
			} //end if
			for (int i = 0; i < con_str.length(); i++) {
				char ch = con_str.charAt(i);
				boolean retbool = isCJKChar(ch);
				if (retbool) {
					out += ch;
				} // end if
			} // end for
		} //end if
		con_str = out;
		return con_str;
	} //end removeNonCJKChars()
	
	public static boolean isCJKString(String str) {
		if(str == null){
			return false;
		} //end if
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			boolean retbool = isCJKChar(ch);
			if (retbool) {
				return true;
			} // end if
		} // end for
		return false;
	} // end checkCJK()
	
	public static boolean isCJKChar(Character ch) {
		Character.UnicodeBlock block = Character.UnicodeBlock.of(ch);
		if (Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS.equals(block)
				|| Character.UnicodeBlock.KATAKANA.equals(block)
				|| Character.UnicodeBlock.HIRAGANA.equals(block)
				|| Character.UnicodeBlock.HANGUL_SYLLABLES.equals(block)
				) {
			return true;
		} // end if
		return false;
	} // end check()
	
	public String convertToSimpChinese() {
		if(ori_str != null){
			ZHConverter converter = ZHConverter.getInstance(ZHConverter.SIMPLIFIED);
			if(con_str == null){
				con_str = converter.convert(ori_str);
			} else {
				con_str = converter.convert(con_str);
			}//end if
			return con_str;
		} //end if
		return "";
	} // end convertToSimpChinese()
	
	public String standardizeVariantChineseFast(){
		if(ori_str != null){
			if(con_str == null){
				con_str = ori_str;
			} //end if	
			String[] specChars = { "檯", "枱", "臺", "台", "峰 ", "峰", "鑑", "鍳", "研", "硏", "羨", "羡", "清", "淸", "群",
					"羣", "羣", "床", "裡", "裏", "啟", "啓", "の", "之", "と", "與", "的", "之" };

			for (int i = 0; i < specChars.length; i += 2) {
				con_str = con_str.replaceAll(specChars[i], specChars[i + 1]);
			}// end for
			return con_str;
		} //end if
		return "";
	} //end standardizeVariantChineseFast()
	
	public String standardizeVariantChinese(){
		if(ori_str != null){
			try{
			    InputStream is = null;
			    is = getClass().getResourceAsStream("ChineseVariantsUnicode.csv");
			    BufferedReader br = new BufferedReader(new InputStreamReader(is));
				String line;
				if(con_str == null){
					con_str = ori_str;
				} //end if
				while ((line = br.readLine()) != null) {
					String[] para = line.split(",");
					for(int i=0; i<para.length; i++){
						for(int k=0; k < con_str.length(); k++){
							char tempchar = con_str.charAt(k);
							if( para[i].toLowerCase().equals("\\u" + Integer.toHexString(tempchar | 0x10000).substring(1))){	
								String tempstr = para[0];
								tempstr = tempstr.replace("\\u","");
								int tempint = Integer.parseInt(tempstr, 16);
								char tempchar2 = (char)tempint;
								con_str = con_str.replace(con_str.charAt(k), tempchar2);
							} //end if
						}// end for		
					} //end for
				} //end while
				br.close();
			} //end try
			catch (IOException e) {} //end catch
			return con_str;
		} //end if
		return "";
	} //standardizeVariantChinese()
} //end class