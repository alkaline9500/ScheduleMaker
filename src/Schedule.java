/**
 * Schedule Class
 * Represents one possible schedule
 * @author Nic Manoogian
 */

import java.util.ArrayList;
import java.util.Scanner;
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

        for (Shift sh : os.getShifts())
        {
            // Don't add the taken shift
            if (!sh.equals(s))
            {
                shifts.add(sh.clone());
            }
            else
            {
                taken = sh;
                System.out.println("Found taken*****");
            }
        }
        
        for (Employee ee : os.getEmployees())
        {
            employees.add(ee.clone());
            if (ee.getName() == e.getName())
            {
                ee.take(taken);
            }
        }
    }

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
    }

    /**
     * Returns a list of the new configurations generated by
     * giving the next available shift the the next available employee
     * @return a list of the new configurations
     */
    public ArrayList<Schedule> getNeighbors()
    {
        ArrayList<Schedule> neighborList = new ArrayList<Schedule>();
        
        for (Employee e : employees)
        { 
            if (e.canWork(shifts.get(0)))
            {
                Schedule newSchedule = new Schedule(this, e, shifts.get(0));
                System.out.println(newSchedule);
                neighborList.add(newSchedule);
            }
            else
            {
                System.out.println(e + " cannot work the shift");
            }
        }

        return neighborList;
    }

    public ArrayList<Employee> getEmployees()
    {
        return employees;
    }

    public ArrayList<Shift> getShifts()
    {
        return shifts;
    }

    public String toString()
    {
        String out = "";
        for (Employee ee : employees)
        {
            out += ee + "\n";
        }
        out += shifts.size() + " unassigned shifts";
        return out;
    }
}
