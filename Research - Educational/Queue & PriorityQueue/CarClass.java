import java.io.*;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;
import java.util.PriorityQueue;
import java.util.LinkedList;

public class CarClass {

    ArrayList<String> temp_list;
    Stack<Car> stack = new Stack<Car>();
    Queue<Car> queue = new LinkedList<Car>();
    PriorityQueue<Car> pq = new PriorityQueue<Car>();
    int count = 0;

    public CarClass() {
        try {
            BufferedReader bf = new BufferedReader(new FileReader(new File("cars.txt")));
            String temp = "";
            while ((temp = bf.readLine()) != null) {
                if (count == 0)
                    count++;
                else {
                    temp_list = new ArrayList<String>();
                    String[] temp_arr = temp.split("\t");
                    System.out.println(temp_arr.length);
                    for (int a = 0; a < temp_arr.length; a++) {
                        if (!temp_arr[a].equals(" "))
                            temp_list.add(temp_arr[a]);
                    }
                    Car temp_car = new Car(temp_list.get(0), temp_list.get(1), temp_list.get(2), temp_list.get(3),
                            temp_list.get(4), temp_list.get(5), temp_list.get(6), temp_list.get(7));
                    stack.push(temp_car);
                    queue.add(temp_car);
                    pq.add(temp_car);
                }
            }

            while (!stack.empty())
                print();
        } catch (IOException ioe) {
            System.out.println("File not found.");
        }
    }

    public void print() {
        System.out.println("Index " + count + "\n");
        System.out.println("Stack: " + stack.pop());
        System.out.println("Queue: " + queue.remove());
        System.out.println("Priority Queue: " + pq.remove() + "\n\n\n");
        count++;
    }

    public class Car implements Comparable<Car> {
        public int id, mpg, size, hp, weight, acc, country, cylinders;

        public Car(String id, String mpg, String size, String hp, String weight, String acc, String country,
                String cylinders) {
            this.id = Integer.parseInt(id);
            this.mpg = Integer.parseInt(mpg);
            this.size = Integer.parseInt(size);
            this.hp = Integer.parseInt(hp);
            this.weight = Integer.parseInt(weight);
            this.acc = Integer.parseInt(acc);
            this.country = Integer.parseInt(country);
            this.cylinders = Integer.parseInt(cylinders);
        }

        public int compareTo(Car car) {
            if (acc != car.acc)
                return -1 * (acc - car.acc);
            if (mpg != car.mpg)
                return mpg - car.mpg;
            if (hp != car.hp)
                return -1 * (hp - car.hp);
            if (size != car.size)
                return -1 * (size - car.size);
            if (weight != car.weight)
                return weight - car.weight;
            if (cylinders != car.cylinders)
                return -1 * (cylinders - car.cylinders);
            if (id != car.id)
                return id - car.id;
            return 0;
        }

        public String toString() {
            return id + "\t\t" + mpg + "\t\t" + size + "\t\t" + hp + "\t\t" + weight + "\t\t" + acc + "\t\t" + country
                    + "\t\t"
                    + cylinders;
        }

    }
}