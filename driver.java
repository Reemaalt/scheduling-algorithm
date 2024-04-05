 import java.util.*;
public class driver {
    Scanner input = new Scanner(System.in);

    
    public static void main(String[] args) {
       
    }
public void menue(){
   
        System.out.println("Choose an action:");
        System.out.println("1. Enter process’ information.");
        System.out.println("2. Report detailed information about each process and different scheduling criteria.");
        System.out.println("3. Exit the program.");
        int choice = input.nextInt();

        switch (choice) {
            case 1:
            PCBinit();
                break;
            case 2:
             //calling method 2
                break;
            case 3:     
            System.out.println("Exiting the program...");
            System.exit(0);
                break;
            default:
                System.out.println("Invalid choice");
        }
        input.close();
    }
    public ProcessControlBlock PCBinit(){
        System.out.println("Enter Procces ID:");
        int Pid = input.nextInt();
        System.out.println("Enter Arrival time:");
        int arrivalTime = input.nextInt();
        System.out.println("Enter Priority of a process::");
        int priority =  input.nextInt();
        System.out.println("Enter CPU Burst:");
        int CpuBurst = input.nextInt();
        System.out.println("Enter Start and termination time:");
       int StartAndTerminationTime = input.nextInt();
       System.out.println("Enter Turn around time:");
         int turnaroundTime = input.nextInt();
         System.out.println("Enter Waiting time:"); 
         int waitingTime = input.nextInt();
         System.out.println("Enter Response time:"); 
         int responseTime = input.nextInt();
        
         
        return new ProcessControlBlock(Pid,priority,arrivalTime,CpuBurst,StartAndTerminationTime,turnaroundTime,waitingTime,responseTime);
         //and then add the procces to  a Queue based on the priority
         // im changing time type from int to time

    }

}