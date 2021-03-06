/**
 * Shift Class
 * Represents a single shift
 */

public class Shift
{
    // Type of Shift
    private String type;
    // Rank
    private boolean supervisor;
    // Start time
    private WeekTime startTime;
    // End time
    private WeekTime endTime;

    /**
     * Constructs a shift
     * @param t type of shift
     * @param s rank
     * @param t1 start time
     * @param t2 end time
     */
    public Shift(String t, boolean s, WeekTime t1, WeekTime t2)
    {
        this.type = t;
        this.supervisor = s;
        this.startTime = t1;
        this.endTime = t2;
    }

    /**
     * Determines if the shift is for a supervisor
     * @return boolean true if the shift is for a supervisor
     */
    public boolean isSupervisor()
    {
        return supervisor;
    }

    /**
     * Determines if two Shifts will overlap
     * @return true if there exists a conflict
     */
    public boolean conflictsWith(Shift s)
    {
        // If shifts start on different days of the week, they will not overlap
        if (s.getStartTime().getWeek() == startTime.getWeek())
        {
            return (startTime.before(s.getEndTime()) && s.getStartTime().before(endTime));
        }
        else
        {
            return false;
        }
        
    }

    /**
     * Gets the start time
     * @return start time
     */
    public WeekTime getStartTime()
    {
        return startTime;
    }

    /**
     * Gets the end time
     * @return end time
     */
    public WeekTime getEndTime()
    {
        return endTime;
    }

    /**
     * Gets the weekday for the shift
     * @return weekday integer (0-6)
     */
    public int getWeek()
    {
        return startTime.getWeek();
    }

    /**
     * Gets a copy of the shift
     * @return new Shift copy
     */
    public Shift clone()
    {
        return new Shift(type, supervisor, startTime.clone(), endTime.clone());
    }

    /**
     * Determines if the Object is the same as the given object
     * @param o other Object to compare
     * @return boolean true of the Object is the same as the given object
     */
    public boolean equals(Object o)
    {
        if (o instanceof Shift)
        {
            Shift s = (Shift) o;
            return s.getStartTime().getStamp() == getStartTime().getStamp() && s.getEndTime().getStamp() == getEndTime().getStamp();
        }
        else
        {
            return false;
        }
    }

    /**
     * Gets the String representation for the Shift
     * @return String representation
     */
    public String toString()
    {
        return super.toString() + ": " + type + " shift from " + startTime + " to " + endTime;
    }
}
