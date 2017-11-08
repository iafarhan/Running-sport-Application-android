package datamodel;

/**
 * Created by Altaf Hussain on 11/7/2017.
 */

public class Athlete {

    int id;
    String averageSpeed;
    String maximumSpeed;
    String distanceTravelled;
    String timeTakenTOCompleteRace;

    public Athlete(int i, String string, String cursorString){   }
    public Athlete(int id, String avgSpeed, String maxSpeed,String distTravel,String timeTaken){
        this.id = id;
        this.averageSpeed = avgSpeed;
        this.maximumSpeed = maxSpeed;
        this.distanceTravelled = distTravel;
        this.timeTakenTOCompleteRace = timeTaken;
    }

    public String getMaximumSpeed() {
        return maximumSpeed;
    }

    public void setMaximumSpeed(String maximumSpeed) {
        this.maximumSpeed = maximumSpeed;
    }

    public String getDistanceTravelled() {
        return distanceTravelled;
    }

    public void setDistanceTravelled(String distanceTravelled) {
        this.distanceTravelled = distanceTravelled;
    }

    public String getTimeTakenTOCompleteRace() {
        return timeTakenTOCompleteRace;
    }

    public void setTimeTakenTOCompleteRace(String timeTakenTOCompleteRace) {
        this.timeTakenTOCompleteRace = timeTakenTOCompleteRace;
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
