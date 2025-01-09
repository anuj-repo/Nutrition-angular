package learning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

public class Collections {

	public static void main(String[] args) {

		Thread threat = new Thread(() -> {

			for (int i = 0; i <= 10; i++) {
				System.out.println("Threat one " + i);
			}

		});

		try {
			Thread.sleep(1000);
			Thread threat1 = new Thread(() -> {
				for (int i = 0; i <= 10; i++) {
					System.out.println("Threat Two " + i);
				}

			});

			threat1.setDaemon(true);
			threat1.start();

		} catch (Exception e) {
			System.out.println(e);
		}

		threat.start();

	}

}
