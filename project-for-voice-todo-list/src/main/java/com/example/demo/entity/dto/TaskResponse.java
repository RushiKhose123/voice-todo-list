package com.example.demo.entity.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskResponse {
    private String operation;
    private String task;
    private String urgency;
    private String dateTime;

    public static class TaskResponseBuilder {
        private String operation;
        private String task;
        private String urgency;
        private String dateTime;


        public TaskResponseBuilder setOperation(String operation) {
            this.operation = operation;
            return this;
        }

        public TaskResponseBuilder setTask(String task) {
            this.task = task;
            return this;
        }

        public TaskResponseBuilder setUrgency(String urgency) {
            this.urgency = urgency;
            return this;
        }

        public TaskResponseBuilder setDateTime(String dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public TaskResponse build() {
            TaskResponse taskResponse1 = new TaskResponse();
            taskResponse1.setDateTime(dateTime);
            return taskResponse1;
        }
    }

    public static TaskResponseBuilder builder(){
        return new TaskResponseBuilder();
    }
}

