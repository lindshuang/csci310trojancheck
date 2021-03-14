package cs310.trojancheckinout.models;

import java.util.List;


public class Building {

    public Building(String buildingName, int maxCapacity, List<String> occupants, int currCapacity, String QRcode) {
        this.buildingName = buildingName;
        this.maxCapacity = maxCapacity;
        this.occupants = occupants;
        this.currCapacity = currCapacity;
        this.QRcode = QRcode;
    }

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

    public List<String> getOccupants() {
        return occupants;
    }

    public void setOccupants(List<String> occupants) {
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
    private List<String> occupants;
    private int currCapacity;
    private String QRcode;
}