package com.example.musiclibrary.model;

import lombok.Data;

@Data
public class SongLength {

    private Integer hours;
    private Integer minutes;
    private Integer seconds;
    private String formattedLength;

    public SongLength(Integer hours, Integer minutes, Integer seconds) {
        if(hours < 0) {
            throw new RuntimeException("Don't put an hour less than 0");
        }
        if(minutes >= 60 || minutes < 0) {
            throw new RuntimeException("Don't put minutes less than 0 or more than 59");
        }
        if(seconds >= 60 || seconds < 0) {
            throw new RuntimeException("Don't put seconds less than 0 or more than 59");
        }
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
        this.formattedLength = formattedForLength(hours, minutes, seconds);

    }

    private String formattedForLength(Integer hours, Integer minutes, Integer seconds) {

        String StringHours = hours.toString();
        String StringMinutes = minutes.toString();
        String StringSeconds = seconds.toString();

        if(hours < 10) {
            StringHours = "0" + StringHours;
        }
        if(minutes < 10) {
            StringMinutes = "0" + StringMinutes;
        }
        if(seconds < 10) {
            StringSeconds = "0" + StringSeconds;
        }
        if(hours < 1) {
            return StringMinutes + ":" + StringSeconds ;
        } else {
            return StringHours + ":"  + StringMinutes + ":" + StringSeconds ;
        }
    }
}