import hk.edu.csids.*;

public class TestChineseHandling
{
	public static void main(String[] args) {
		String str = "夜トマトダイエット.abcde周潤發asdfjKf??klsdf!@#$%6六合彩sfdkxlkvj23498756*(&SD6234時報文化出版企業股份有限公司";
		CJKStringHandling ch = new CJKStringHandling(str);
		//ch.setOriginalString(str);
		if(ch.isCJK()){
			System.out.println("THIS IS CJK");
			ch.standardizeVariantChinese();
			ch.convertToSimpChinese();
			ch.removeNonCJKChars();
			str = ch.getResultString();
		} else {
			System.out.println("THIS IS NOT CJK");
		} //end if
		System.out.println("Original: " + ch.getOriginalString());
		System.out.println("Converted: " + str);
	}
}