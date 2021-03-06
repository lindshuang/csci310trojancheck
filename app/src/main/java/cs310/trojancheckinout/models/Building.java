package cs310.trojancheckinout.models;

import java.util.List;


public class Building {

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public List<User> getOccupants() {
        return occupants;
    }

    public void setOccupants(List<User> occupants) {
        this.occupants = occupants;
    }

    public int getCurrCapacity() {
        return currCapacity;
    }

    public void setCurrCapacity(int currCapacity) {
        this.currCapacity = currCapacity;
    }

    public String getQRcode() {
        return QRcode;
    }

    public void setQRcode(String QRcode) {
        this.QRcode = QRcode;
    }

    private String buildingName;
    private int maxCapacity;
    private List<User> occupants;
    private int currCapacity;
    private String QRcode;
}
