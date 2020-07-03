package com.example.todoapp;

public class Task {
        private String taskId;
        private String taskName;

        public Task(){}

        public Task(String taskId, String taskName) {
            this.taskId = taskId;
            this.taskName = taskName;
        }

        public String getTaskId() {
            return taskId;
        }

        public String getTaskName() {
            return taskName;
        }
    }


