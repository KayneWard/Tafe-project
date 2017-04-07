/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package traffic.monitoring.application;

/**
 *
 * @author Kayne
 */

//class to set the string names 
public class TrafficData
{
        String Time;
        String Location;
        String Lanes;
        String TotalVehicles;
        String AvgVehicles;
        String AvgVelocity;

// set the string as empty
    public TrafficData()
    {
        Time = "0:00 AM";
        Location = "0";
        Lanes = "0";
        TotalVehicles = "0";
        AvgVehicles = "0";
        AvgVelocity = "0"; 
                
    }        

  // 
    public TrafficData(String time, String location, String lanes, String totalVehicles, String avgVehicles, String avgVelocity)
    {
        Time = time;
        Location = location;
        Lanes = lanes;
        TotalVehicles = totalVehicles;
        AvgVehicles = avgVehicles;
        AvgVelocity = avgVelocity;
    }    
// constructor to split the string by commas and store each peice of data between commas on its own.
    // used because the data will be recieved from the monitoring station as a comma delimited line
    public TrafficData(String str)
    {
        String temp[] = str.split(",");
        Time = temp[0];
        Location = temp[1];
        Lanes = temp[2];
        TotalVehicles = temp[3];
        AvgVehicles = temp[4];
        AvgVelocity = temp[5];
    }
    
public String getAllData()
{
    return Time + "," + Location + "," + Lanes + "," + TotalVehicles + "," + AvgVehicles + "," + AvgVelocity;
}
public String getTableData()
{
    return Time + "," + Location + "," + AvgVehicles + "," + AvgVelocity;
}
public String getLinkedListData()
{
    return Time + "-" + AvgVehicles + "-" +  AvgVelocity;
}
public String getTimeLocation()
{
    return Time + " _ " + Location;
}



    public void setTime(String Time)
    {
        this.Time = Time;
    }

    public void setLocation(String Location)
    {
        this.Location = Location;
    }

    public void setLanes(String Lanes)
    {
        this.Lanes = Lanes;
    }

    public void setVehicles(String Vehicles)
    {
        this.TotalVehicles = Vehicles;
    }

    public void setVehiclesPerLane(String VehiclesPerLane)
    {
        this.AvgVehicles = VehiclesPerLane;
    }

    public void setVelocity(String Velocity)
    {
        this.AvgVelocity = Velocity;
    }

    public String getTime()
    {
        return Time;
    }

    public String getLocation()
    {
        return Location;
    }

    public String getLanes()
    {
        return Lanes;
    }

    public String getVehicles()
    {
        return TotalVehicles;
    }

    public String getVehiclesPerLane()
    {
        return AvgVehicles;
    }

    public String getVelocity()
    {
        return AvgVelocity;
    }

    
}
