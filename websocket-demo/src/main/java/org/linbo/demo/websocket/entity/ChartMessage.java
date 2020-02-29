package org.linbo.demo.websocket.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ChartMessage {

    private String from;

    private String to;

    private String message;

    private Date date;

}
