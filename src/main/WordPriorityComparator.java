package main;

import java.util.Comparator;

public class WordPriorityComparator implements Comparator<Word> {

	@Override
	public int compare(Word o1, Word o2) {
		int result;
		result = o2.getPriority() - o1.getPriority();
		if(result == 0) {
			result = o2.getName().compareTo(o1.getName());
		}
		return result;
	}

}
