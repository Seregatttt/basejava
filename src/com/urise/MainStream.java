package com.urise;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainStream {

	public static void main(String[] args) {
		System.out.println(minNumber(new ArrayList<>(Arrays.asList(1, 1, 2, 2, 3, 3, 1, 1, 1, 1))));
		System.out.println(oddOrEven(new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5))));
	}

	private static int minNumber(List<Integer> integers) {
		Optional<Integer> s = Optional.ofNullable(integers
				.stream()
				.distinct()
				.sorted()
				.reduce(0, (s1, s2) -> s1 * 10 + s2));

		return s.orElse(0);
	}

	private static List<Integer> oddOrEven(List<Integer> integers) {
		Map<Boolean, List<Integer>> groupListNumber = integers
				.stream()
				.collect(Collectors.partitioningBy((p) -> p % 2 == 0));

		Optional<Integer> sum = Optional.ofNullable(
				Stream.concat(groupListNumber.get(true).stream(), groupListNumber.get(false).stream())
						.reduce(0, (s1, s2) -> s1 + s2));

		if (sum.isPresent()) {
			if (sum.get() % 2 == 0) {
				return groupListNumber.get(true);
			} else {
				return groupListNumber.get(false);
			}
		} else {
			return null;
		}
	}
}
