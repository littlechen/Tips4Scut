package tips4scut.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import tips4scut.model.Jw2005Score;
import tips4scut.utils.Pair;


public class StringUtil {
	
	public static final String STR_DELIMIT_1ST = "\\|";
	public static final String STR_DELIMIT_2ND = "\\$";
	public static final String STR_DELIMIT_3RD = "#";
	
	public static final char DELIMIT_1ST = 124;
	public static final char DELIMIT_2ND = 36;
	public static final char DELIMIT_3RD = 35;
	
	public static String delLastDelimit(String str,char delimit){
		return str.substring(0,str.lastIndexOf(delimit) -1 );
	}
	
	
	public static void printList(List<String> list){
		for(String val : list){
			System.out.println(val);
		}
	}

    public static void printList4Score(List<Jw2005Score> list){
        for(Jw2005Score val : list){
            System.out.println(val);
        }
    }
	
	public static <T> List<Pair<Integer, Integer>> backwardMaxMatch(String s,
			Set<String> dict, int maxWordLen, int minWordLen) {
		LinkedList wordList = new LinkedList();
		int curL = 0;
		int curR = s.length();

		while (curR >= minWordLen) {
			boolean isMatched = false;
			curL = (curR - maxWordLen < 0) ? 0 : curR - maxWordLen;
			while (curR - curL >= minWordLen) {
				if (dict.contains(s.substring(curL, curR))) {
					wordList.addFirst(new Pair(new Integer(curL), new Integer(
							curR)));

					curR = curL;
					curL = (curR - maxWordLen < 0) ? 0 : curR - maxWordLen;
					isMatched = true;
					break;
				}

				++curL;
			}
			if (!(isMatched))
				--curR;
		}
		return wordList;
	}

	public static <T> String genDelimitedString(Collection<T> objList,
			char delimit) {
		if ((objList == null) || (objList.isEmpty()))
			return "";
		StringBuilder sb = new StringBuilder();
		for (Iterator i$ = objList.iterator(); i$.hasNext();) {
			Object t = i$.next();
			sb.append(t.toString());
			sb.append(delimit);
		}
		return sb.substring(0, sb.length() - 1);
	}
	
	public static <T> String genDelimitedString(T[] objList, char delimit) {
		if ((objList == null) || (objList.length < 1))
			return "";
		StringBuilder sb = new StringBuilder();
		for (Object t : objList) {
			sb.append(t.toString());
			sb.append(delimit);
		}
		return sb.substring(0, sb.length() - 1);
	}
	
	public static boolean isSubsequence(String s1, String s2) {
		int len1 = s1.length();
		int len2 = s2.length();
		int[][] sublen = new int[len1 + 1][len2 + 1];
		int[][] subdir = new int[len1 + 1][len2 + 1];

		for (int i = 0; i <= len1; ++i) {
			sublen[i][0] = 0;
		}
		for (int i = 0; i <= len2; ++i) {
			sublen[0][i] = 0;
		}

		for (int i = 1; i <= len1; ++i) {
			for (int j = 1; j <= len2; ++j) {
				if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
					sublen[i][j] = (sublen[(i - 1)][(j - 1)] + 1);
					subdir[i][j] = 0;
				} else if (sublen[(i - 1)][j] >= sublen[i][(j - 1)]) {
					sublen[i][j] = sublen[(i - 1)][j];
					subdir[i][j] = 1;
				} else {
					sublen[i][j] = sublen[i][(j - 1)];
					subdir[i][j] = 2;
				}
			}
		}

		return ((len1 == sublen[len1][len2]) || (len2 == sublen[len1][len2]));
	}
}
