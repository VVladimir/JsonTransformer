package org.example;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;
@Data
public class CloseLocationGroups implements Serializable {
    @SerializedName("per_extra_vehicle")
    private int perExtraVehicle;
    private String name;



}
