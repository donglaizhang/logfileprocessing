package org.donglai.logp.utils;

import java.nio.file.Path;
import java.util.LinkedList;

public class InsertSorting {
	/**
	 * insert and sort together, binary search to insert
	 * @param list
	 * @param newPath
	 */
	public static void insertAndSort(LinkedList<Path> list, Path logfile){
		if(logfile==null){
			return;
		}
		String fileName=logfile.toString();
		if(list.isEmpty()){
			list.add(logfile);
			return;
		}
		 Path first = list.getFirst();
		if(before(logfile.toString(),first.toString())){
			list.addFirst(logfile);
		}
		
	}
	private static boolean before(String name1, String name2){
		String cal1=name1.substring(name1.length()-14,name1.length()-3);
		String cal2=name1.substring(name2.length()-14,name2.length()-3);
		String[] arrs1=cal1.split("-");
		String[] arrs2=cal2.split("-");
		int len=arrs1.length;
		int i=0;
		while(i<len){
			if(Integer.parseInt(arrs1[i])==Integer.parseInt(arrs2[i])){
				i++;
			}else{
				break;
			}
		}
		return Integer.parseInt(arrs1[i])<Integer.parseInt(arrs2[i]);
	}

}
