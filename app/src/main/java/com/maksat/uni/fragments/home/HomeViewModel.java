package com.maksat.uni.fragments.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.maksat.uni.models.Events;

public class HomeViewModel extends ViewModel {
   private MutableLiveData<Events.events> mEvent;
   private MutableLiveData<Integer> mParticipantId;

   public HomeViewModel(){
       mEvent=new MutableLiveData<>();
       mParticipantId=new MutableLiveData<>();
   }

   public LiveData<Events.events> getEvent(){
       return mEvent;
   }

   public void setEvent(Events.events event){
       mEvent.setValue(event);
   }

   public LiveData<Integer> getPaticipantId(){return mParticipantId;}

   public void setParticipantId(Integer id){mParticipantId.setValue(id);}

}