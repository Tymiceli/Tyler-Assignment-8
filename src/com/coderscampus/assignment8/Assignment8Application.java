package com.coderscampus.assignment8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Assignment8Application {

	public static void main(String[] args) throws InterruptedException {
		Assignment8 a8 = new Assignment8();

		List<Integer> numbersList = new ArrayList<>();

		List<CompletableFuture<Void>> tasks = new ArrayList<>();

		ExecutorService pool = Executors.newCachedThreadPool();

		for (int i = 0; i < 1000; i++) {
			CompletableFuture<Void> task = CompletableFuture.supplyAsync(() -> a8.getNumbers(), pool)
					.thenAcceptAsync(numbersList::addAll, pool);
			tasks.add(task);
		}

		while (tasks.stream().filter(CompletableFuture::isDone).count() < 1000) { // while the tasks are all (1000) done
			Thread.sleep(1000);
		}
		
		System.out.println("The list of records is " + numbersList.size() + " numbers long.");
		System.out.println("This index should not throw an exception: " + numbersList.get(1000000));
//		Map<Integer, Integer> myNumberMap = 
//				numbersList.stream().distinct();
//		System.out.println(numbersList);
		
	}
}
