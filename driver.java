 import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
public class driver {

    static final int TIME_QUANTUM = 3;

    static ProcessControlBlock[] cpuArray;
    static ProcessControlBlock[] Q1 ;
    static ProcessControlBlock[] Q2;
   
    static int currentTime = 0;
    static int q1Size = 0;
    static int q2Size = 0;
    static int q2Currentindex=-1;
    static String order = "";
    static Scanner input = new Scanner(System.in);
    
    public static void main(String[] args) {

        driver run= new driver();
        run.menue();
       
    }

    //menue method 
public void menue(){
    int choice;
   do{
        System.out.println("Choose an action:");
        System.out.println("1. Enter process, information.");
        System.out.println("2. Report detailed information about each process and different scheduling criteria.");
        System.out.println("3. Exit the program.");
        System.out.print("Enter your choice: ");
         choice = input.nextInt();

        switch (choice) {
            case 1:
            enterProcessInformation();
                break;
            case 2:
            reportInformation();           
                 break;
            case 3:     
            System.out.println("Exiting the program...");
            System.exit(0);
                break;
            default:
                System.out.println("Invalid choice ,Please enter again.");
            }
        } while (choice != 3);
    }

    //case 1 method
public void enterProcessInformation(){
      
        System.out.print("Enter the number of processes: ");
        int numOfProcesses=input.nextInt();
		cpuArray= new ProcessControlBlock[numOfProcesses];

        for (int i = 1; i <=numOfProcesses; i++) {
            System.out.println("Enter information for process " + i + ":");
            System.out.print("Priority (1 or 2): ");
            int priority = input.nextInt();
            System.out.print("Arrival time: ");
            int arrivalTime = input.nextInt();
            System.out.print("CPU burst time: ");
            int cpuBurst = input.nextInt();
            System.out.println("done info  P"+ i );
           
            ProcessControlBlock Process = new ProcessControlBlock(("P"+i),priority,arrivalTime,cpuBurst);
            cpuArray[i-1]= Process;           
        }
        scheduleProcesses();
    }

   //the scheduling !! RR & SJF
 public void scheduleProcesses(){
 
    Arrays.sort(cpuArray, (a, b) -> a.arrivalTime - b.arrivalTime);

    Q1=new ProcessControlBlock[cpuArray.length];
    Q2=new ProcessControlBlock[cpuArray.length];

    // check
    while(true) {
        add();
        
        if(q1Size!=0) {
            runQ1();continue;
        }

        if(q2Size!=0) {
            runQ2();continue;
        }

        if(checkEmpty())
            break;

        else {

            currentTime++;
        }

    
    }//end while 
}

    //case 2 method(report)
    public void reportInformation() {
        String output="";

    
        if (cpuArray == null) {
            System.out.println("\nSystem is empty\n");
            return;
        }
    else{
        
        // Display scheduling order on console
        System.out.println("Scheduling order of the processes: ");
        output += "\n\n[" + order.substring(0, order.length() - 1) + "]\n----------\n";

        System.out.println(output);
    
        // Display detailed information about each process on console
        System.out.println("Detailed information about each process:");
    
        for (ProcessControlBlock process : cpuArray) {
            System.out.println("Process ID: " + process.processID);
            System.out.println("Priority: " + process.priority);
            System.out.println("Arrival time: " + process.arrivalTime);
            System.out.println("CPU burst: " + process.cpuBurst);
            System.out.println("Start time: " + process.startTime);
            System.out.println("Termination time: " + process.terminationTime);
            System.out.println("Turnaround time: " + process.turnaroundTime);
            System.out.println("Waiting time: " + process.waitingTime);
            System.out.println("Response time: " + process.responseTime);
            System.out.println("------------");
        }
    
        // Calculating and displaying average turnaround time, waiting time, and response time
        double avgTurnaroundTime = Arrays.stream(cpuArray).mapToInt(p -> p.turnaroundTime).average().orElse(0);
        double avgWaitingTime = Arrays.stream(cpuArray).mapToInt(p -> p.waitingTime).average().orElse(0);
        double avgResponseTime = Arrays.stream(cpuArray).mapToInt(p -> p.responseTime).average().orElse(0);
    
        System.out.println("Average Turnaround Time: " + avgTurnaroundTime);
        System.out.println("Average Waiting Time: " + avgWaitingTime);
        System.out.println("Average Response Time: " + avgResponseTime);
    
        // Write all information to the output file
        try {
            FileWriter writer = new FileWriter("Report.txt");
    
            // Write scheduling order to file
            writer.write("Scheduling order of the processes: \n");
            writer.write(output);
    
            // Write detailed information about each process to file
            writer.write("Detailed information about each process:\n");
            for (ProcessControlBlock process : cpuArray) {
                writer.write("Process ID: " + process.processID + "\n");
                writer.write("Priority: " + process.priority + "\n");
                writer.write("Arrival time: " + process.arrivalTime + "\n");
                writer.write("CPU burst: " + process.cpuBurst + "\n");
                writer.write("Start time: " + process.startTime + "\n");
                writer.write("Termination time: " + process.terminationTime + "\n");
                writer.write("Turnaround time: " + process.turnaroundTime + "\n");
                writer.write("Waiting time: " + process.waitingTime + "\n");
                writer.write("Response time: " + process.responseTime + "\n\n");
                writer.write("------------\n");
            }
    
            // Write average turnaround time, waiting time, and response time to file
            writer.write("Average Turnaround Time: " + avgTurnaroundTime + "\n");
            writer.write("Average Waiting Time: " + avgWaitingTime + "\n");
            writer.write("Average Response Time: " + avgResponseTime + "\n");
    
            writer.close();
    
            System.out.println("\nReport written to Report.txt");
        } catch (IOException e) {
            System.out.println("An error occurred while writing the report.");
            e.printStackTrace();
        }
    }
    }

    //RR & SJF here
public  void add() {
    for(int i=0 ; i<cpuArray.length ; i++) {
        ProcessControlBlock p = cpuArray[i];


        if (p==null) {

            break;
        }




        if(p.arrivalTime<=currentTime && !p.isInQueue ) {

            if(p.priority==1)
                Q1[q1Size++]=p;
            else
                Q2[q2Size++]=p;

            p.isInQueue=true;
            p.lastTimeMovedInQueue=currentTime;
            
            
            

        }//end if
    }//end for

}//end addToQ1

public void runQ1() {
   
    q2Currentindex = -1;

    if (q1Size < 1) {
        // No process in Q1 to execute
        return;
    }

    ProcessControlBlock currentP = Q1[0];

    if (q1Size > 1) {
        ProcessControlBlock nextP = Q1[1];

        if (currentP.lastTimeMovedInQueue >= nextP.lastTimeMovedInQueue &&
            nextP.rem == nextP.cpuBurst &&
            currentP.rem != currentP.cpuBurst) {
            
            // Swap processes in Q1 if the condition is met
            Q1[0] = nextP;
            Q1[1] = currentP;
            currentP = nextP;
        }
    }
    updateOrder(currentP);

    // Record the start time if this is the first time the process is executed
    if (currentP.startTime == -1) {
        currentP.startTime = currentTime;
    }
    
    if (currentP.rem <= TIME_QUANTUM) {
        // Process finishes within the quantum
        currentTime += currentP.rem;
        add();
        currentP.terminationTime = currentTime;
        currentP.turnaroundTime = currentP.terminationTime - currentP.arrivalTime;
        currentP.waitingTime = currentP.turnaroundTime - currentP.cpuBurst;
        currentP.rem = 0;
        currentP.responseTime = currentP.startTime - currentP.arrivalTime;
        Delete(Q1, 1, 0); // Remove the completed process from Q1
    
    } else {
        // Process uses up its time quantum
        currentP.rem -= TIME_QUANTUM;
        currentTime += TIME_QUANTUM;
        add();
        currentP.lastTimeMovedInQueue = currentTime;
        DeleteAndAdd(Q1, 1); // Move the current process to the end of Q1
    }
}

public void runQ2() {

    int shortestIndex;

    if(q2Currentindex!=-1) {
        shortestIndex=q2Currentindex;
    }

    else {
        shortestIndex=findShortest(Q2);
        q2Currentindex=shortestIndex;
    }

    ProcessControlBlock current = Q2[shortestIndex];

    //first time for the process
    if(current.rem==current.cpuBurst) {
        current.startTime=currentTime;

    }

    currentTime++;


    if(current.rem==1) {

        current.terminationTime=currentTime;
        current.turnaroundTime=current.terminationTime-current.arrivalTime;
        current.waitingTime=current.turnaroundTime-current.cpuBurst;
        current.responseTime=current.startTime-current.arrivalTime;
        Delete(Q2,2,shortestIndex);
        q2Currentindex=-1;

    }

    current.rem=current.rem-1;
    updateOrder(current);

}//end executeq2

    //Helping methods
public  void split(ProcessControlBlock[] all) {
    int count1=0;
    int count2=0;

    for(ProcessControlBlock p : all){
        if(p.priority==1)
            count1++;
        else if(p.priority==2)
            count2++;

    }//end foreach


    
}// end split

public void Delete(ProcessControlBlock array[], int q, int indexToDelete) {
    if (q == 1) {
        for (int i = indexToDelete; i < q1Size - 1; i++) {
            array[i] = array[i + 1];
        }
        q1Size--;
    } else if (q == 2) {
        for (int i = indexToDelete; i < q2Size - 1; i++) {
            array[i] = array[i + 1];
        }
        q2Size--;
        q2Currentindex=-1;
    }
}

public void DeleteAndAdd(ProcessControlBlock array[], int q) {

    if(array.length ==1) return;

         if (q == 1) {
            ProcessControlBlock firstP = array[0];
            for (int i = 0; i < q1Size - 1; i++) {
            array[i] = array[i + 1];
        }
        array[q1Size - 1] = firstP;
    }

        else if (q == 2) {
            ProcessControlBlock firstP = array[0];
               for (int i = 0; i < q2Size - 1; i++) {
            array[i] = array[i + 1];
        }
        array[q1Size - 1] = firstP;	}
  
}

public boolean checkEmpty() {
    
    for(ProcessControlBlock p : cpuArray) {
        if(p.isInQueue==false)
            return false;
    }//end for

    return true;
}//end check

public int findShortest(ProcessControlBlock[] q) {
    if(q.length==0)return -1;
    if(q.length==1)return 0;

    int shortest=0;
    for(int i=1 ; i<q2Size; i++) {
        if(q[i].rem<q[shortest].rem)
            shortest=i;
    }

    return shortest;
}//end find shortest

 public void updateOrder(ProcessControlBlock currentProcess) {

    if(order.length()!=0) {

        String trimmedOrder = order.endsWith(",") ? order.substring(0, order.length() - 1) : order;

        String[] items = trimmedOrder.split("\\|");

        if (items.length == 0 || !items[items.length - 1].equals(currentProcess.processID)) {
            order += currentProcess.processID + "|";
        } 
    }
    else {
            order+=currentProcess.processID+"|";
         }

} 


}