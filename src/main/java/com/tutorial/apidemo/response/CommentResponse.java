package com.tutorial.apidemo.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {
    private int id;
    private String content;
    private float rate;
    private String room_name;
    private String floor;
    private String username;
    private String email;

}
