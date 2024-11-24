package org.example;


import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

@Data
public class Options implements Serializable {
    @SerializedName("penalty")
    private OptionsPenalty penalty;
    @SerializedName("name")
    private String name;

}
