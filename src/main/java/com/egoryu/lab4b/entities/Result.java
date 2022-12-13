package com.egoryu.lab4b.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Result implements Serializable {
    private String result;

    private String give;

    public Result(String result, String give) {
        this.result = result;
        this.give = give;
    }
}
