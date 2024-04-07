public class  ProcessControlBlock {
    String processID;
    int priority;
    int arrivalTime;
    int cpuBurst;
    int startTime;
    int terminationTime;
    int turnaroundTime;
    int waitingTime;
    int responseTime;
    int rem;
    int lastTimeMovedInQueue;
    boolean isInQueue;

    ProcessControlBlock(String processID, int priority, int arrivalTime, int cpuBurst) {
        this.processID = processID;
        this.priority = priority;
        this.arrivalTime = arrivalTime;
        this.cpuBurst = cpuBurst;
        this.startTime = -1; // Initialize to -1 indicating not yet started
        this.terminationTime = -1; // Initialize to -1 indicating not yet terminated
        this.isInQueue = false;
        rem=cpuBurst;
        lastTimeMovedInQueue =0;
    }

    void calculateTurnaroundTime() {
        turnaroundTime = terminationTime - arrivalTime;
    }

    void calculateWaitingTime() {
        waitingTime = turnaroundTime - cpuBurst;
    }

    void calculateResponseTime() {
        responseTime = startTime - arrivalTime;
    }
}

    