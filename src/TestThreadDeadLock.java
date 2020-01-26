public class TestThreadDeadLock {
	public static Object LOCK1 = new Object();
	public static Object LOCK2 = new Object();

	public static void main(String args[]) {
		Thread1 thread1 = new Thread1();
		Thread2 thread2 = new Thread2();
		thread1.start();
		thread2.start();
	}

	private static class Thread1 extends Thread {
		public void run() {
			synchronized (LOCK1) {
				System.out.println("Thread 1: lock object LOCK1");
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Thread 1: Waiting for object LOCK2");

				synchronized (LOCK2) {
					System.out.println("Thread 1: lock object LOCK2");
				}
			}
			System.out.println("Thread 1: Successful run");
		}
	}

	private static class Thread2 extends Thread {
		public void run() {
			synchronized (LOCK2) {
				System.out.println("Thread 2: lock object LOCK2");

				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Thread 2: Waiting for object LOCK1");

				synchronized (LOCK1) {
					System.out.println("Thread 2: lock object LOCK2");
				}
			}
			System.out.println("Thread 2: Successful run");
		}
	}
}