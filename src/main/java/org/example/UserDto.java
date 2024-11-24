package org.example;

import lombok.Data;

import java.util.List;
@Data
public class UserDto {
   private String name;
   private int age;
   private List<Integer> marks;
   private Options options;

}
