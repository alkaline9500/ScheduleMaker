/**
 * Schedule Class
 * Represents one possible schedule
 * @author Nic Manoogian
 */

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;
import java.io.File;

public class Schedule
{
    // List of shifts which need to be covered
    private ArrayList<Shift> shifts;
    // List of employees
    private ArrayList<Employee> employees;

    /**
     * Default constructor creates the empty ArrayLists
     */
    public Schedule()
    {
        shifts = new ArrayList<Shift>();
        employees = new ArrayList<Employee>();
    }
    
    /**
     * Constructs a Schedule using another Schedule, an employee who will take a shift, and the shift
     * @param os other Schedule
     * @param e employee who will take the shift
     */
    public Schedule(Schedule os, Employee e, Shift s)
    {
        shifts = new ArrayList<Shift>();
        employees = new ArrayList<Employee>();
        Shift taken = null;

        // Load all Shifts but the given one
        for (Shift sh : os.getShifts())
        {
            // Don't add the taken shift
            if (!sh.equals(s))
            {
                shifts.add(sh.clone());
            }
            else
            {
                taken = sh.clone();
            }
        }
        
        // Load all Employees and assign the given Employee the given Shift
        for (Employee ee : os.getEmployees())
        {
            // Make a new Employee
            Employee newEmployee = ee.clone();
            // If it is the target, take the Shift
            if (ee.getName() == e.getName())
            {
                newEmployee.take(taken);
            }
            // Add the new Employee to the list
            employees.add(newEmployee);
        }
    }

    /**
     * Constructs a Schedule by copying another Schedule
     * @param os other Schedule
     */
    public Schedule(Schedule os)
    {
        shifts = new ArrayList<Shift>();
        employees = new ArrayList<Employee>();
        for (Shift sh : os.getShifts())
        {
            shifts.add(sh.clone());
        }

        for (Employee ee : os.getEmployees())
        {
            employees.add(ee.clone());
        }
    }

    /**
     * Constructs an "empty" schedule using a list of shifts to be covered and a list of employees
     * @param shiftsFile file containing shifts
     * @param employeesFile file containing shifts
     */
    public Schedule(String shiftsFile, String employeesFile)
    {
        this();
        Scanner sc = null;

        // Add employees
        try
        {
            sc = new Scanner(new File(employeesFile));
        }
        catch (java.io.FileNotFoundException e)
        {
            System.err.println("File not found");
            System.exit(1);
        }
        while (sc.hasNext())
        {
            String name = sc.next();
            boolean supervisor = sc.next().equals("supervisor");
            DayAvailability d0 = new DayAvailability(new WeekTime(sc.next()), new WeekTime(sc.next()));
            DayAvailability d1 = new DayAvailability(new WeekTime(sc.next()), new WeekTime(sc.next()));
            DayAvailability d2 = new DayAvailability(new WeekTime(sc.next()), new WeekTime(sc.next()));
            DayAvailability d3 = new DayAvailability(new WeekTime(sc.next()), new WeekTime(sc.next()));
            DayAvailability d4 = new DayAvailability(new WeekTime(sc.next()), new WeekTime(sc.next()));
            DayAvailability d5 = new DayAvailability(new WeekTime(sc.next()), new WeekTime(sc.next()));
            DayAvailability d6 = new DayAvailability(new WeekTime(sc.next()), new WeekTime(sc.next()));
            Availability a = new Availability(d0,d1,d2,d3,d4,d5,d6);
            employees.add(new Employee(name, supervisor, a));
        }

        // Add shifts
        try
        {
            sc = new Scanner(new File(shiftsFile));
        }
        catch (java.io.FileNotFoundException e)
        {
            System.err.println("File not found");
            System.exit(1);
        }
        while (sc.hasNext())
        {
            String type = sc.next();
            boolean supervisor = sc.next().equals("supervisor");
            WeekTime startTime = new WeekTime(sc.next());
            WeekTime endTime = new WeekTime(sc.next());
            shifts.add(new Shift(type, supervisor, startTime, endTime));
        }
    }

    /**
     * Determines if this configuration is the goal
     * @return if there are no shifts to be covered
     */
    public boolean isGoal()
    {
        return shifts.size() == 0;
        // TODO Each employee has one shift
        // TODO Other factors
    }

    /**
     * Returns a list of the new configurations generated by
     * giving the next available shift the the next available employee
     * @return a list of the new configurations
     */
    public ArrayList<Schedule> getNeighbors()
    {
        ArrayList<Schedule> neighborList = new ArrayList<Schedule>();

        Collections.shuffle(employees);
        // TODO Sort by amount of availability rather than shuffle
       
        // Go through the list of employees 
        for (Employee e : employees)
        { 
            // If the employee can work the first shift in the list
            if (e.canWork(shifts.get(0)))
            {
                // Make a new Schedule without the given shift, where the given employee has that shift
                Schedule newSchedule = new Schedule(this, e, shifts.get(0));
                // Add that shift to the neighborList
                neighborList.add(newSchedule);
            }
        }

        return neighborList;
    }

    /**
     * Gets the list of Employees
     * @return ArrayList of Employee objects
     */
    public ArrayList<Employee> getEmployees()
    {
        return employees;
    }

    /**
     * Gets the list of Shifts
     * @return ArrayList of Shifts
     */
    public ArrayList<Shift> getShifts()
    {
        return shifts;
    }

    /**
     * Gets a String representation for a Schedule
     * @return String representation of the Schedule
     */
    public String toString()
    {
        String out = super.toString() + "-------------------\n";
        for (Employee ee : employees)
        {
            out += ee + "\n";
        }
        out += shifts.size() + " unassigned shifts\n";
        out += "Is goal?: " + isGoal() + "\n";
        return out;
    }
}
