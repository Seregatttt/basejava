import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainStream {

	public static void main(String[] args) {

		System.out.println(minNumber(new ArrayList<>(Arrays.asList(1, 1, 2, 2, 3, 3, 1, 1, 1, 1))));

		System.out.println(oddOrEven(new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5))));
	}

	private static int minNumber(List<Integer> integers) {
		int s = integers
				.stream()
				.distinct()
				.sorted()
				.reduce((s1, s2) -> s1 * 10 + s2).get();
		return s;
	}

	private static List<Integer> oddOrEven(List<Integer> integers) {
		Map<Boolean, List<Integer>> groupListNumber = integers
				.stream()
				.collect(Collectors.partitioningBy((p) -> p % 2 == 0));

		if (Stream.concat(groupListNumber.get(true).stream(), groupListNumber.get(false).stream())
				.mapToInt(integer -> integer).sum() % 2 == 0) {
			return new ArrayList<>(groupListNumber.get(true));
		} else {
			return new ArrayList<>(groupListNumber.get(false));
		}
	}

}
