import java.util.*;

public class problem9_4 {
	public static String shortestPathname(String pathname) {
		Stack<String> directories = new Stack<String>();
		boolean startsWithSlash = false;
		if(pathname.substring(0, 1).equals("/")) {
			startsWithSlash = true;
			pathname = pathname.substring(1);
		}
		int length = pathname.length();		
		int dotCounter = 0;
		boolean foundChar = false;
		int charIndex = -1;
		for(int i=0; i < length; i++) {
			String currentChar = pathname.substring(i, i+1);
			if(currentChar.equals(".")) {
				dotCounter++;
				if(dotCounter > 2) {
					return "Invalid pathname.";
				}
			} else if(currentChar.equals("/")) {
				if(dotCounter > 0) {
					if(dotCounter == 2) {
						if(directories.empty() || directories.peek() == "..") {
							directories.push("..");
						} else {
							String pop = directories.pop();
						}
					}
					dotCounter = 0;
				} else {
					if(foundChar) {
						System.out.println(i);
						String directoryName = pathname.substring(charIndex, i);
						directories.push(directoryName);
						foundChar = false;
					}
				}
			} else {
				if(!foundChar) {
					charIndex = i;
					foundChar = true;
				} else if(dotCounter >0) {
					return "Invalid pathname.";
				}
			}
		}
		String returnString = "";
		while(!directories.empty()) {
			returnString = "/" + directories.pop() + returnString;
		}

		if(!startsWithSlash) {
			returnString = returnString.substring(1);
		}
		return returnString;
	}

	public static void main(String[] args) {
		System.out.println(shortestPathname("/Users/Christopher////../Christopher/"));
	}
}
			