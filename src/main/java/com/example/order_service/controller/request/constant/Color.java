package com.example.order_service.controller.request.constant;

import lombok.Getter;

@Getter
public enum Color {

    BLACK("Black"),
    WHITE("White"),
    YELLOW("Yellow"),
    PINK("Pink"),
    RED("Red"),
    PURPLE("Purple"),
    BLUE("Blue"),
    GREEN("Green"),
    GRAY("Gray"),
    BROWN("Brown");

    private final String color;

    Color(String color){
        this.color = color;
    }
}
