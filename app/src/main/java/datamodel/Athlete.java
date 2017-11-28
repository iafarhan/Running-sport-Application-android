package datamodel;

/**
 * Created by Altaf Hussain on 11/7/2017.
 */

public class Athlete {

    int id;
    public  String averageSpeed;
    public  String maximumSpeed;
    public  String totaltimeTaken;
    public  String totaldistanceTravelled;
    public  String totalrun;

    public Athlete(int i, String string, String cursorString){   }
    public Athlete(int id, String avgSpeed, String maxSpeed,String timeTaken,String totaldistTravel,String totalrun){
        this.id = id;
        this.averageSpeed = avgSpeed;
        this.maximumSpeed = maxSpeed;
        this.totaldistanceTravelled = totaldistTravel;
        this.totaltimeTaken = timeTaken;
        this.totalrun=totalrun;
    }

    public String getMaximumSpeed() {
        return maximumSpeed;
    }

    public void setMaximumSpeed(String maximumSpeed) {
        this.maximumSpeed = maximumSpeed;
    }

    public String getDistanceTravelled() {
        return totaldistanceTravelled;
    }

    public void setDistanceTravelled(String distanceTravelled) {
        this.totaldistanceTravelled = distanceTravelled;
    }

    public String getTimeTakenTOCompleteRace() {
        return totaltimeTaken;
    }

    public void setTimeTakenTOCompleteRace(String timeTakenTOCompleteRace) {
        this.totaltimeTaken = timeTakenTOCompleteRace;
    }

    public int getID(){
        return this.id;
    }

    public void setID(int id){
        this.id = id;
    }

    public String getAverageSpeed(){
        return this.averageSpeed;
    }

    public void setAverageSpeed(String avgSpeed){
        this.averageSpeed = avgSpeed;
    }
}
