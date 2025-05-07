package com.example.demo.converters;

import com.example.demo.entity.Task;
import com.example.demo.entity.dto.TaskResponse;

import java.util.ArrayList;
import java.util.List;

public class EntityToResponse {

    public static List<TaskResponse> convertTasksToTaskResponses(List<Task> tasks){
        List<TaskResponse> taskResponses =new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            taskResponses.add(convertTaskToTaskResponse(tasks.get(i)));
        }
        return null;
    }

    public static TaskResponse convertTaskToTaskResponse(Task task){
        TaskResponse taskResponse = TaskResponse.builder().setTask(task.getTask()).setDateTime(task.getDatetime())
                .setOperation(task.getOperation()).setUrgency(task.getUrgency()).build();

        return taskResponse;
    }
}
