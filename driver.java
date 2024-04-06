 import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
public class driver {

    static final int TIME_QUANTUM_Q1 = 3; // Time quantum for Q1
    static final int Q1_PRIORITY = 1; // Priority for Q1
    static final int Q2_PRIORITY = 2; // Priority for Q2
    static ArrayList<ProcessControlBlock> Q1 = new ArrayList<>();
    static ArrayList<ProcessControlBlock> Q2 = new ArrayList<>();
    static ArrayList<ProcessControlBlock> scheduledProcesses = new ArrayList<>();
    static int currentTime = 0;
    Scanner input = new Scanner(System.in);

    
    public static void main(String[] args) {

        driver run= new driver();
        run.menue();
       
    }

    //menue method 
public void menue(){
   
        System.out.println("Choose an action:");
        System.out.println("1. Enter process’ information.");
        System.out.println("2. Report detailed information about each process and different scheduling criteria.");
        System.out.println("3. Exit the program.");
        System.out.print("Enter your choice: ");
        int choice = input.nextInt();

        switch (choice) {
            case 1:
            enterProcessInformation();
                break;
            case 2:
            reportInformation();                break;
            case 3:     
            System.out.println("Exiting the program...");
            System.exit(0);
                break;
            default:
                System.out.println("Invalid choice ,Please enter again.");
        }
        input.close();
    }

    //case 1 method
    public void enterProcessInformation(){
      
        System.out.print("Enter the number of processes: ");
        int numProcesses = input.nextInt();

        for (int i = 0; i < numProcesses; i++) {
            System.out.println("Enter information for process " + (i + 1) + ":");
            System.out.print("Priority (1 or 2): ");
            int priority = input.nextInt();
            System.out.print("Arrival time: ");
            int arrivalTime = input.nextInt();
            System.out.print("CPU burst time: ");
            int cpuBurst = input.nextInt();

            ProcessControlBlock process = new ProcessControlBlock(  (i + 1), priority, arrivalTime, cpuBurst);
            if (priority == Q1_PRIORITY)
                Q1.add(process);
            else
                Q2.add(process);
        }
    }

    //case 2 method(report)
    static void reportInformation() {

        scheduleProcesses();
        try {
            FileWriter writer = new FileWriter("Report.txt");
            writer.write("Scheduling order of the processes: ");
            for (ProcessControlBlock process : scheduledProcesses) {
                writer.write(process.processID + " | ");
            }
            writer.write("\n\n");
            for (ProcessControlBlock process : scheduledProcesses) {
                writer.write("Process ID: " + process.processID + "\n");
                writer.write("Priority: " + process.priority + "\n");
                writer.write("Arrival time: " + process.arrivalTime + "\n");
                writer.write("CPU burst: " + process.cpuBurst + "\n");
                writer.write("Start time: " + process.startTime + "\n");
                writer.write("Termination time: " + process.terminationTime + "\n");
                writer.write("Turnaround time: " + process.turnaroundTime + "\n");
                writer.write("Waiting time: " + process.waitingTime + "\n");
                writer.write("Response time: " + process.responseTime + "\n");
                writer.write("\n");
            }

            double avgTurnaroundTime = scheduledProcesses.stream().mapToInt(p -> p.turnaroundTime).average().orElse(0);
            double avgWaitingTime = scheduledProcesses.stream().mapToInt(p -> p.waitingTime).average().orElse(0);
            double avgResponseTime = scheduledProcesses.stream().mapToInt(p -> p.responseTime).average().orElse(0);

            writer.write("Average Turnaround Time: " + avgTurnaroundTime + "\n");
            writer.write("Average Waiting Time: " + avgWaitingTime + "\n");
            writer.write("Average Response Time: " + avgResponseTime + "\n");

            writer.close();

            System.out.println("Report written to Report.txt");
        } catch (IOException e) {
            System.out.println("An error occurred while writing the report.");
            e.printStackTrace();
        }
    }

}