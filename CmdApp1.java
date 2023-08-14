import java.util.*;
import java.text.*;
//import java.text.SimpleDateFormat;

class AllTask {
    String taskName;
    String taskDesc;
    String taskDueDate;
    int taskPriority;
    boolean isDone;
    
}

public class CmdApp1 {
    Scanner sc=new Scanner(System.in);
    List<AllTask> task=new ArrayList<>();
    
    String redColor = "\u001B[31m ";
    String greenColor = "\u001B[32m";
    String yellowColor = "\u001B[33m";
    String blueColor = "\u001B[34";
    String whiteColor = "\u001B[37m";
    public void addTask() {
        System.out.print(greenColor+"Enter task: "+whiteColor);
        String task_Name = sc.nextLine();

        System.out.print(greenColor+"Enter task description: "+whiteColor);
        String task_Desc = sc.nextLine();

        System.out.print(greenColor+"Enter due date (yyyy-MM-dd) : "+whiteColor);
        String task_DueDate = sc.next();

        System.out.print(greenColor+"Enter priority (1-5): "+whiteColor);
        int task_Priority = sc.nextInt();
        sc.nextLine();

        AllTask newTask = new AllTask();
        newTask.taskName = task_Name;
        newTask.taskDesc = task_Desc;
        newTask.taskDueDate = task_DueDate;
        newTask.taskPriority=task_Priority;

        System.out.print(greenColor + "Set reminder for this task (Y/N): " + whiteColor);
        String setReminder = sc.nextLine();

        if (setReminder.equalsIgnoreCase("Y")) {
            setReminderForTask(newTask.taskName);
        }

        task.add(newTask);
        System.out.println(yellowColor+" Task is successfully added."+whiteColor);
    }

      public void setReminderForTask(String taskName) {
        System.out.print(greenColor + "Enter reminder date and time (yyyy-MM-dd HH:mm): " + whiteColor);
        String reminderDateTime = sc.nextLine();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try 
        {
            Date reminderDate = dateFormat.parse(reminderDateTime);
            if (reminderDate.after(new Date()))
             {
                Timer taskReminderTimer = new Timer();
                taskReminderTimer.schedule(new TimerTask()
                {
                    
                    public void run() 
                    {
                        System.out.println("\u001B[31mReminder: Deadline approaching for task '" + taskName + "'!\u001B[0m");
                    }
                }, reminderDate);

                System.out.println(yellowColor + "Reminder set for the task." + whiteColor);
            } else {
                System.out.println(redColor + "Reminder date should be in the future." + whiteColor);
            }
        } catch (ParseException e) {
            System.out.println(redColor + "Invalid date format. Reminder not set." + whiteColor);
        }
    }
    public void displayTasks(List<AllTask> displayTasks) {
        System.out.println("Choose display below options:");
        System.out.println("1. System Added Order");
        System.out.println("2. Priority Wise");
        System.out.println("3. Due Date Wise");
        int displayChoice = sc.nextInt();
        sc.nextLine();

        switch (displayChoice) {
            case 1:
                // Default order so no need to modify the list
                break;
            case 2:
                Collections.sort(displayTasks, Comparator.comparingInt(task -> task.taskPriority));
                break;
            case 3:
                Collections.sort(displayTasks, Comparator.comparing(task -> task.taskDueDate));
                break;
            default:
                System.out.println("Please Enter valid choice.");
                return;
        }

        System.out.println(yellowColor+"All Information of Tasks:"+whiteColor);
        System.out.println("+--------+--------------------+--------------------------+--------------+-----------+---------+----------------+");
        System.out.println(greenColor+"| Sr.    | Task               | Description              | Due Date     | Priority  | Done    | Status         |"+whiteColor);
        System.out.println("+--------+--------------------+--------------------------+--------------+-----------+---------+----------------+");

        int seq_No = 1;
        for (AllTask task : displayTasks) {
            String doneStatus = task.isDone ? "yes":"no";
            String completionStatus = task.isDone ?  "\u001B[32m Done \u001B[0m" : "\u001B[31m Pending \u001B[0m";
            System.out.printf("|  %-6d| %-16s   | %-17s        | %-10s   | %7d   | %-6s  | %-10s   \n",seq_No++,task.taskName,task.taskDesc,task.taskDueDate,task.taskPriority,doneStatus,completionStatus);
        }

        System.out.println("+--------+--------------------+--------------------------+--------------+-----------+---------+----------------+");
    }

    public void markTaskDone() {
        System.out.print("Enter the serial number of the task to mark as done: ");
        int task_Index = sc.nextInt();

        if (task_Index>=1 && task_Index<=task.size()) {
            AllTask selectedTask = task.get(task_Index - 1);
            selectedTask.isDone = true;
            System.out.println("Task marked as done.");
        } else {
            System.out.println("Invalid task number.");
        }
    }

    public void listPendingTasks() {
        List<AllTask> pending_Tasks = new ArrayList<>();
        for (AllTask task : task) {
            if (!task.isDone) {
                pending_Tasks.add(task);
            }
        }

        if (pending_Tasks.isEmpty())
        {
            System.out.println("There's no pending tasks.");
            return;
        }

        System.out.println("\n\nBelow is the List of pending tasks:"+whiteColor);
        System.out.println(greenColor+"1. Order by Priority");
        System.out.println("2. Order by Due Date"+whiteColor);
        int choice = sc.nextInt();
        sc.nextLine();

        if (choice==1)
        {
            Collections.sort(pending_Tasks, Comparator.comparingInt(task -> task.taskPriority));
        } 
        else if (choice==2) 
        {
            Collections.sort(pending_Tasks, Comparator.comparing(task -> task.taskDueDate));
        } 
        else
        {
            System.out.println("Invalid choice.");
            return;
        }

        displayTasks(pending_Tasks);
    }

    public void removeCompletedTasks() {
        List<AllTask> tasksToRemove = new ArrayList<>();
        for (AllTask task : task) {
            if (task.isDone) {
                tasksToRemove.add(task);
            }
        }

        if (tasksToRemove.isEmpty()) {
            System.out.println("No completed tasks to remove.");
            return;
        }

        task.removeAll(tasksToRemove);
        System.out.println("all completed tasks gets removed");
    }

    public static void main(String[] arg) {
        CmdApp1 options = new CmdApp1();
        boolean temp = true;
        Scanner scobj = new Scanner(System.in);
        String bold = "\u001B[1m";
        String italic = "\u001B[3m";
        String reset = "\u001B[0m";
        String cyan = "\u001B[36m"; // Cyan color
        String white = "\u001B[37m"; // White color
        System.out.println(bold + italic + "\n\n \u001B[36m \u001B[41m---------- Welcome To To-Do List Application !----------" + reset);

    while (temp) {
        System.out.println("\n " + cyan + "Choose Following Option :  " + reset); // Cyan color
        System.out.println(white + " 1. Add Task" );
        System.out.println( " 2. Display Tasks" );
        System.out.println(" 3. Mark Task as Done");
        System.out.println(" 4. List Pending Tasks");
        System.out.println(" 5. Remove Completed Tasks");
        System.out.println(" 6. Exit" + reset);

            int choice = scobj.nextInt();
            scobj.nextLine();

            switch (choice) {
                case 1:
                    options.addTask();
                    break;
                case 2:
                    options.displayTasks(options.task);
                    break;
                case 3:
                    options.markTaskDone();
                    break;
                case 4:
                    options.listPendingTasks();
                    break;
                case 5:
                    options.removeCompletedTasks();
                    break;
                case 6:
                    temp = false;
                    break;
                default:
                    System.out.println("Enter valid choice.");
            }
        }
    }
}
