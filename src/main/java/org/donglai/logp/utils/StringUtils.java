package org.donglai.logp.utils;

public class StringUtils {
	/**
	 * check the string is positive integer 
	 */
	public static boolean isPositiveInt(String str) {
		for (int i = str.length()-1; i >= 0;i--) {
			int chr = str.charAt(i);
			if (chr < 48 || chr > 57)
				return false;
		}
		return true;
	}
	/**
	 * calculate the value of the big number(string) + long 
	 * @param start
	 * @param number
	 * @return
	 */
	public static String addLong(String start,long number){
		String strnum=number+"";
		int i=start.length()-1,j=strnum.length()-1;
		StringBuilder sb=new StringBuilder();
		boolean carry=false;
		while(i>=0&&j>=0){
			int int1=start.charAt(i)-'0';
			int int2=strnum.charAt(j)-'0';
			int sum=int1+int2+(carry?1:0);
			if(sum>9){
				sum=sum%10;
				carry=true;
			}else{
				carry=false;
			}
			sb.insert(0,sum);
			i--;
			j--;
		}
		while(i>=0){
			int int1=start.charAt(i)-'0';
			int sum=int1+(carry?1:0);
			if(sum>9){
				sum=sum%10;
				carry=true;
			}else{
				carry=false;
			}
			sb.insert(0,sum);
			i--;
		}
		while(j>=0){
			int int2=strnum.charAt(j)-'0';
			int sum=int2+(carry?1:0);
			if(sum>9){
				sum=sum%10;
				carry=true;
			}else{
				carry=false;
			}
			sb.insert(0,sum);
			j--;
		}
		if(carry){
			sb.insert(0, "1");
		}
		return sb.toString();
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
