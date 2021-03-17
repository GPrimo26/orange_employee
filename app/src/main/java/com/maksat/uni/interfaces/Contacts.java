package com.maksat.uni.interfaces;

import com.maksat.uni.models.Employee;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Contacts {

    @GET("/api/sadmin/employees")
    Call<List<Employee>> getEmployees(@Query("Name") String name);

}
