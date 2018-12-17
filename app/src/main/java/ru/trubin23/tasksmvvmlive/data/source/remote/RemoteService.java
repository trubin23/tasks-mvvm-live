package ru.trubin23.tasksmvvmlive.data.source.remote;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

interface RemoteService {

    @GET("/api_mvvm_live/tasks")
    Call<List<NetworkTask>> getTasks();

    @GET("/api_mvvm_live/tasks/{id}")
    Call<NetworkTask> getTask(@Path("id") String id);

    @POST("/api_mvvm_live/tasks")
    Call<NetworkTask> addTask(@Body NetworkTask task);

    @PUT("/api_mvvm_live/tasks/{id}")
    Call<NetworkTask> updateTask(@Path("id") String id, @Body NetworkTask task);

    @PUT("/api_mvvm_live/tasks/{id}")
    Call<NetworkTask> completeTask(@Path("id") String id, @Body StatusOfTask task);

    @DELETE("/api_mvvm_live/tasks/{id}")
    Call<NetworkTask> deleteTask(@Path("id") String id);

    @DELETE("/api_mvvm_live/tasks/completed")
    Call<Integer> deleteCompletedTasks();
}