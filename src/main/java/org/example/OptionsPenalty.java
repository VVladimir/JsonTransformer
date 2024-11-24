package org.example;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;
@Data
public class OptionsPenalty implements Serializable {

    @SerializedName("close_location_groups")
    private CloseLocationGroups closeLocationGroups;


}
