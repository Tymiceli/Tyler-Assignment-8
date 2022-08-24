package com.coderscampus.assignment8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Assignment8Application {

	public static void main(String[] args) throws InterruptedException {
		Assignment8 a8 = new Assignment8();

		List<Integer> numbersList = new ArrayList<>();
		numbersList = Collections.synchronizedList(numbersList);

		List<CompletableFuture<Void>> tasks = new ArrayList<>();

		ExecutorService pool = Executors.newCachedThreadPool();

			for (int i = 0; i < 1000; i++) {
				CompletableFuture<Void> task = CompletableFuture.supplyAsync(() -> a8.getNumbers(), pool)
						.thenAcceptAsync(numbersList::addAll, pool);
				tasks.add(task);
			}

		while (tasks.stream().filter(CompletableFuture::isDone).count() < 1000) { // while the tasks are all (1000) done
//			System.out.println("The number of completed threads: " + tasks.stream().filter(CompletableFuture::isDone).count());
			Thread.sleep(500);
		}
//		System.out.println("The number of completed threads: " + tasks.stream().filter(CompletableFuture::isDone).count());
//		System.out.println("The list of records is " + numbersList.size() + " numbers long.");
//		System.out.println("This index should not throw an exception: " + numbersList.get(999999));
		
		Map<Integer, Integer> myNumberMap = numbersList
				.stream()
				.collect(Collectors.toMap(Function.identity(), duplicateValue -> 1, Integer::sum));
		
		System.out.println(myNumberMap);
		
		

	}
}
